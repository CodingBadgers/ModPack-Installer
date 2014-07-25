package com.mcbadgercraft.installer.packs;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mcbadgercraft.installer.packs.exception.RepositoryException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public abstract class JsonPackRepository implements PackRepository {

    private transient List<ModPack> packs;

    @Override
    public void load(Gson gson) {
        this.packs = Lists.newArrayList();

        try (InputStreamReader reader = new InputStreamReader(getContentUrl().openConnection().getInputStream())) {
            JsonObject object = new JsonParser().parse(reader).getAsJsonObject();

            Iterable<JsonObject> packs = Iterables.transform(object.get("packs").getAsJsonArray(), new Function<JsonElement, JsonObject>() {
                @Override
                public JsonObject apply(JsonElement input) {
                    return input.getAsJsonObject();
                }
            });

            for (JsonObject current : packs) {
                this.packs.add(gson.fromJson(current, ModPack.class));
            }
        } catch (IOException ex) {
            throw new RepositoryException("Could not load data from " + getContentUrl(), ex);
        }
    }

    @Override
    public List<ModPack> getPacks() {
        return this.packs;
    }

    @Override
    public ModPack getPack(String name) {
        for (ModPack pack : this.packs) {
            if (pack.getName().equalsIgnoreCase(name)) {
                return pack;
            }
        }

        return null;
    }

    @Override
    public URL buildUrl(ModPack pack) {
        try {
            return new URL(getBaseUrl() + pack.getPath());
        } catch (MalformedURLException e) {
            throw new RepositoryException(e);
        }
    }

    public abstract URL getContentUrl();

    public abstract URL getBaseUrl();

}
