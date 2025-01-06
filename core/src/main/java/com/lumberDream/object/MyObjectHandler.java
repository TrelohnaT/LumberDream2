package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.lumberDream.config.objects.ConfigObject;
import com.lumberDream.config.objects.ConfigTree;

import java.util.HashMap;
import java.util.Map;

public class MyObjectHandler {

    private final Map<String, MyObject> objectMap = new HashMap<>();
    private final ConfigObject configObject;
    private final TextureAtlas atlas;

    public MyObjectHandler(ConfigObject configObject) {
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

    public Map<String, MyObject> getObjectMap() {
        return this.objectMap;
    }

}
