package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.tile.Tile;
import com.lumberDream.utils.HitBox;

import java.util.List;


public interface MyObject {

    Tile getTile();

    Rectangle getHitBox();
    List<HitBox> getHitBoxList();
    void load(Sprite sprite);
    void unload();

}
