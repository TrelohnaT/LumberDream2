package com.lumberDream.entity.states.activities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lumberDream.object.MyObject;

import java.util.List;

public interface ActivityState {

    void interact();

    List<Sprite> getAnimation();

    ActivityStateType getType();

}
