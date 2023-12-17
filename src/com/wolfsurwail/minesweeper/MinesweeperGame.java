package com.wolfsurwail.minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperGame extends Game {
    private static int SIDE = getRandomInt();

    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";

    private GameObject[][] gameField = new GameObject[SIDE][SIDE];

    private static final Color color1 = getRandomColor();
    private static final Color color2 = getRandomColor();
    private static final Color color3 = getRandomColor();
    private static final Color color4 = getRandomColor();

    private int countClosedTiles = SIDE * SIDE;
    private int countFlags;
    private int score;
    private int countMinesOnField;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    public static int getRandomInt() {
        Random random = new Random();
        return random.nextInt(15) + 9;
    }

    public static Color getRandomColor() {
        Color[] colors = {Color.GREEN,Color.LIGHTGREEN, Color.LIMEGREEN, Color.LIME, Color.MEDIUMSPRINGGREEN,Color.SPRINGGREEN};
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped) {
            restart();
            return;
        }
        openTile(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellValueEx(x, y, color1, "");
            }
        }

        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private void countMineNeighbors() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                GameObject gameObject = gameField[y][x];
                if (!gameObject.isMine) {
                    for (GameObject neighbor : getNeighbors(gameObject)) {
                        if (neighbor.isMine) {
                            gameObject.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private void restart() {
        countClosedTiles = SIDE * SIDE;
        score = 0;
        setScore(score);
        countMinesOnField = 0;
        isGameStopped = false;
        createGame();
    }

    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];

        if (gameObject.isOpen || gameObject.isFlag || isGameStopped) {
            return;
        }

        countClosedTiles--;
        gameObject.isOpen = true;
        setCellColor(x, y, color2);

        if (gameObject.isMine) {
            setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
            gameOver();
            return;
        }

        this.score += 5;
        setScore(score);

        if (countClosedTiles == countMinesOnField) {
            win();
        }

        if (gameObject.countMineNeighbors > 0) {
            setCellValue(x, y, String.valueOf(gameObject.countMineNeighbors));
        } else {
            for (GameObject neighbor : getNeighbors(gameObject)) {
                openTile(neighbor.x, neighbor.y);
            }
        }
    }

    private void gameOver() {
        showMessageDialog(Color.RED, "GAME OVER", Color.BLACK, 50);
        isGameStopped = true;
    }

    private void win() {
        showMessageDialog(Color.GREEN, "YOU WIN", Color.VIOLET, 50);
        isGameStopped = true;
    }

    private void markTile(int x, int y) {
        GameObject gameObject = gameField[y][x];

        if (gameObject.isOpen || isGameStopped || (countFlags <= 0 && !gameObject.isFlag)) {
            return;
        }

        if (gameObject.isFlag) {
            countFlags++;
            gameObject.isFlag = false;
            setCellValueEx(x, y, color3, "");
        } else {
            countFlags--;
            gameObject.isFlag = true;
            setCellValueEx(x, y, color4, FLAG);
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1 ; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1 ; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }
}