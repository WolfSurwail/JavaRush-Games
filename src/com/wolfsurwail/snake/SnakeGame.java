package com.wolfsurwail.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.Random;

public class SnakeGame extends Game {
    public static final int WIDTH = getRandomInt();
    public static final int HEIGHT = getRandomInt();
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = WIDTH * HEIGHT - 1;
    private int score;
    private static Color randomColorOne = getRandomColor();
    private static Color randomColorTwo = getRandomColor();

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }

        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else  if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }  else  if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }

    private void drawScene() {
        int i = 0;
        for (int y = 0; y < HEIGHT ; y++) {
            for (int x = 0; x < WIDTH ; x++) {
                if(HEIGHT % 2 == 0 && WIDTH % 2 == 0) {
                    if (i % 2 == 0) {
                        setCellValueEx(x, y, randomColorOne, "");
                        i++;
                    } else {
                        setCellValueEx(x, y, randomColorTwo, "");
                        i++;
                    }
                } else if (HEIGHT % 2 == 0 || WIDTH % 2 == 0) {
                    if (i % 4 == 0) {
                        setCellValueEx(x, y, randomColorOne, "");
                        i++;
                    } else {
                        setCellValueEx(x, y, randomColorTwo, "");
                        i++;
                    }
                }
                else {
                    if (i % 3 == 0) {
                        setCellValueEx(x, y, randomColorOne, "");
                        i++;
                    } else {
                        setCellValueEx(x, y, randomColorTwo, "");
                        i++;
                    }
                }
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    public static Color getRandomColor() {
        Color[] colors = Color.values();
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    public static int getRandomInt() {
        Random random = new Random();
        return random.nextInt(15) + 5;
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            score+= 5;
            setScore(score);
            turnDelay-= 10;
            setTurnTimer(turnDelay);
        }
        if(!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    private void createNewApple() {
        Apple newApple;
        do {
            int newX = getRandomNumber(WIDTH);
            int newY = getRandomNumber(HEIGHT);
            newApple = new Apple(newX, newY);
        } while (snake.checkCollision(newApple)); {
            apple = newApple;
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"Вы проиграли!",Color.RED,70);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"Вы выйграли!",Color.RED,70);
    }
}
