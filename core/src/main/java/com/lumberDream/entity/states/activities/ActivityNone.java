package com.lumberDream.entity.states.activities;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public class ActivityNone implements ActivityState{

    private final ActivityStateType type = ActivityStateType.none;

    public ActivityNone() {

    }

    @Override
    public void interact() {

    }

    @Override
    public List<Sprite> getAnimation() {
        return List.of();
    }

    @Override
    public ActivityStateType getType() {
        return this.type;
    }
}
