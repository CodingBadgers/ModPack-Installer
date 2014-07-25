package com.mcbadgercraft.installer.packs;

import com.google.gson.annotations.Expose;
import com.mcbadgercraft.installer.packs.exception.RepositoryException;
import lombok.AllArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
public class RemotePackRepository extends JsonPackRepository {

    @Expose
    private URL url;

    @Override
    public URL getContentUrl() {
        try {
            return new URL(url + "packs.json");
        } catch (MalformedURLException ex) {
            throw new RepositoryException(ex);
        }
    }

    @Override
    public URL getBaseUrl() {
        return this.url;
    }
}
