package com.lumberDream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Tile {

    String getId();
    float getX();
    float getY();
    float getWidth();
    float getHeight();

    Sprite getSprite();

    void load(Sprite sprite);
    void unload();

}
