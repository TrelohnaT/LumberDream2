package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.tile.BackgroundTile;
import com.lumberDream.tile.Tile;
import com.lumberDream.utils.HitBox;

import java.util.LinkedList;
import java.util.List;


public class Tree2 implements MyObject {


    private final Tile tile;
    private List<HitBox> hitBoxList = new LinkedList<>();
    private Rectangle hitBox;

    public Tree2(String id, String type, float x, float y, Sprite sprite) {

        this.tile = new BackgroundTile(
            id,
            type,
            sprite,
            x,
            y * -1
        );

        hitBoxList.add(
            new HitBox(
                "trunk",
                HitBox.types.UnEnterAble,
                new Rectangle(
                    (int) tile.getX() + this.tile.getWidth() / 4,
                    (int) tile.getY(),
                    this.tile.getWidth() / 2,
                    this.tile.getHeight() / 2
                )
            )
        );

        hitBoxList.add(
            new HitBox(
                "cutDownArea",
                HitBox.types.EnterAble,
                new Rectangle(
                    (int) tile.getX(),
                    (int) tile.getY(),
                    this.tile.getWidth(),
                    this.tile.getHeight() / 2
                )
            )
        );

    }

    @Override
    public Tile getTile() {
        return tile;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public List<HitBox> getHitBoxList() {
        return this.hitBoxList;
    }

    @Override
    public void load(Sprite sprite) {
        this.tile.load(sprite);
    }

    @Override
    public void unload() {
        this.tile.unload();
    }
}
