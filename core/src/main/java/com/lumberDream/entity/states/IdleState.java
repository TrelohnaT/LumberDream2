package com.lumberDream.entity.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lumberDream.utils.AnimationHandler;
import com.lumberDream.utils.NameLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdleState implements PlayerState{

    private final StateTypes type = StateTypes.idle;

    private final AnimationHandler animation;

    public IdleState(String atlasPath) {

        Map<String, Float> idleSpeed = new HashMap<>();
        idleSpeed.put(NameLib.body, 0.5f);
        idleSpeed.put(NameLib.hands, 0.5f);
        idleSpeed.put(NameLib.legs, 1f);
        idleSpeed.put(NameLib.head, 0.5f);
        animation = new AnimationHandler(atlasPath, idleSpeed);
    }

    @Override
    public List<Sprite> getSpriteList(float x, float y, boolean flip) {
        return this.animation.getSpriteList(x,y,flip);
    }


    @Override
    public StateTypes getType() {
        return this.type;
    }


}
