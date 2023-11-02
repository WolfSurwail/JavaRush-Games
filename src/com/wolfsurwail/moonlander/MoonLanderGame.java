package com.wolfsurwail.moonlander;
import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private GameObject platform;
    private boolean isGameStopped;
    private int score;

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.UP) {
            isUpPressed = true;
        } else if (key == Key.LEFT) {
            isLeftPressed = true;
            isRightPressed = false;
        } else if (key == Key.RIGHT) {
            isRightPressed = true;
            isLeftPressed = false;
        } else if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.UP) {
            isUpPressed = false;
        } else if (key == Key.LEFT) {
            isLeftPressed = false;
        } else if (key == Key.RIGHT) {
            isRightPressed = false;
        }
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if ( x > WIDTH - 1 || x < 0 || y < 0 || y > HEIGHT - 1) {
            return;
        }
        super.setCellColor(x,y,color);
    }

    @Override
    public void onTurn(int step) {
        if (score > 0) {
            score--;
        }
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        setScore(score);
        drawScene();
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
        showGrid(false);
    }

    private void createGame() {
        score = 1000;
        createGameObjects();
        drawScene();
        setTurnTimer(50);
        isLeftPressed = false;
        isUpPressed = false;
        isRightPressed = false;
        isGameStopped = false;
    }

    private void drawScene() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellColor(x,y,Color.BLACK);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGameObjects() {
        rocket = new Rocket(WIDTH / 2,0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 2, ShapeMatrix.PLATFORM);
    }

    private void check() {
        if (rocket.isCollision(landscape)) {
            gameOver();
        } else if (rocket.isStopped() && rocket.isCollision(platform))  {
            win();
        }
    }

    private void win() {
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "Win!", Color.GREEN,85);
        stopTurnTimer();
    }


    private void gameOver() {
        score = 0;
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "Game Over!", Color.RED,85);
        stopTurnTimer();
    }


}
