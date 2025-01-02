package com.lumberDream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lumberDream.entity.Entity;
import com.lumberDream.entity.Player;
import com.lumberDream.handlers.UiHandler;
import com.lumberDream.handlers.ViewStuffHandler;
import com.lumberDream.tile.BackGroundManager;

import java.util.HashMap;
import java.util.Map;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private final Game agame;

    BackGroundManager backGroundManager;

    ViewStuffHandler viewStuffHandler;

    SpriteBatch spriteBatch;
    Stage stage;

    private UiHandler uiHandler;
    private final Map<String, Entity> entityMap = new HashMap<>();

    public FirstScreen(Game agame) {
        this.agame = agame;
    }

    @Override
    public void show() {
        // Prepare your screen here.
        spriteBatch = new SpriteBatch();
        uiHandler = new UiHandler();
        viewStuffHandler = new ViewStuffHandler();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(uiHandler.getUi());
        stage.setDebugAll(true);
        // init entities
        entityMap.put(
            "player",
            new Player(
                "player",
                "atlasPlayerMove/playerMove.atlas",
                0,
                0,
                1,
                1
            )
        );

        backGroundManager = new BackGroundManager(
            -6,
            -4,
            6,
            4,
            BackGroundManager.mapBlueprint,
            "background/background.atlas");
        //backGroundManager = new BackGroundManager(1, 1);
        //backGroundManager.generateBackground("grass_bg", "background/background.atlas");
    }

    @Override
    public void render(float delta) {

        Main.timeElapsed += Gdx.graphics.getDeltaTime();
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewStuffHandler.getViewport().getCamera().update();
        viewStuffHandler.getViewport().apply();
        spriteBatch.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);


        // camera follows player character
        Entity player = this.entityMap.get("player");
        // we want player to be in the middle of viewport
        // players position is in the left bottom corner
        viewStuffHandler.moveCamera(player.getX() + 0.5f, player.getY() + 0.5f);


        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            agame.setScreen(new SecondScreen(agame));
        }

        entityMap.forEach((id, entity) -> entity.update());

        spriteBatch.begin();

        if (this.backGroundManager != null) {
            this.backGroundManager.getTileMap(player.getX(), player.getY())
                .forEach((id, tile) -> tile.getSprite().draw(spriteBatch));
        }
        entityMap.forEach((id, entity) -> entity.getSprite().draw(spriteBatch));
        spriteBatch.end();

        // UI will be rendered last
        stage.act();
        stage.draw();

        //ToDo cause memory leak
/*
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(0, 0, 1, 0));
        for (var entry : entityMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }
        sr.end();*/
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewStuffHandler.resize(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        entityMap.forEach((id, entity) -> entity.clear());
        spriteBatch.dispose();
        stage.dispose();
        uiHandler.dispose();
        uiHandler = null;
        viewStuffHandler = null;

    }

}
