package com.lumberDream.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class UiHandler {

    private final Skin skin;

    public UiHandler() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }
    // ToDo resizing is not working good

    public Table getUi() {

        Table root = new Table();
        root.setFillParent(true);

        Table bottomHotBar = new Table();
        TextButton tool1 = new TextButton("tool1", skin);
        bottomHotBar.add(tool1).width(100).height(50);
        TextButton tool2 = new TextButton("tool2", skin);
        bottomHotBar.add(tool2).width(100).height(50);

        root.add(bottomHotBar).expandY().bottom();

        return root;

    }

    public void dispose() {
        this.skin.dispose();

    }

}
