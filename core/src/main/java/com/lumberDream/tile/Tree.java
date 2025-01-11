package com.lumberDream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lumberDream.config.objects.ConfigTree;

public class Tree implements Tile{


    private final String id;
    private final String type;
    private boolean isLoaded = false;

    private Sprite sprite;
    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    public Tree(String id, String type, float x, float y, Sprite sprite) {
        this.id = id;
        this.type = type;
        this.x = x * 100;
        this.y = y * 100 * -1; // origin is in TOP left corner
        load(sprite);
    }

    /*public Tree(ConfigTree configTree, Sprite sprite) {
        this.id = configTree.id;
        this.type = configTree.type;
        this.x = configTree.x * 100;
        this.y = configTree.y * 100 * -1; // origin is in TOP left corner
        load(sprite);
    }*/

    public Tree(
        String id,
        String type,
        Sprite sprite,
        float x,
        float y
    ) {
       this.id = id;
       this.type = type; //ToDo this could also determinate texture
       this.x = x;
       this.y = y;
       load(sprite);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTextureName() {
        return "";
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public Sprite getSprite() {
        return this.sprite;
    }

    @Override
    public void load(Sprite sprite) {
        if(!isLoaded || this.sprite == null) {
            this.sprite = sprite;
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
            //sprite.setSize(2, 2);
            sprite.setPosition(this.x, this.y);
            isLoaded = true;
        }
    }

    @Override
    public void unload() {
        if(isLoaded) {
            sprite = null;
            isLoaded = false;
        }
    }
}
