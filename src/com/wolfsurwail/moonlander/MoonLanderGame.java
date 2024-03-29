package com.wolfsurwail.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private GameObject platform;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isGameStopped;
    private int score;

    private int gameLevel = 1;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        showGrid(false);
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        drawScene();
        if (score > 0) {
            score--;
        }
        setScore(score);
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x > WIDTH - 1 || x < 0 || y < 0 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x, y, color);
    }

    @Override
    public void onKeyPress(Key key) {
        if (Key.RIGHT == key) {
            isRightPressed = true;
            isLeftPressed = false;
        } else if (Key.LEFT == key) {
            isLeftPressed = true;
            isRightPressed = false;
        } else if (Key.UP == key) {
            isUpPressed = true;
        }

        if (isGameStopped && Key.SPACE == key) {
            createGame();
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (Key.RIGHT == key) {
            isRightPressed = false;
        } else if (Key.LEFT == key) {
            isLeftPressed = false;
        } else if (Key.UP == key) {
            isUpPressed = false;
        }
    }

    private void createGame() {
        setTurnTimer(50);
        createGameObjects();
        drawScene();
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;
        score = 1000;
    }

    private void drawScene() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellColor(x, y, Color.BLACK);
            }
        }

        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGameObjects() {
        switch (gameLevel) {
            case 1:
                rocket = new Rocket(WIDTH / 2.0, 0);
                landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE_ONE);
                platform = new GameObject(23, HEIGHT - 1, ShapeMatrix.PLATFORM);
                break;
            case 2:
                rocket = new Rocket(WIDTH / 2.0, 0);
                landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE_TWO);
                platform = new GameObject(9, HEIGHT - 1, ShapeMatrix.PLATFORM);
                break;
            case 3:
                rocket = new Rocket(WIDTH / 2.0, 0);
                landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE_THREE);
                platform = new GameObject(26, HEIGHT - 1, ShapeMatrix.PLATFORM);
                break;
            case 4:
                rocket = new Rocket(WIDTH / 2.0, 0);
                landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE_FOUR);
                platform = new GameObject(48, HEIGHT - 1, ShapeMatrix.PLATFORM);
                break;
            default:
                rocket = new Rocket(WIDTH / 2.0, 0);
                landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE_FIVE);
                platform = new GameObject(50, HEIGHT - 1, ShapeMatrix.PLATFORM);
                break;
        }

    }

    private void check() {
        if (rocket.isCollision(platform) && rocket.isStopped()) {
            win();
        } else if (rocket.isCollision(landscape)) {
            gameOver();
        }
    }

    private void win() {
        if (gameLevel < 5) {
            rocket.land();
            isGameStopped = true;
            gameLevel++;
            showMessageDialog(Color.WHITE, "LEVEL #" + gameLevel, Color.GREEN, 50);
            stopTurnTimer();
            createGame();
        } else {
            rocket.land();
            isGameStopped = true;
            showMessageDialog(Color.WHITE, "YOU WIN", Color.GREEN, 50);
            stopTurnTimer();
            gameLevel = 0;
        }
    }

    private void gameOver() {
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.RED, 50);
        stopTurnTimer();
        score = 0;
    }
}
