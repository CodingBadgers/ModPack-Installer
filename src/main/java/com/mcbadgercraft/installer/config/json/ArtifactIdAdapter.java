package com.mcbadgercraft.installer.config.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mcbadgercraft.installer.config.ArtifactId;

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
