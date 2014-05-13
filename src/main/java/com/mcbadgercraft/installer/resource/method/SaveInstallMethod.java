package com.mcbadgercraft.installer.resource.method;

import com.mcbadgercraft.installer.config.ResourceInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SaveInstallMethod implements InstallMethod {

    public static final int BUFFER = 1024;

    @Override
    public File createPath(File gamedir, ResourceInfo info) {
        return new File(gamedir, "saves" + File.separator + info.getArtifactId().getName() + info.getFiletype().getExtension());
    }

    @Override
    public File postDownload(File resource, ResourceInfo info) {
        return unzip(resource);
    }

    public File unzip(File zipFile) {
        File outputDir = new File(zipFile.getName().substring(0, zipFile.getName().lastIndexOf('.')));
        byte[] buffer = new byte[BUFFER];

        try{
            //create output directory is not exists
            if(!outputDir.exists()){
                outputDir.mkdir();
            }

            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(outputDir + File.separator + fileName);
                newFile.getParentFile().mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return outputDir;
    }

}
