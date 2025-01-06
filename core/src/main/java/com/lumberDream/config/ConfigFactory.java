package com.lumberDream.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.lumberDream.config.objects.ConfigObject;

public class ConfigFactory {


    public static ConfigMap getMapConfig() {
        JsonValue value = new JsonReader().parse(Gdx.files.internal("configs/map.json"));
        return new Json().fromJson(ConfigMap.class,
            value.toJson(JsonWriter.OutputType.json));
    }

    public static ConfigObject getObjectConfig() {
        JsonValue value = new JsonReader().parse(Gdx.files.internal("configs/object.json"));
        return new Json().fromJson(ConfigObject.class,
            value.toJson(JsonWriter.OutputType.json));
    }

}
