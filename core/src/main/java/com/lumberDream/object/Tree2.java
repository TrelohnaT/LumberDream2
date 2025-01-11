package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.lumberDream.tile.Tile;
import com.lumberDream.tile.Tree;


public class Tree2 implements MyObject {

    private final String id;
    private final String type;
    private final float x;
    private final float y;
    private final float sizeX;
    private final float sizeY;

    private final Tile tile;
    private final Rectangle hitBox;

    public Tree2(String id, String type, float x, float y, Sprite sprite) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;

        this.tile = new Tree(id, type, x, y, sprite);

        this.sizeX = sprite.getWidth();
        this.sizeY = sprite.getHeight();

        // tree hit box will be only on the trunk of the tree
        this.hitBox = new Rectangle(
            (int) tile.getX() + this.sizeX/4,
            (int) tile.getY(),
            this.sizeX/2,
            this.sizeY/2
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
}
