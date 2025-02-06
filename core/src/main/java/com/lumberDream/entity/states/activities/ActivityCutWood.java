package com.lumberDream.entity.states.activities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lumberDream.object.MyObject;
import com.lumberDream.object.MyObjectTypes;

import java.util.List;

public class ActivityCutWood implements ActivityState {

    private final ActivityStateType type = ActivityStateType.cutWood;

    private final MyObject interactAble;

    public ActivityCutWood(MyObject interactAble) {
        this.interactAble = interactAble;
    }

    @Override
    public void interact() {
        System.out.println("cut down tree: " + interactAble.getTile().getId());
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
