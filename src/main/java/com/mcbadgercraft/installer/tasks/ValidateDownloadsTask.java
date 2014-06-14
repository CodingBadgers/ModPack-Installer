package com.mcbadgercraft.installer.tasks;

import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.utils.ChecksumGenerator;
import com.mcbadgercraft.installer.utils.Utils;
import io.github.thefishlive.installer.InstallPhase;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.Download;
import io.github.thefishlive.installer.download.DownloadSet;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class ValidateDownloadsTask extends Task {

    public ValidateDownloadsTask() {
        super("validateDownloads");
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {

        DownloadSet downloads = (DownloadSet) installer.getTasks(InstallPhase.DOWNLOAD);
        File curFile = null;
        Download cur = null;

        for (Iterator<Download> itr = downloads.iterator(); itr.hasNext(); ) {
            cur = itr.next();
            curFile = cur.getFileDest();

            try {
                String local = ChecksumGenerator.createSha1(curFile);
                String remote = Utils.readURLContents(cur.getChecksumUrl());

                Bootstrap.getLog().debug("Checking hash on {}", curFile.getName());
                Bootstrap.getLog().trace("Checksum Url: {}", cur.getChecksumUrl());
                Bootstrap.getLog().trace("Local : {}", local);
                Bootstrap.getLog().trace("Remote: {}", remote);

                if (!validChecksum(remote)) {
                    Bootstrap.getLog().debug("The remote checksum is not a valid sha1 hash ({})", remote);
                    continue;
                }

                if (remote != null && !local.equalsIgnoreCase(remote)) {
                    throw new InstallerException(String.format("Local and remote files do not match (%s)", curFile.getName()));
                }
            } catch (FileNotFoundException e) {
                Bootstrap.getLog().debug("Download for {} did not provide a sha1 hash.", curFile.getName());
            } catch (IOException ex) {
                Bootstrap.getLog().error("Error getting checksum for file {} ({})", curFile.getName(), ex.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw new InstallerException(e);
            }
        }

        return true;
    }

    private boolean validChecksum(String remote) {
        return remote.length() == 40;
    }

}
