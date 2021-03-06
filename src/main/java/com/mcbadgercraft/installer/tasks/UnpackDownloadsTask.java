package com.mcbadgercraft.installer.tasks;

import com.google.common.io.Files;
import com.mcbadgercraft.installer.Bootstrap;
import io.github.thefishlive.installer.InstallPhase;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.DownloadSet;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;
import org.tukaani.xz.XZFormatException;
import org.tukaani.xz.XZInputStream;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

public class UnpackDownloadsTask extends Task {

    public static final String PACK_EXTENSION = ".pack.xz";

    public UnpackDownloadsTask() {
        super("unpackDownloads");
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {

        DownloadSet downloads = (DownloadSet) installer.getTasks(InstallPhase.DOWNLOAD);
        List<File> files = downloads.getDownloadedFiles();
        File cur = null;

        for (Iterator<File> itr = files.iterator(); itr.hasNext(); ) {
            cur = itr.next();

            if (cur.getName().endsWith(PACK_EXTENSION)) {
                try {
                    Bootstrap.getLog().debug("Decompressing {}.", cur.getName());
                    decompress(cur);
                } catch (XZFormatException ex) {
                    // This wasn't meant to be, rename back to a jar file
                    Bootstrap.getLog().warn(cur.getName() + " is not a valid XZ compressed file.");
                    cur.renameTo(new File(cur.getParentFile(), cur.getName().substring(0, cur.getName().length() - PACK_EXTENSION.length())));
                } catch (IOException ex) {
                    Bootstrap.getLog().warn(cur.getName() + " could not be decompressed correctly.");
                    throw new InstallerException(ex);
                }
            }
        }

        return true;
    }

    private File decompress(File download) throws IOException {
        if (!download.exists()) return download;

        File dest = new File(download.getAbsolutePath().substring(0, download.getAbsolutePath().length() - PACK_EXTENSION.length()));

        if (dest.exists()) {
            dest.delete();
        }

        byte[] decompressed = readFully(new XZInputStream(new ByteArrayInputStream(Files.toByteArray(download))));

        //Snag the checksum signature
        String end = new String(decompressed, decompressed.length - 4, 4);
        if (!end.equals("SIGN")) {
            throw new IOException("Unpacking failed, signature missing " + end);
        }

        int x = decompressed.length;
        int len =
                ((decompressed[x - 8] & 0xFF)) |
                        ((decompressed[x - 7] & 0xFF) << 8) |
                        ((decompressed[x - 6] & 0xFF) << 16) |
                        ((decompressed[x - 5] & 0xFF) << 24);
        byte[] checksums = Arrays.copyOfRange(decompressed, decompressed.length - len - 8, decompressed.length - 8);

        FileOutputStream jarBytes = new FileOutputStream(dest);
        JarOutputStream jos = new JarOutputStream(jarBytes);

        Pack200.newUnpacker().unpack(new ByteArrayInputStream(decompressed), jos);

        jos.putNextEntry(new JarEntry("checksums.sha1"));
        jos.write(checksums);
        jos.closeEntry();

        jos.close();
        jarBytes.close();
        return dest;
    }

    public byte[] readFully(InputStream stream) throws IOException {
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
