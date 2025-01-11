package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.tile.BackgroundTile;
import com.lumberDream.tile.Tile;


public class Tree2 implements MyObject {


    private final Tile tile;
    private final Rectangle hitBox;

    public Tree2(String id, String type, float x, float y, Sprite sprite) {

        this.tile = new BackgroundTile(
            id,
            type,
            sprite,
            x,
            y * -1
        );

        // tree hit box will be only on the trunk of the tree
        this.hitBox = new Rectangle(
            (int) tile.getX() + this.tile.getWidth() / 4,
            (int) tile.getY(),
            this.tile.getWidth() / 2,
            this.tile.getHeight() / 2
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
    public void load(Sprite sprite) {
        this.tile.load(sprite);
    }

    @Override
    public void unload() {
        this.tile.unload();
    }
}
