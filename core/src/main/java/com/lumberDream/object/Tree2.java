package com.lumberDream.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.lumberDream.config.objects.ConfigTree;
import com.lumberDream.tile.Tile;
import com.lumberDream.tile.Tree;

import java.awt.*;

public class Tree2 implements MyObject {

    private  ConfigTree configTree;
    private  Tile tile;
    private Rectangle hitbox;

    public Tree2(String id, String type, float x, float y, Sprite sprite) {
        this.tile = new Tree(id, type, x, y, sprite);
        this.hitbox = new Rectangle((int) tile.getX(), (int) tile.getY(), 2, 2);
    }

    public Tree2(ConfigTree configTree, Sprite sprite) {
        this.configTree = configTree;
        //this.tile = new Tree(configTree, sprite);
        this.hitbox = new Rectangle((int) tile.getX(), (int) tile.getY(), 2, 2);
    }


    @Override
    public Tile getTile() {
        return tile;
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }
}
