package com.lumberDream.config;

import com.lumberDream.config.biomes.ConfigBiome;

import java.util.List;

public class ConfigMap {

    public int width; // width is universal for all biomes
    public int height;
    public String pathToAtlas;

    public List<ConfigBiome> biomes;

}

