package com.lumberDream.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;
import java.util.Map;

public class InputHandler implements InputProcessor {

    public static final String keyW = "w";
    public static final String keyA = "a";
    public static final String keyS = "s";
    public static final String keyD = "d";
    public static final String keySpace = "space";

    private boolean w = false;
    private boolean a = false;
    private boolean s = false;
    private boolean d = false;
    private boolean space = false;

    public float zoom = 5;

    public Map<String, Boolean> getState() {
        Map<String, Boolean> tmp = new HashMap<>();
        tmp.put(InputHandler.keyW, w);
        tmp.put(InputHandler.keyA, a);
        tmp.put(InputHandler.keyS, s);
        tmp.put(InputHandler.keyD, d);
        tmp.put(InputHandler.keySpace, space);
        return tmp;
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.W) {
            w = true;
        }
        if (i == Input.Keys.S) {
            s = true;
        }
        if (i == Input.Keys.A) {
            a = true;
        }
        if (i == Input.Keys.D) {
            d = true;
        }
        if (i == Input.Keys.SPACE) {
            space = true;
        }


        return false;
    }

    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.W) {
            w = false;
        }
        if (i == Input.Keys.S) {
            s = false;
        }
        if (i == Input.Keys.A) {
            a = false;
        }
        if (i == Input.Keys.D) {
            d = false;
        }
        if (i == Input.Keys.SPACE) {
            space = false;
        }


        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        zoom += v1;
        if(zoom < 0) {
            zoom = 0;
        }

        return false;
    }
}
