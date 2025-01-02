package com.lumberDream.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class ViewStuffHandler {


    private OrthographicCamera camera;
    private Vector3 cameraVector = new Vector3();
    private ExtendViewport viewport;


    public ViewStuffHandler() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(9, 7 * (h / w));
        viewport = new ExtendViewport(9, 7, camera);

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void moveCamera(float x, float y) {
        cameraVector.x = x + 0.5f;
        cameraVector.y = y + 0.5f;
        viewport.getCamera().position.lerp(cameraVector, 0.1f);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public ExtendViewport getViewport() {
        return viewport;
    }

}
