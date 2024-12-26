package com.lumberDream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lumberDream.entity.Entity;
import com.lumberDream.entity.Player;
import com.lumberDream.tile.BackGroundManager;

import java.util.HashMap;
import java.util.Map;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private final Game agame;
    OrthographicCamera camera;
    ExtendViewport viewport;

    BackGroundManager backGroundManager;

    SpriteBatch spriteBatch;
    Stage stage;
    Skin skin;

    private final Map<String, Entity> entityMap = new HashMap<>();

    public FirstScreen(Game agame) {
        this.agame = agame;
    }

    @Override
    public void show() {
        // Prepare your screen here.
        spriteBatch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(9, 7 * (h / w));
        viewport = new ExtendViewport(9, 7, camera);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        Button button = new Button(skin);
        button.add(new Label("aaa", skin));

        root.add(button).width(100).height(50).center();

        stage.addActor(root);

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

        backGroundManager = new BackGroundManager(1, 1);
        backGroundManager.generateBackground("grass_bg", "background/background.atlas");
    }

    @Override
    public void render(float delta) {

        Main.timeElapsed += Gdx.graphics.getDeltaTime();
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.getCamera().update();
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            agame.setScreen(new SecondScreen(agame));
        }

        stage.act();
        stage.draw();


        entityMap.forEach((id, entity) -> entity.update());

        spriteBatch.begin();

        this.backGroundManager.getTileMap().forEach((id, tile) -> tile.getSprite().draw(spriteBatch));

        entityMap.forEach((id, entity) -> entity.getSprite().draw(spriteBatch));
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
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
        skin.dispose();

        entityMap.forEach((id, entity) -> entity.clear());

    }

    private void handleZoom(float zoom) {
        camera.zoom = zoom / 10;
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100 / camera.viewportWidth);
    }

}
