package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.tile.Tile;


public interface MyObject {

    Tile getTile();

    Rectangle getHitBox();

    void load(Sprite sprite);
    void unload();

}
