package com.lumberDream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.entity.states.*;
import com.lumberDream.utils.NameLib;

import java.util.List;

public class Player implements Entity {

    private String id = "";
    private float x = 0; // not pixels but tiles
    private float y = 0; // not pixels but tiles
    private float previousX = 0;
    private float previousY = 0;

    private float sizeX = 0;
    private float sizeY = 0;

    private final float speed = 250f;

    private PlayerState state;

    private boolean faceRight = false;

    private final String atlasPath;

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
        this.sizeX = 128;//tmp.getWidth(); //ToDo this might make some issues
        this.sizeY = 128;//tmp.getHeight();
        this.hitBox = new Rectangle(x - this.sizeX / 2, y - this.sizeY / 2, this.sizeX, this.sizeY);

        this.state = new IdleState("playerAnimations/idle/idle.atlas");

    }

    private void changeState(StateTypes newState) {

        switch (newState) {
            case idle -> this.state = new IdleState(NameLib.idleAnimationAtlas);
            case walkHorizontal -> this.state = new WalkHorizontalState(NameLib.horizontalAnimationAtlas);
            case walkDown -> this.state = new WalkDownState(NameLib.downAnimationAtlas);
            case walkUp -> this.state = new WalkUpState(NameLib.upAnimationAtlas);
        }


    }

    public void update() {

        this.previousX = x;
        this.previousY = y;

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean idle = true;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += this.speed * deltaTime;
            this.changeState(StateTypes.walkUp);
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y += this.speed * deltaTime * (-1);
            this.changeState(StateTypes.walkDown);
            idle = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.x += this.speed * deltaTime * (-1);
            this.changeState(StateTypes.walkHorizontal);
            this.faceRight = false;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += this.speed * deltaTime;
            this.faceRight = true;
            this.changeState(StateTypes.walkHorizontal);
            idle = false;
        }

        // if no movement, switch to idle animation
        if (idle) {
            changeState(StateTypes.idle);
        } else {
            // move hitBox
            this.hitBox.setPosition(this.x - this.sizeX / 2, this.y - this.sizeY / 2);
        }
    }

    @Override
    public List<Sprite> getSpriteList() {
        return state.getSpriteList(this.x, this.y, this.faceRight);
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
    }
}
