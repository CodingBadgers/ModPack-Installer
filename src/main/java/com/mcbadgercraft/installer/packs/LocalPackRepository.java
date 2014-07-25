package com.mcbadgercraft.installer.packs;

import com.google.gson.annotations.Expose;
import com.mcbadgercraft.installer.packs.exception.RepositoryException;
import lombok.AllArgsConstructor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
public class LocalPackRepository extends JsonPackRepository {

    @Expose
    private File file;

    @Override
    public URL getContentUrl() {
        try {
            return this.file.toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new RepositoryException(ex);
        }
    }

    @Override
    public URL getBaseUrl() {
        try {
            return this.file.getParentFile().toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new RepositoryException(ex);
        }
    }
}
