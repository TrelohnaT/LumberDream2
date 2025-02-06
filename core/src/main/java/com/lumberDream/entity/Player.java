package com.lumberDream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.entity.states.activities.ActivityCutWood;
import com.lumberDream.entity.states.activities.ActivityNone;
import com.lumberDream.entity.states.activities.ActivityState;
import com.lumberDream.entity.states.activities.ActivityStateType;
import com.lumberDream.entity.states.movement.*;
import com.lumberDream.object.MyObject;
import com.lumberDream.object.MyObjectTypes;
import com.lumberDream.utils.NameLib;

import java.util.LinkedList;
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

    private MovementState movementState;
    private ActivityState activityState;

    private boolean faceRight = false;
    private boolean interacting = false;

    private final Rectangle hitBox;

    public Player(
        String id,
        float x,
        float y
    ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.sizeX = 128;//tmp.getWidth(); //ToDo this might make some issues
        this.sizeY = 128;//tmp.getHeight();
        this.hitBox = new Rectangle(x - this.sizeX / 2, y - this.sizeY / 2, this.sizeX, this.sizeY);

        this.movementState = new IdleState(NameLib.idleAnimationAtlas);
        this.activityState = new ActivityNone();

    }

    private void changeMovementState(MovementStateTypes newState) {
        switch (newState) {
            case idle -> this.movementState = new IdleState(NameLib.idleAnimationAtlas);
            case walkHorizontal -> this.movementState = new WalkHorizontalState(NameLib.horizontalAnimationAtlas);
            case walkDown -> this.movementState = new WalkDownState(NameLib.downAnimationAtlas);
            case walkUp -> this.movementState = new WalkUpState(NameLib.upAnimationAtlas);
        }
    }

    private void changeActivityState(ActivityStateType newState, MyObject interactAble) {
        switch (newState) {
            case none -> this.activityState = new ActivityNone();
            case cutWood -> this.activityState = new ActivityCutWood(interactAble);
        }

        System.out.println(this.activityState.getType());
    }

    public void update() {

        this.previousX = x;
        this.previousY = y;

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean idle = true;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.y += this.speed * deltaTime;
            this.changeMovementState(MovementStateTypes.walkUp);
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.y += this.speed * deltaTime * (-1);
            this.changeMovementState(MovementStateTypes.walkDown);
            idle = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.x += this.speed * deltaTime * (-1);
            this.changeMovementState(MovementStateTypes.walkHorizontal);
            this.faceRight = false;
            idle = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.x += this.speed * deltaTime;
            this.faceRight = true;
            this.changeMovementState(MovementStateTypes.walkHorizontal);
            idle = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.activityState.interact();
            interacting = true;
        } else {
            interacting = false;
        }

        // if no movement, switch to idle animation
        if (idle) {
            changeMovementState(MovementStateTypes.idle);
        } else {
            // move hitBox
            this.hitBox.setPosition(this.x - this.sizeX / 2, this.y - this.sizeY / 2);
        }
    }

    @Override
    public List<Sprite> getSpriteList() {
        if (interacting) {
            return new LinkedList<>();
        } else {
            return movementState.getSpriteList(this.x, this.y, this.faceRight);
        }
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
    public void inReacOfInteraction(MyObject interactAble) {
        if (interactAble == null) {
            this.changeActivityState(ActivityStateType.none, null);
            return;

        }

        if (MyObjectTypes.tree.equals(interactAble.getType())) {
            this.changeActivityState(ActivityStateType.cutWood, interactAble);
        }

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
