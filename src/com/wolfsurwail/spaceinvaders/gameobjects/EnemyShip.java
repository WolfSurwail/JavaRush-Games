package com.wolfsurwail.spaceinvaders.gameobjects;

import com.wolfsurwail.spaceinvaders.Direction;
import com.wolfsurwail.spaceinvaders.ShapeMatrix;

public class EnemyShip extends Ship {
    public int score = 15;

    public EnemyShip(double x, double y) {
        super(x, y);
        this.setStaticView(ShapeMatrix.ENEMY);
    }

    public void move(Direction direction, double speed) {
        switch (direction) {
            case RIGHT:
                x = x + speed;
                break;
            case LEFT:
                x = x - speed;
                break;
            case DOWN:
                y += 2;
                break;
        }
    }

    @Override
    public Bullet fire() {
        return new Bullet(x + 1, y + height, Direction.DOWN);
    }

    @Override
    public void kill() {
        if (!isAlive) {
            return;
        }
        isAlive = false;

        this.setAnimatedView(false,
                ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST,
                ShapeMatrix.KILL_ENEMY_ANIMATION_SECOND,
                ShapeMatrix.KILL_ENEMY_ANIMATION_THIRD);
    }
}