package com.wolfsurwail.spaceinvaders.gameobjects;


import com.javarush.engine.cell.Game;
import com.wolfsurwail.spaceinvaders.Direction;
import com.wolfsurwail.spaceinvaders.ShapeMatrix;
import com.wolfsurwail.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {
    private static int ROWS_COUNT = 0;
    private static int COLUMNS_COUNT = 0;
    private static final int STEP = ShapeMatrix.ENEMY.length + 1;

    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;

    public EnemyFleet() {
        createShips();
    }

    private void createShips() {
        ships = new ArrayList<>();
        if (SpaceInvadersGame.gameLevel == 1) {
            ROWS_COUNT  = 1;
            COLUMNS_COUNT = 5;
            for (int y = 0; y < 1; y++) {
                for (int x = 0; x < 5; x++) {
                    ships.add(new EnemyShip(x * STEP, y * STEP + 12));
                }
            }
        } else if (SpaceInvadersGame.gameLevel == 2) {
            ROWS_COUNT  = 1;
            COLUMNS_COUNT = 10;
            for (int y = 0; y < ROWS_COUNT; y++) {
                for (int x = 0; x < COLUMNS_COUNT; x++) {
                    ships.add(new EnemyShip(x * STEP, y * STEP + 12));
                }
            }
        } else if (SpaceInvadersGame.gameLevel == 3) {
            ROWS_COUNT  = 2;
            COLUMNS_COUNT = 5;
            for (int y = 0; y < ROWS_COUNT; y++) {
                for (int x = 0; x < COLUMNS_COUNT; x++) {
                    ships.add(new EnemyShip(x * STEP, y * STEP + 12));
                }
            }
        } else if (SpaceInvadersGame.gameLevel == 4) {
            ROWS_COUNT  = 2;
            COLUMNS_COUNT = 10;
            for (int y = 0; y < ROWS_COUNT; y++) {
                for (int x = 0; x < COLUMNS_COUNT; x++) {
                    ships.add(new EnemyShip(x * STEP, y * STEP + 12));
                }
            }
        } else {
            ROWS_COUNT  = 3;
            COLUMNS_COUNT = 10;
            for (int y = 0; y < ROWS_COUNT; y++) {
                for (int x = 0; x < COLUMNS_COUNT; x++) {
                    ships.add(new EnemyShip(x * STEP, y * STEP + 12));
                }
            }
        }

        Boss boss = new Boss(STEP * COLUMNS_COUNT / 2 - ShapeMatrix.BOSS_ANIMATION_FIRST.length / 2 - 1, 5);
        ships.add(boss);
    }

    public void draw(Game game) {
        for (GameObject ship : ships) {
            ship.draw(game);
        }
    }

    public int getShipsCount() {
        return ships.size();
    }

    public void move() {
        if (ships.isEmpty()) {
            return;
        }

        Direction currentDirection = direction;
        if (direction == Direction.LEFT && getLeftBorder() < 0) {
            direction = Direction.RIGHT;
            currentDirection = Direction.DOWN;
        } else if (direction == Direction.RIGHT && getRightBorder() > SpaceInvadersGame.WIDTH) {
            direction = Direction.LEFT;
            currentDirection = Direction.DOWN;
        }

        double speed = getSpeed();
        for (EnemyShip ship : ships) {
            ship.move(currentDirection, speed);
        }
    }

    public double getBottomBorder() {
        double bottom = 0;
        for (GameObject ship : ships) {
            if (ship.y + ship.height > bottom) {
                bottom = ship.y + ship.height;
            }
        }
        return bottom;
    }

    private double getLeftBorder() {
        double left = SpaceInvadersGame.WIDTH;
        for (GameObject ship : ships) {
            if (ship.x < left) {
                left = ship.x;
            }
        }
        return left;
    }

    private double getRightBorder() {
        double right = 0;
        for (GameObject ship : ships) {
            if (ship.x + ship.width > right) {
                right = ship.x + ship.width;
            }
        }
        return right;
    }

    private double getSpeed() {
        int count = ships.size() * 5;
        double speed = 3. / count;
        return speed > 2. ? 2. : speed;
    }

    public Bullet fire(Game game) {
        if (ships.isEmpty()) {
            return null;
        }

        int random = game.getRandomNumber(100 / SpaceInvadersGame.COMPLEXITY);
        if (random > 0) {
            return null;
        }

        int shipNumber = game.getRandomNumber(ships.size());
        EnemyShip ship = ships.get(shipNumber);

        return ship.fire();
    }

    public void deleteHiddenShips() {
        for (EnemyShip ship : new ArrayList<>(ships)) {
            if (!ship.isVisible()) {
                ships.remove(ship);
            }
        }
    }

    public int verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) {
            return 0;
        }

        int score = 0;
        for (Bullet bullet : bullets) {
            for (EnemyShip ship : ships) {
                if (ship.isAlive && bullet.isAlive && ship.isCollision(bullet)) {
                    ship.kill();
                    bullet.kill();
                    score += ship.score;
                }
            }
        }
        return score;
    }
}