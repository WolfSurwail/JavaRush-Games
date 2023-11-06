package com.wolfsurwail.snake;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;
    private static Color randomColor = getRandomColor();

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        Color color = !isAlive ? Color.RED : randomColor;

        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject gameObject = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, sign, color, 75);
        }
    }

    public static Color getRandomColor() {
        Color[] colors = Color.values();
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    public void setDirection(Direction direction) {
        if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        } else if (this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        } else if (this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        } else if (this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }

        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        }
        this.direction = direction;
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH || newHead.y >= SnakeGame.HEIGHT || newHead.x < 0 || newHead.y < 0) {
            isAlive = false;
            return;
        }
        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, newHead);
        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
            return;
        } else {
            removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject oldHead = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(oldHead.x - 1, oldHead.y);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(oldHead.x + 1, oldHead.y);
        } else if (direction == Direction.UP) {
            return new GameObject(oldHead.x, oldHead.y - 1);
        } else {
            return new GameObject(oldHead.x, oldHead.y + 1);
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
        
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject part: snakeParts) {
            if (part.x == gameObject.x && part.y == gameObject.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
