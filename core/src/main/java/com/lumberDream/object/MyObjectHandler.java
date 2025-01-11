package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lumberDream.config.objects.ConfigObject;
import com.lumberDream.config.objects.ConfigTree;
import com.lumberDream.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class MyObjectHandler {

    private float renderDistanceX;
    private float renderDistanceY;

    private final Map<String, MyObject> objectMap = new HashMap<>();
    private final ConfigObject configObject;
    private final TextureAtlas atlas;

    public MyObjectHandler(
        ConfigObject configObject,
        float renderDistanceX,
        float renderDistanceY
    ) {
        this.renderDistanceX = renderDistanceX;
        this.renderDistanceY = renderDistanceY;
        this.configObject = configObject;
        this.atlas = new TextureAtlas(configObject.atlas);

        load();
    }

    private void load() {
        for(ConfigTree configTree : configObject.trees) {
            String type = configTree.type;
            int count = configTree.count;

            int randomX = 2;
            int randomY = 2;

            for(int i = 0; i < count; i++) {

                String tmp = "tree_" + type + "_" + randomX + "_" + randomY;
                objectMap.put(tmp, new Tree2("test", type, randomX, randomY, atlas.createSprite(type)));

            }
        }
    }
    public Map<String, MyObject> getObjectMap(float playerX, float playerY) {
        Map<String, MyObject> tmp = new HashMap<>();
        this.objectMap.forEach((id, myObject) -> {
            float toPlayerX = Math.abs(playerX - myObject.getTile().getX());
            float toPlayerY = Math.abs(playerY - myObject.getTile().getY());
            if (toPlayerX < (myObject.getTile().getWidth() * renderDistanceX) / 100f
                && toPlayerY < (myObject.getTile().getHeight() * renderDistanceY) / 100f) {
                // if myObject is visible load it
                myObject.load(new Sprite(this.atlas.createSprite(myObject.getTile().getTextureName())));
                tmp.put(id, myObject);
            } else {
                // if not unload it
                myObject.unload();
            }
        });
        return tmp;
    }

    public Map<String, MyObject> getObjectMap() {
        return this.objectMap;
    }

}
