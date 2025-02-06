package com.lumberDream.entity.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lumberDream.utils.AnimationHandler;
import com.lumberDream.utils.NameLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalkUpState implements PlayerState{

    private final StateTypes type = StateTypes.idle;

    private final AnimationHandler animation;

    public WalkUpState(String atlasPath) {

        Map<String, Float> animationSpeed = new HashMap<>();
        animationSpeed.put(NameLib.body, 1 / 15f);
        animationSpeed.put(NameLib.head, 0.5f);
        animationSpeed.put(NameLib.hands, 1 / 15f);
        animationSpeed.put(NameLib.legs, 1 / 15f);
        animation = new AnimationHandler(atlasPath, animationSpeed);
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
