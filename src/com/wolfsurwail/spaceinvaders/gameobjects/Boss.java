package com.wolfsurwail.spaceinvaders.gameobjects;

import com.wolfsurwail.spaceinvaders.Direction;
import com.wolfsurwail.spaceinvaders.ShapeMatrix;

public class Boss extends EnemyShip {
    private int frameCount = 0;

    public Boss(double x, double y) {
        super(x, y);
        this.setAnimatedView(true,
                ShapeMatrix.BOSS_ANIMATION_FIRST,
                ShapeMatrix.BOSS_ANIMATION_SECOND);
        score = 100;
    }

    @Override
    public void nextFrame() {
        frameCount++;
        if (frameCount % 10 == 0 || !isAlive) {
            super.nextFrame();
        }
    }

    @Override
    public void kill() {
        if (!isAlive) {
            return;
        }
        isAlive = false;

        this.setAnimatedView(false,
                ShapeMatrix.KILL_BOSS_ANIMATION_FIRST,
                ShapeMatrix.KILL_BOSS_ANIMATION_SECOND,
                ShapeMatrix.KILL_BOSS_ANIMATION_THIRD);
    }

    @Override
    public Bullet fire() {
        if (!isAlive) {
            return null;
        }

        int offset;
        if (matrix == ShapeMatrix.BOSS_ANIMATION_FIRST) {
            offset = 6;
        } else {
            offset = 0;
        }
        return new Bullet(x + offset, y + height, Direction.DOWN);
    }
}