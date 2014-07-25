package com.mcbadgercraft.installer.packs.json;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcbadgercraft.installer.packs.ModPack;
import com.mcbadgercraft.installer.packs.ModPack.Version;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestModPackAdapter {

    private static final String TEST_DATA = "{\n" +
            "      \"name\": \"AdminPack\",\n" +
            "      \"description\": \"Admin modpack for 1.7.2\",\n" +
            "      \"latest\":\n" +
            "      {\n" +
            "        \"minecraft\": \"1.7.2\",\n" +
            "        \"pack\": \"b\"\n" +
            "      },\n" +
            "      \"versions\":\n" +
            "      [\n" +
            "        {\n" +
            "          \"minecraft\": \"1.6.4\",\n" +
            "          \"pack\":\n" +
            "          [\n" +
            "            \"a\"\n" +
            "          ]\n" +
            "        },\n" +
            "        {\n" +
            "          \"minecraft\": \"1.7.2\",\n" +
            "          \"pack\":\n" +
            "          [\n" +
            "            \"a\",\n" +
            "            \"b\"\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }";

    private Gson gson;
    private ModPack pack;

    @Before
    public void setUp() throws Exception {
        gson = new GsonBuilder()
        			.setPrettyPrinting()
                    .registerTypeAdapter(ModPack.class, new ModPackAdapter())
                    .create();

        List<Version> versions = Lists.newArrayList();
        versions.add(new Version("1.6.4", "a"));
        versions.add(new Version("1.7.2", "a"));
        versions.add(new Version("1.7.2", "b"));

        pack = new ModPack("AdminPack", "Admin modpack for 1.7.2", new Version("1.7.2", "b"), versions);
    }

    @Test
    public void testDeserialization() {
        assertEquals(pack, gson.fromJson(TEST_DATA, ModPack.class));
    }

    @Test
    public void testSerialization() {
    	System.out.println(gson.toJson(pack));
    }
}
