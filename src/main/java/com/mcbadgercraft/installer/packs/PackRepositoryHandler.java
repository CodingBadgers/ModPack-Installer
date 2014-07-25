package com.mcbadgercraft.installer.packs;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.mcbadgercraft.installer.config.ArtifactId;
import com.mcbadgercraft.installer.config.PackConfig;
import com.mcbadgercraft.installer.config.json.ArtifactIdAdapter;
import com.mcbadgercraft.installer.config.json.FileAdapter;
import com.mcbadgercraft.installer.config.json.UUIDAdapter;
import com.mcbadgercraft.installer.packs.exception.RepositoryException;
import com.mcbadgercraft.installer.packs.json.ModPackAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class PackRepositoryHandler {

    private static final Gson GSON = new GsonBuilder()
                                            .setPrettyPrinting()
                                            .registerTypeAdapter(ArtifactId.class, new ArtifactIdAdapter())
                                            .registerTypeAdapter(UUID.class, new UUIDAdapter())
                                            .registerTypeAdapter(File.class, new FileAdapter())
                                            .registerTypeAdapter(ModPack.class, new ModPackAdapter())
                                            .create();

    private File file;
    private List<PackRepository> repositories = Lists.newArrayList();

    public PackRepositoryHandler() {}

    public PackRepositoryHandler(File file) {
        this.file = file;
    }

    public List<ModPack> getPacks() {
        List<ModPack> packs = Lists.newArrayList();

        for (PackRepository repo : repositories) {
            packs.addAll(repo.getPacks());
        }

        return packs;
    }

    public ModPack getPack(String name) {
        Preconditions.checkNotNull(name);

        ModPack pack;

        for (PackRepository repo : repositories) {
            pack = repo.getPack(name);

            if (pack != null) {
                return pack;
            }
        }

        return null;
    }

    public PackRepository getRepositoryForPack(ModPack pack) {
        Preconditions.checkNotNull(pack);

        for (PackRepository repo : repositories) {
            pack = repo.getPack(pack.getName());

            if (pack != null) {
                return repo;
            }
        }

        return null;
    }


    public PackConfig getFullInfo(ModPack pack) {
        PackRepository repo = getRepositoryForPack(pack);

        if (repo == null) {
            throw new IllegalArgumentException("That mod pack is not contained in any known repository");
        }

        URL url = repo.buildUrl(pack);
        System.out.println(url);

        try (InputStreamReader writer = new InputStreamReader(url.openConnection().getInputStream())) {
            return GSON.fromJson(writer, PackConfig.class);
        } catch (IOException e) {
             throw new RepositoryException (e);
        }
    }

    public void registerPackRepository(PackRepository repository) {
        repository.load(GSON);
        this.repositories.add(repository);

        this.save();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
        } catch (IOException ex) {
            throw new RepositoryException("Could not save repository handler to disk", ex);
        }
    }
}
