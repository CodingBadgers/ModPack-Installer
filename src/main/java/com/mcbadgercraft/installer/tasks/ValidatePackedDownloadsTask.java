package com.mcbadgercraft.installer.tasks;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mcbadgercraft.installer.Bootstrap;
import io.github.thefishlive.installer.InstallPhase;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.DownloadSet;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ValidatePackedDownloadsTask extends Task {

    public ValidatePackedDownloadsTask() {
        super("validatePackedDownloads");
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        DownloadSet downloads = (DownloadSet) installer.getTasks(InstallPhase.DOWNLOAD);
        List<File> files = downloads.getDownloadedFiles();
        File cur;
        File decompressed;

        for (Iterator<File> itr = files.iterator(); itr.hasNext(); ) {
            cur = itr.next();

            if (!cur.getName().endsWith(UnpackDownloadsTask.PACK_EXTENSION)) {
                continue;
            }

            decompressed = new File(cur.getAbsolutePath().substring(0, cur.getAbsolutePath().length() - UnpackDownloadsTask.PACK_EXTENSION.length()));

            try {
                validateJar(decompressed);
            } catch (IOException e) {
                throw new InstallerException(e);
            }
        }
        return true;
    }

    private void validateJar(File jarFile) throws IOException, InstallerException {
        Bootstrap.getLog().debug("Checking checksums on {}.", jarFile.getName());

        Map<String, String> files = Maps.newHashMap();
        String[] hashes = null;

        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        JarEntry current;

        while(entries.hasMoreElements()) {
            current = entries.nextElement();

            byte[] data = readFully(jar, current);

            if (current.getName().equals("checksums.sha1")) {
                hashes = Iterables.toArray(Splitter.on("\n").split(new String(data, Charsets.UTF_8)), String.class);
            } else if (!current.isDirectory()) {
                files.put(current.getName(), Hashing.sha1().hashBytes(data).toString());
            }
        }
        jar.close();

        if (hashes == null) {
            throw InstallerException.missingChecksum(jar.getName());
        }

        boolean failed = false;

        for (String hash : hashes) {
            if (hash.trim().equals("") || !hash.contains(" ")) continue;

            Iterator<String> e = Splitter.on(" ").split(hash).iterator();
            String validChecksum = e.next();
            String target = e.next();
            String checksum = files.get(target);

            if (checksum == null) {
                Bootstrap.getLog().error("{} is missing", target);
                failed = true;
            } else if (!checksum.equals(validChecksum)) {
                Bootstrap.getLog().error("{} failed (local: {};expected: {}", target, checksum, validChecksum);
                failed = true;
            }
        }

        if (failed) {
            throw InstallerException.invalidChecksum(jar.getName());
        }

        Bootstrap.getLog().debug("Checksum valid on {}.", jarFile.getName());
    }

    public static byte[] readFully(JarFile jar, JarEntry entry) throws IOException {
        InputStream stream = jar.getInputStream(entry);
        byte[] data = new byte[4096];
        ByteArrayOutputStream entryBuffer = new ByteArrayOutputStream();
        int len;

        do {
            len = stream.read(data);
            if (len > 0) {
                entryBuffer.write(data, 0, len);
            }
        } while (len != -1);

        return entryBuffer.toByteArray();
    }

}
