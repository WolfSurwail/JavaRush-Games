package com.wolfsurwail.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

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
        for (int y = 0; y < HEIGHT ; y++) {
            for (int x = 0; x < WIDTH ; x++) {
                setCellValueEx(x,y, Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
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
