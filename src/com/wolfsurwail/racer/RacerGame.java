package com.wolfsurwail.racer;

import com.javarush.engine.cell.*;
import com.wolfsurwail.racer.road.RoadManager;

import java.util.Random;

public class RacerGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private static final int RACE_GOAL_CARS_COUNT = 40;
    private PlayerCar player;
    private FinishLine finishLine;
    private ProgressBar progressBar;

    private RoadMarking roadMarking;
    private RoadManager roadManager;

    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        }
        if (roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT) {
            finishLine.show();
        }
        if (finishLine.isCrossed(player)) {
            win();
            drawScene();
            return;
        }
        moveAll();
        roadManager.generateNewRoadObjects(this);
        score -= 5;
        setScore(score);
        drawScene();
    }

    private void createGame() {
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
        drawScene();
        setTurnTimer(40);
        isGameStopped = false;
        score = 3500;
    }

    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        roadManager.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);
        player.draw(this);
    }

    public static Color getRandomColor() {
        Color[] colors = {Color.GREEN,Color.LIGHTGREEN, Color.LIMEGREEN, Color.LIME, Color.MEDIUMSPRINGGREEN,Color.SPRINGGREEN};
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == CENTER_X) {
                    setCellColor(x, y, Color.WHITE);
                } else if (x >= ROADSIDE_WIDTH && x < WIDTH - ROADSIDE_WIDTH) {
                    setCellColor(x, y, Color.DIMGREY);
                } else if (x == 13 || x == 50) {
                    setCellColor(x,y,Color.BISQUE);
                } else if(x == 12 || x == 51) {
                    setCellColor(x,y,Color.BLACK);
                }
                else {
                    setCellColor(x, y, getRandomColor());
                }
            }
        }
    }

    private void moveAll() {
        roadMarking.move(player.speed);
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
        player.move();
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
        if (key == Key.RIGHT) {
            player.setDirection(Direction.RIGHT);
        } else if (key == Key.LEFT) {
            player.setDirection(Direction.LEFT);
        }
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
        if (key == Key.UP) {
            player.speed = 2;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if ((key == Key.RIGHT && player.getDirection() == Direction.RIGHT)
                || (key == Key.LEFT && player.getDirection() == Direction.LEFT)) {
            player.setDirection(Direction.NONE);
        }
        if (key == Key.UP) {
            player.speed = 1;
        }
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Game Over", Color.RED,70);
        stopTurnTimer();
        player.stop();
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Win", Color.GREEN,70);
        stopTurnTimer();
    }
}