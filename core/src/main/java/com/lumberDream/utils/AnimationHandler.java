package com.lumberDream.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lumberDream.Main;

import java.util.*;

public class AnimationHandler implements Disposable {

    public final Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();

    private TextureAtlas atlas;

    public AnimationHandler(Array<TextureAtlas.AtlasRegion> textureRegion, Map<String, Float> animationSpeedMap) {
        textureRegion.forEach(region -> {
            System.out.println(region.name);
            animationMap.put(
                region.name,
                new Animation<>(animationSpeedMap.get(region.name), atlas.findRegions(region.name))
            );

        });

    }

    public AnimationHandler(String atlasPath, Map<String, Float> animationSpeedMap) {
        this.atlas = new TextureAtlas(atlasPath);

        atlas.getRegions().forEach(region -> {
            System.out.println(region.name);
            animationMap.put(
                region.name,
                new Animation<>(animationSpeedMap.get(region.name), atlas.findRegions(region.name))
            );

        });
    }

    public List<Sprite> getSpriteList(float x, float y) {
        List<Sprite> tmpList = new LinkedList<>();
        this.animationMap.forEach((name, animation) -> {
            Sprite tmp = new Sprite(animation.getKeyFrame(Main.timeElapsed, true));
            tmp.translateX(x - tmp.getWidth() / 2);
            tmp.translateY(y - tmp.getHeight() / 2);
            tmpList.add(tmp);
        });
        return tmpList;
    }

    public Optional<Animation<TextureRegion>> getAnimation(String name) {
        if (this.animationMap.containsKey(name)) {
            return Optional.of(this.animationMap.get(name));
        }
        return Optional.empty();
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
        animationMap.clear();

    }
}
