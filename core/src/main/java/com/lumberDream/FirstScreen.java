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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
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
    Vector3 cameraVector = new Vector3();
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

        Table bottomHotBar = new Table();
        TextButton tool1 = new TextButton("tool1", skin);
        bottomHotBar.add(tool1).width(100).height(50);
        TextButton tool2 = new TextButton("tool2", skin);
        bottomHotBar.add(tool2).width(100).height(50);

        root.add(bottomHotBar).expandY().bottom();

        stage.addActor(root);
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


        // camera follows player character
        Entity player = this.entityMap.get("player");
        // we want player to be in the middle of viewport
        // players position is in the left bottom corner
        cameraVector.x = player.getX() + 0.5f;
        cameraVector.y = player.getY() + 0.5f;
        viewport.getCamera().position.lerp(cameraVector, 0.1f);



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

        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(0, 0, 1, 0));
        for (var entry : entityMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }
        sr.end();
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
