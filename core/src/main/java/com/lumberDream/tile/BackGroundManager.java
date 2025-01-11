package com.lumberDream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lumberDream.config.ConfigMap;
import com.lumberDream.config.biomes.ConfigBiome;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BackGroundManager {

    private int renderDistanceX = 1;
    private int renderDistanceY = 2;

    private final ConfigMap configMap;
    private TextureAtlas atlas;

    private final Map<String, Tile> tileMap = new HashMap<>();
    private final Map<String, Tile> treeMap = new HashMap<>();

    public BackGroundManager(
        int renderDistanceX,
        int renderDistanceY,
        ConfigMap configMap
    ) {
        this.renderDistanceX = renderDistanceX;
        this.renderDistanceY = renderDistanceY;
        this.configMap = configMap;
        this.atlas = new TextureAtlas(configMap.pathToAtlas); //ToDo this might be also in the config
        this.load();
    }

    private void load() {
        for (int y = 0; y < configMap.height; y++) {
            // ToDo each biome will have its onw grass type
            Optional<ConfigBiome> currentBiome = getBiomeByHeight(y);
            if (currentBiome.isEmpty()) {
                System.out.println("for Y: " + y + " there is no biome");
                break;
            }
            for (int x = 0; x < configMap.width; x++) {
                // generate grass
                String tmp = "grass_" + x + "_" + y;
                this.tileMap.put(
                    tmp,
                    new BackgroundTile(
                        tmp,
                        currentBiome.get().backgroundTileName,
                        new Sprite(this.atlas.createSprite(currentBiome.get().backgroundTileName)),
                        x,
                        y * -1) // point 0,0 is the topLeft corner of the map
                );//"background/background.atlas"));

            }
        }
    }

    private Optional<ConfigBiome> getBiomeByHeight(int y) {
        for (ConfigBiome configBiome : configMap.biomes) {
            if (configBiome.heightOffset <= y && y < configBiome.height) {
                return Optional.of(configBiome);
            }
        }
        return Optional.empty();
    }


    public Map<String, Tile> getTileMap(float playerX, float playerY) {
        Map<String, Tile> tmp = new HashMap<>();
        this.tileMap.forEach((id, tile) -> {
            float toPlayerX = Math.abs(playerX - tile.getX());
            float toPlayerY = Math.abs(playerY - tile.getY());
            if (toPlayerX < (tile.getWidth() * renderDistanceX) / 100f
                && toPlayerY < (tile.getHeight() * renderDistanceY) / 100f) {
                // if tile is visible load it
                tile.load(new Sprite(this.atlas.createSprite(tile.getTextureName())));
                tmp.put(id, tile);
            } else {
                // if not unload it
                tile.unload();
            }
        });
        return tmp;
    }

}
