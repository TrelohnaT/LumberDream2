package com.lumberDream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundTile implements Tile{

    private final String id;
    private final String textureName;
    private boolean isLoaded = false;

    private Sprite sprite;
    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    public BackgroundTile(String id, String textureName, Sprite sprite, float x, float y) {
        this.id = id;
        this.textureName = textureName;
        this.x = x * sprite.getWidth();
        this.y = y * sprite.getWidth();
        // we need to load sprite once to know its width and height
        this.load(sprite);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTextureName() {
        return this.textureName;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
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
        //ToDo grass will be animated, this is just place holder
//        Sprite tmp = new Sprite(this.atlas.createSprite("grass_bg"));
//        // grass tile was multiplied only 2x
//        tmp.setSize(1, 1);
//        tmp.translateX(this.x/scale);
//        tmp.translateY(this.y/scale);
        return sprite;
    }

    @Override
    public void load(Sprite sprite) {
        if(!isLoaded || this.sprite == null) {
            this.sprite = sprite;
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
            //sprite.setSize(1, 1);
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
