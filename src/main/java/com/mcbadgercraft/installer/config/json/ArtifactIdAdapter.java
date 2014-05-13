package com.mcbadgercraft.installer.config.json;

import com.google.gson.*;
import com.mcbadgercraft.installer.config.ArtifactId;

import java.lang.reflect.Type;

public class ArtifactIdAdapter implements JsonSerializer<ArtifactId>, JsonDeserializer<ArtifactId> {

    @Override
    public ArtifactId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ArtifactId(json.getAsString());
    }

    @Override
    public JsonElement serialize(ArtifactId src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(String.format("%1$s:%2$s:%3$s", src.getGroup(), src.getName(), src.getVersion()));
    }

}
