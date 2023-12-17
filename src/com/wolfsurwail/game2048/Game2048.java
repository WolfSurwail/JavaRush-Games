package com.wolfsurwail.game2048;

import com.javarush.engine.cell.*;

import java.util.Random;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;
    private int gameStoppedWith = 2048;

    private static final Color color2 = getRandomColor();
    private static final Color color3 = getRandomColor();
    private static final Color color4 = getRandomColor();
    private static final Color color5 = getRandomColor();
    private static final Color color6 = getRandomColor();
    private static final Color color7 = getRandomColor();
    private static final Color color8 = getRandomColor();
    private static final Color color9 = getRandomColor();
    private static final Color color10 = getRandomColor();
    private static final Color color11 = getRandomColor();
    private static final Color color12 = getRandomColor();
    private static final Color color13 = getRandomColor();
    private static final Color color14 = getRandomColor();
    private static final Color color15 = getRandomColor();
    private static final Color color16 = getRandomColor();

    private void createGameStoppedWith() {
        Random random = new Random();
        switch (random.nextInt(5) + 1) {
            case 1: gameStoppedWith = 4096; break;
            case 2: gameStoppedWith = 8192; break;
            case 3: gameStoppedWith = 16384; break;
            case 4: gameStoppedWith = 32768; break;
            default: gameStoppedWith = 2048;
        }
    }

    private static Color getRandomColor() {
        Color[] colors = Color.values();
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
        createGameStoppedWith();
        System.out.println(gameStoppedWith);
    }

    @Override
    public void onKeyPress(Key key) {
        if (isGameStopped) {
            if (key == Key.SPACE) {
                isGameStopped = false;
                score = 0;
                setScore(score);
                createGame();
                drawScene();
            } else {
                return;
            }
        }

        if (!canUserMove()) {
            gameOver();
            return;
        }

        if (key == Key.UP) {
            moveUp();
        } else if (key == Key.RIGHT) {
            moveRight();
        } else if (key == Key.DOWN) {
            moveDown();
        } else if (key == Key.LEFT) {
            moveLeft();
        } else {
            return;
        }
        drawScene();
    }

    private void createGame() {
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }

    private boolean canUserMove() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (gameField[y][x] == 0) {
                    return true;
                } else if (y < SIDE - 1 && gameField[y][x] == gameField[y + 1][x]) {
                    return true;
                } else if ((x < SIDE - 1) && gameField[y][x] == gameField[y][x + 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createNewNumber() {
        if (getMaxTileValue() >= gameStoppedWith) {
            win();
            return;
        }

        boolean isCreated = false;
        do {
            int x = getRandomNumber(SIDE);
            int y = getRandomNumber(SIDE);
            if (gameField[y][x] == 0) {
                gameField[y][x] = getRandomNumber(10) < 9 ? 2 : 4;
                isCreated = true;
            }
        }
        while (!isCreated);
    }

    private int getMaxTileValue() {
        int max = gameField[0][0];
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (gameField[y][x] > max) {
                    max = gameField[y][x];
                }
            }
        }
        return max;
    }

    private void gameOver() {
        showMessageDialog(Color.NONE, "GAME OVER!", Color.WHITE, 50);
        isGameStopped = true;
    }

    private void win() {
        showMessageDialog(Color.NONE, "YOU WIN!", Color.WHITE, 50);
        isGameStopped = true;
    }

    private void setCellColoredNumber(int x, int y, int value) {
        Color color = getColorByValue(value);
        String str = value > 0 ? "" + value : "";
        setCellValueEx(x, y, color, str);
    }

    private Color getColorByValue(int value) {
        switch (value) {
            case 0:
                return Color.WHITE;
            case 2:
                return color2;
            case 4:
                return color3;
            case 8:
                return color4;
            case 16:
                return color5;
            case 32:
                return color6;
            case 64:
                return color7;
            case 128:
                return color8;
            case 256:
                return color9;
            case 512:
                return color10;
            case 1024:
                return color11;
            case 2048:
                return color12;
            case 4096:
                return color13;
            case 8192:
                return color14;
            case 16384:
                return color15;
            case 32768:
                return color16;
            default:
                return Color.NONE;
        }
    }

    private void moveLeft() {
        boolean isNewNumberNeeded = false;
        for (int[] row : gameField) {
            boolean wasCompressed = compressRow(row);
            boolean wasMerged = mergeRow(row);
            if (wasMerged) {
                compressRow(row);
            }
            if (wasCompressed || wasMerged) {
                isNewNumberNeeded = true;
            }
        }
        if (isNewNumberNeeded) {
            createNewNumber();
        }
    }

    private void moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }

    private void moveRight() {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }

    private void moveDown() {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    private boolean compressRow(int[] row) {
        int insertPosition = 0;
        boolean result = false;
        for (int x = 0; x < SIDE; x++) {
            if (row[x] > 0) {
                if (x != insertPosition) {
                    row[insertPosition] = row[x];
                    row[x] = 0;
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }

    private boolean mergeRow(int[] row) {
        boolean result = false;
        for (int i = 0; i < row.length - 1; i++) {
            if (row[i] != 0 && row[i] == row[i + 1]) {
                row[i] += row[i + 1];
                row[i + 1] = 0;
                result = true;
                score += row[i];
                setScore(score);
            }
        }
        return result;
    }

    private void rotateClockwise() {
        int[][] result = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                result[j][SIDE - 1 - i] = gameField[i][j];
            }
        }
        gameField = result;
    }

    private void drawScene() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
    }
}
