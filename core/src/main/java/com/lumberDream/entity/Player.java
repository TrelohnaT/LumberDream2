package com.lumberDream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.Main;

public class Player implements Entity {

    private final String idleAnimation = "idle_animation";
    private final String frontWalk = "front_walk";
    private final String backWalk = "back_walk";
    private final String leftWalk = "left_walk";
    private final String rightWalk = "right_walk";

    private String id = "";
    private float x = 0; // not pixels but tiles
    private float y = 0; // not pixels but tiles
    private float previousX = 0;
    private float previousY = 0;

    private float sizeX = 0;
    private float sizeY = 0;

    private final float speed = 150f;


    private final String atlasPath;
    private TextureAtlas atlas;
    private String currentAnimation = idleAnimation;

    private Rectangle hitBox; // ToDo check if this is alright

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
        Sprite tmp = new Sprite(this.atlas.createSprite("idle_animation"));
        this.sizeX = tmp.getWidth();
        this.sizeY = tmp.getHeight();
        this.hitBox = new Rectangle(x - this.sizeX/2, y - this.sizeY/2, this.sizeX, this.sizeY);
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
            currentAnimation = idleAnimation;
        } else {
            // move hitBox
            this.hitBox.setPosition(this.x - this.sizeX/2, this.y - this.sizeY/2);
        }
    }

    @Override
    public Sprite getSprite() {
        Animation<TextureRegion> animation = new Animation<>(1 / 15f, atlas.findRegions(currentAnimation));
        TextureRegion tr = animation.getKeyFrame(Main.timeElapsed, true);
        Sprite tmp = new Sprite(tr);
        tmp.translateX(this.x - tmp.getWidth()/2);
        tmp.translateY(this.y - tmp.getHeight()/2);
        return tmp;
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
        this.atlas = new TextureAtlas(atlasPath);

    }

    @Override
    public void clear() {
        this.atlas.dispose();
    }
}
