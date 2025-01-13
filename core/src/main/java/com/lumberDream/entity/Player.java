package com.lumberDream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.utils.AnimationHandler;
import com.lumberDream.utils.BodyAnimationParts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Entity {


    private final String idle_animation = "idle_animation";
    private final String frontWalk = "front_walk";
    private final String backWalk = "back_walk";
    private final String leftWalk = "left_walk";
    private final String rightWalk = "right_walk";

    private final AnimationHandler idleAnimation;
    private final AnimationHandler frontWalkAnimation;

    private String id = "";
    private float x = 0; // not pixels but tiles
    private float y = 0; // not pixels but tiles
    private float previousX = 0;
    private float previousY = 0;

    private float sizeX = 0;
    private float sizeY = 0;

    private final float speed = 250f;


    private final String atlasPath;
    private TextureAtlas atlas;
    private String currentAnimation = idle_animation;

    private final Rectangle hitBox;

    public Player(
        String id,
        String atlasPath,
        float x,
        float y
    ) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.load();
        //Sprite tmp = new Sprite(this.atlas.createSprite("idle_animation"));
        this.sizeX = 128;//tmp.getWidth();
        this.sizeY = 128;//tmp.getHeight();
        this.hitBox = new Rectangle(x - this.sizeX / 2, y - this.sizeY / 2, this.sizeX, this.sizeY);

        Map<String, Float> a = new HashMap<>();
        a.put(BodyAnimationParts.idle + "_" + BodyAnimationParts.body, 0.5f);
        a.put(BodyAnimationParts.idle + "_" + BodyAnimationParts.head, 0.5f);
        a.put(BodyAnimationParts.idle + "_" + BodyAnimationParts.hands, 0.5f);
        a.put(BodyAnimationParts.idle + "_" + BodyAnimationParts.legs, 1f);

        idleAnimation = new AnimationHandler("playerAnimations/idle/idle.atlas", a);

        Map<String, Float> b = new HashMap<>();
        b.put(BodyAnimationParts.front + "_" + BodyAnimationParts.body, 1 / 15f);
        b.put(BodyAnimationParts.front + "_" + BodyAnimationParts.head, 0.5f);
        b.put(BodyAnimationParts.front + "_" + BodyAnimationParts.hands, 1 / 15f);
        b.put(BodyAnimationParts.front + "_" + BodyAnimationParts.legs, 1 / 15f);

        frontWalkAnimation = new AnimationHandler("playerAnimations/front/walk_front.atlas", b);

    }

    public void update() {

        this.previousX = x;
        this.previousY = y;

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean idle = true;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += this.speed * deltaTime;
            currentAnimation = backWalk;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y += this.speed * deltaTime * (-1);
            currentAnimation = frontWalk;
            idle = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.x += this.speed * deltaTime * (-1);
            currentAnimation = leftWalk;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += this.speed * deltaTime;
            currentAnimation = rightWalk;
            idle = false;
        }

        // if no movement, switch to idle animation
        if (idle) {
            currentAnimation = idle_animation;
        } else {
            // move hitBox
            this.hitBox.setPosition(this.x - this.sizeX / 2, this.y - this.sizeY / 2);
        }
    }

    @Override
    public List<Sprite> getSpriteList() {
        if (currentAnimation.equals(idle_animation)) {
            return idleAnimation.getSpriteList(this.x, this.y);
        } else if (currentAnimation.equals(frontWalk)) {
            return frontWalkAnimation.getSpriteList(this.x, this.y);
        } else if (currentAnimation.equals(backWalk)) {

        } else if (currentAnimation.equals(leftWalk)) {

        } else if (currentAnimation.equals(rightWalk)) {

        }
        return List.of();
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public void hitObstacle() {
        this.x = previousX;
        this.y = previousY;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public void load() {
        //this.atlas = new TextureAtlas(atlasPath);

    }

    @Override
    public void clear() {
        //this.atlas.dispose();
    }
}
