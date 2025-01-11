package com.lumberDream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lumberDream.config.ConfigFactory;
import com.lumberDream.entity.Entity;
import com.lumberDream.entity.Player;
import com.lumberDream.handlers.UiHandler;
import com.lumberDream.handlers.ViewStuffHandler;
import com.lumberDream.object.MyObject;
import com.lumberDream.object.MyObjectHandler;
import com.lumberDream.tile.BackGroundManager;
import com.lumberDream.utils.HitBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private final Game agame;

    private BackGroundManager backGroundManager;
    private ViewStuffHandler viewStuffHandler;
    private MyObjectHandler objectHandler;
    private SpriteBatch spriteBatch;

    private ShapeRenderer sr;
    private Stage stage;
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
        objectHandler = new MyObjectHandler(
            ConfigFactory.getObjectConfig(),
            350,
            200
        );

        sr = new ShapeRenderer();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        stage.addActor(uiHandler.getUi());
        stage.setDebugAll(true);
        // init entities
        entityMap.put(
            "player",
            new Player(
                "player",
                "atlasPlayerMove/playerMove.atlas",
                500,
                -500
            )
        );

        backGroundManager = new BackGroundManager(
            700,
            400,
            ConfigFactory.getMapConfig()
        );

    }

    @Override
    public void render(float delta) {
        Main.timeElapsed += delta;

        Entity player = this.entityMap.get("player");

        handleViewPort(player);

        // test switching to next screen
        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            agame.setScreen(new SecondScreen(agame));
        }

        handleCollisions();

        updateEntities();

        drawNonUiStuff(player);

        drawUi();

        // for debugging
        drawHitBoxes();

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewStuffHandler.resize(width, height);
        stage.getViewport().update(width, height, true);
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

    private void handleViewPort(Entity player) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewStuffHandler.getViewport().getCamera().update();
        viewStuffHandler.getViewport().apply();
        spriteBatch.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);

        // camera follows player character
        // we want player to be in the middle of viewport
        // players position is in the left bottom corner
        viewStuffHandler.moveCamera(player.getX() + 0.5f, player.getY() + 0.5f);

    }

    private void handleCollisions() {
        for(Entity entityChecked : this.entityMap.values()) {
            // collision between objects
            for(MyObject myObject : this.objectHandler.getObjectMap().values()) {
                for(HitBox hitBox : myObject.getHitBoxList()) {
                    if(entityChecked.getHitBox().overlaps(hitBox.rectangle)) {
                        if (hitBox.type == HitBox.types.UnEnterAble) {
                            entityChecked.hitObstacle();
                        }
                        if (hitBox.type == HitBox.types.EnterAble) {
                            System.out.println("tree : " + myObject.getTile().getId() + " can be cut down");

                        }
                    }
                }

            }
        }

    }

    private void updateEntities() {
        entityMap.forEach((id, entity) -> entity.update());

    }

    private void drawNonUiStuff(Entity player) {
        spriteBatch.begin();
        if (this.backGroundManager != null) {
            // first background
            this.backGroundManager.getTileMap(player.getX(), player.getY())
                .forEach((id, tile) -> tile.getSprite().draw(spriteBatch));

        }

        if(this.objectHandler != null) {
            this.objectHandler.getObjectMap(player.getX(), player.getY())
                .forEach((id, myObj) -> myObj.getTile().getSprite().draw(spriteBatch));
        }

        entityMap.forEach((id, entity) -> entity.getSprite().draw(spriteBatch));

        spriteBatch.end();
    }


    private void drawUi() {
        //stage.setViewport(viewStuffHandler.getViewport());
        // UI will be rendered last
        stage.act();
        stage.draw();

    }

    private void drawHitBoxes() {
        sr.setProjectionMatrix(viewStuffHandler.getViewport().getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (var entry : entityMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.setColor(new Color(0, 0, 1, 0));
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }
        for(MyObject myObject : objectHandler.getObjectMap().values()) {
            for(HitBox hitBox : myObject.getHitBoxList()) {
                if(hitBox.type == HitBox.types.UnEnterAble) {
                    Rectangle tmp = hitBox.rectangle;
                    sr.setColor(new Color(1, 0, 0, 0));
                    sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
                } else if(hitBox.type == HitBox.types.EnterAble) {
                    Rectangle tmp = hitBox.rectangle;
                    sr.setColor(new Color(0, 1, 0, 0));
                    sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
                }
            }

        }

        sr.setColor(new Color(0,0,0,0));
        sr.rect(
            viewStuffHandler.getActualViewPort().x,
            viewStuffHandler.getActualViewPort().y,
            viewStuffHandler.getActualViewPort().width,
            viewStuffHandler.getActualViewPort().height
        );

        sr.end();

    }

}
