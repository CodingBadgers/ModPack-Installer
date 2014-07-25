package com.mcbadgercraft.installer.packs.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.mcbadgercraft.installer.packs.ModPack;
import com.mcbadgercraft.installer.packs.ModPack.Version;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ModPackAdapter implements JsonSerializer<ModPack>, JsonDeserializer<ModPack> {

    @Override
    public ModPack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        String name = object.get("name").getAsString();
        String desc = object.get("description").getAsString();
        JsonObject latest = object.get("latest").getAsJsonObject();

        Version latestVersion = new Version(latest.get("minecraft").getAsString(), latest.get("pack").getAsString());
        List<Version> versions = Lists.newArrayList();

        for (JsonElement cur : object.get("versions").getAsJsonArray()) {
            JsonObject current = cur.getAsJsonObject();

            String mc = current.get("minecraft").getAsString();

            for (JsonElement currentPack : current.get("pack").getAsJsonArray()) {
                versions.add(new Version(mc, currentPack.getAsString()));
            }
        }

        return new ModPack(name, desc, latestVersion, versions);
    }

    @Override
    public JsonElement serialize(ModPack src, Type typeOfSrc, JsonSerializationContext context) {
    	JsonObject json = new JsonObject();
    	
    	json.add("name", new JsonPrimitive(src.getName()));
    	json.add("description", new JsonPrimitive(src.getDescription()));
    	
    	JsonObject latest = new JsonObject();
    	latest.add("minecraft", new JsonPrimitive(src.getLatest().getMinecraft()));
    	latest.add("pack", new JsonPrimitive(src.getLatest().getPack()));
    	
    	json.add("latest", latest);
    	
    	JsonArray versions = new JsonArray();
    	
    	Map<String, JsonArray> versionData = Maps.newHashMap();
    	
    	for (Version version : src.getVersions()) {
    		if (versionData.containsKey(version.getMinecraft())) {
    			versionData.get(version.getMinecraft()).add(new JsonPrimitive(version.getPack()));
    		} else {
    			JsonArray array = new JsonArray();
    			array.add(new JsonPrimitive(version.getPack()));
    			versionData.put(version.getMinecraft(), array);
    		}
    	}
    	
    	for (Map.Entry<String, JsonArray> entry : versionData.entrySet()) {
    		JsonObject object = new JsonObject();
    		object.add("minecraft", new JsonPrimitive(entry.getKey()));
    		object.add("pack", entry.getValue());
    		versions.add(object);
    	}
    	
    	json.add("versions", versions);
    	
        return json;
    }
}
