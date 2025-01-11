package com.lumberDream.object;

import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.tile.Tile;


public interface MyObject {

    Tile getTile();

    Rectangle getHitBox();

}
