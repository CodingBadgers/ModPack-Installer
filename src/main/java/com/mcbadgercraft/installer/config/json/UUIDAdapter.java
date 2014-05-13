package com.mcbadgercraft.installer.config.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDAdapter implements JsonSerializer<UUID>, JsonDeserializer<UUID> {

    private static final Pattern UUID_REPLACE = Pattern.compile("-", Pattern.LITERAL);
    private static final Pattern UUID_PARSE = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    public static String format(UUID src) {
        return UUID_REPLACE.matcher(src.toString()).replaceAll("");
    }

    public static UUID parseUuid(String src) {
        return UUID.fromString(UUID_PARSE.matcher(src).replaceFirst("$1-$2-$3-$4-$5"));
    }

    @Override
    public JsonElement serialize(UUID src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(format(src));
    }

    @Override
    public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return parseUuid(json.getAsString());
    }

}
