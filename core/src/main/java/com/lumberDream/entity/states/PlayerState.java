package com.lumberDream.entity.states;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public interface PlayerState {

    List<Sprite> getSpriteList(float x, float y, boolean flip);

    StateTypes getType();

}
