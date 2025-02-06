package com.lumberDream.entity.states.movement;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public interface MovementState {

    List<Sprite> getSpriteList(float x, float y, boolean flip);

    MovementStateTypes getType();

}
