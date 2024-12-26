package com.lumberDream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.*;

public class BackGroundManager {


    public static List<List<Integer>> mapBlueprint = new LinkedList<>(List.of(Arrays.asList(1, 1, 1),Arrays.asList(1, 1, 1),Arrays.asList(1, 1, 1)));

    private  int tileCountX;
    private  int tileCountY;

    private TextureAtlas atlas;

    private final Map<String, Tile> tileMap = new HashMap<>();


    public BackGroundManager(int tileCountX, int tileCountY) {
        this.tileCountX = tileCountX;
        this.tileCountY = tileCountY;


    }

    public BackGroundManager(List<List<Integer>> mapBlueprint, String atlasPath) {
        this.atlas = new TextureAtlas(atlasPath);
        for(int y = 0; y < mapBlueprint.size(); y++) {
            for(int x = 0; x < mapBlueprint.get(y).size(); x++) {
                if(mapBlueprint.get(y).get(x) == 1) {
                    String tmp = "grass_" + x + "_" + y;
                    this.tileMap.put(tmp, new Grass(tmp,new Sprite(this.atlas.createSprite("grass_bg")), x, y));//"background/background.atlas"));

                }
            }
        }
    }

    public Map<String, Tile> getTileMap(float playerX, float playerY) {
        Map<String, Tile> tmp = new HashMap<>();
        int renderDistance = 2;
        this.tileMap.forEach((id, tile) -> {
            float toPlayerX = Math.abs(playerX - tile.getX());
            float toPlayerY = Math.abs(playerY - tile.getY());
            if(toPlayerX < (tile.getWidth() * renderDistance)/100f && toPlayerY < (tile.getHeight() * renderDistance)/100f) {
                // if tile is visible load it
                tile.load(new Sprite(this.atlas.createSprite("grass_bg")));
                tmp.put(id, tile);
            } else {
                // if not unload it
                tile.unload();
            }
        });
        return tmp;
    }

}
