package com.mcbadgercraft.installer.config.json;

import com.google.gson.*;

import java.io.File;
import java.lang.reflect.Type;

public class FileAdapter implements JsonDeserializer<File>, JsonSerializer<File> {

    @Override
    public JsonElement serialize(File src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getAbsolutePath());
    }

    @Override
    public File deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new File(json.getAsString());
    }

}
