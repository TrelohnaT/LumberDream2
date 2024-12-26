package com.lumberDream.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public interface Entity {
    String getId();

    float getX();

    float getY();

    Sprite getSprite();

    Rectangle getHitBox();

    /**
     * If some obstacle is hit, jump to the before position
     */
    void hitObstacle();

    void update();

    void load();

    void clear();
}
