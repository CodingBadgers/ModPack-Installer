package com.mcbadgercraft.installer.packs;

import com.google.gson.Gson;

import java.net.URL;
import java.util.List;

/**
 * Represents a list of modpacks available for this installer to install.
 */
public interface PackRepository {

    /**
     * Load the data from this repository into memory.
     *
     * @param gson the current Gson instance
     */
    public void load(Gson gson);

    /**
     * Get all the modpacks available in this repository.
     *
     * @return a list of all the modpacks in this repository
     */
    public List<ModPack> getPacks();

    /**
     * Get a specific modpack from this repository.
     *
     * @param pack the modpack's name
     * @return the modpack, or null if this repository cannot find a modpack with that name
     */
    public ModPack getPack(String pack);

    /**
     * Build the url for the latest version of this modpack.
     *
     * @param pack the pack to lookup
     * @return the url for the latest version of this modpack
     */
    public URL buildUrl(ModPack pack);

}
