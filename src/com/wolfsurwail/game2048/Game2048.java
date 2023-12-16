package com.wolfsurwail.game2048;

import com.javarush.engine.cell.*;

public class Game2048 extends Game {
    private static int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;
    private static final int MENU_WIDTH = 20;
    private static final int MENU_HEIGHT = 24;
    private MenuGameObject[][] menuGameObjects = new MenuGameObject[MENU_HEIGHT][MENU_WIDTH];
    private boolean sizeMapCase;
    private int stopPlay = 0;
    private int spawnInRound = 0;
    private boolean isGameStarted;


    @Override
    public void initialize() {
        createMenu();
    }

    private void createMenu() {
        drawScreenMenu(MENU_WIDTH,MENU_HEIGHT,Color.SILVER);
        showGrid(true);
        pasteText("Выбери режимы:", 3,2);
        pasteText("Размер карты:", 1,4);

        pasteText("4х4",2,7);
        pasteText("5х5",6,7);
        pasteText("6х6",10,7);
        pasteText("7х7",14,7);

        draw3x3Cube(2,6,Color.GOLD);
        draw3x3Cube(6,6,Color.GOLD);
        draw3x3Cube(10,6,Color.GOLD);
        draw3x3Cube(14,6,Color.GOLD);

        pasteText("До скольки играем:",1,12);

        pasteText("2048",3,14);
        pasteText("4096",8,14);
        pasteText("8192",13,14);

        drawRed(4,16);
        drawRed(9,16);
        drawRed(14,16);

        pasteText("Появление за раз:",1,18);

        pasteText("1",6,20);
        pasteText("2",10,20);
        pasteText("3",14,20);

        drawRed(6,22);
        drawRed(10,22);
        drawRed(14,22);
    }

    private void drawRed(int startX, int startY) {
        setCellColor(startX,startY,Color.RED);
        setMenuGameObjects(startX,startY);
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
        if (getMaxTileValue() >= stopPlay) {
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
                return Color.PLUM;
            case 4:
                return Color.SLATEBLUE;
            case 8:
                return Color.DODGERBLUE;
            case 16:
                return Color.DARKTURQUOISE;
            case 32:
                return Color.MEDIUMSEAGREEN;
            case 64:
                return Color.LIMEGREEN;
            case 128:
                return Color.DARKORANGE;
            case 256:
                return Color.SALMON;
            case 512:
                return Color.ORANGERED;
            case 1024:
                return Color.DEEPPINK;
            case 2048:
                return Color.MEDIUMVIOLETRED;
            case 4096:
                return Color.HONEYDEW;
            case 8192:
                return Color.LIGHTCORAL;
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

    private void pasteText(String s, int startX, int startY ) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            setCellValueEx(startX,startY, Color.GOLD, String.valueOf(c), Color.BLACK,80);
            startX++;
        }
    }

    public void drawScreenMenu(int WIDTH, int HEIGHT, Color color) {
        setScreenSize(WIDTH,HEIGHT);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i,j,color,"");
            }
        }
    }

    public void draw3x3Cube(int startX, int startY,Color color) {
        for (int x = startX; x < startX + 3; x++) {
            for (int y = startY; y < startY + 3; y++) {
                setCellColor(x,y,color);
            }
        }
        setCellColor(startX+1,startY+4,Color.RED);
        setMenuGameObjects(startX + 1,startY + 4);
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        openTileMenu(x, y);
        replaceMenu(x,y);
        replaceAllBugs();
        checkChose();
    }

    private void openTileMenu(int x, int y) {
        MenuGameObject menuGameObject = menuGameObjects[y][x];

        if (!menuGameObject.isOpen) {
            setCellColor(x,y,Color.GREEN);
            menuGameObject.isOpen = true;
        } else {
            menuGameObject.isOpen = false;
        }
    }

    private void setMenuGameObjects(int x, int y) {
        menuGameObjects[y][x] = new MenuGameObject(x,y,true,false);
    }

    private void replaceMenu(int aX, int y) {
        for (MenuGameObject[] menuGameObject : menuGameObjects) {
            for (int x = 0; x < MENU_WIDTH; x++) {
                if (menuGameObject[x] != null) {
                    if (menuGameObject[x].isFlag) {
                        setCellColor(x,y,Color.RED);
                    }
                }
            }
        }
        setCellColor(aX,y,Color.GREEN);
    }

    private void replaceAllBugs() {
        // first line
        setCellColor(4,10,Color.SILVER);
        setCellColor(6,10,Color.SILVER);
        setCellColor(9,10,Color.SILVER);
        setCellColor(10,10,Color.SILVER);
        setCellColor(14,10,Color.SILVER);
        // second line
        setCellColor(3,16,Color.SILVER);
        setCellColor(6,16,Color.SILVER);
        setCellColor(7,16,Color.SILVER);
        setCellColor(10,16,Color.SILVER);
        setCellColor(11,16,Color.SILVER);
        setCellColor(15,16,Color.SILVER);
        // third line
        setCellColor(3,22,Color.SILVER);
        setCellColor(4,22,Color.SILVER);
        setCellColor(7,22,Color.SILVER);
        setCellColor(8,22,Color.SILVER);
        setCellColor(9,22,Color.SILVER);
        setCellColor(11,22,Color.SILVER);
        setCellColor(15,22,Color.SILVER);
    }

    private void checkChose() {
        MenuGameObject Line1Chose1 = menuGameObjects[3][10];
        MenuGameObject Line1Chose2 = menuGameObjects[7][10];
        MenuGameObject Line1Chose3 = menuGameObjects[11][10];
        if (Line1Chose1.isOpen) {
            SIDE = 4;
            sizeMapCase = true;
        } else if (Line1Chose2.isOpen) {
            SIDE = 5;
            sizeMapCase = true;
        } else if (Line1Chose3.isOpen) {
            SIDE = 6;
            sizeMapCase = true;
        } else {
            SIDE = 7;
            sizeMapCase = true;
        }
        MenuGameObject Line2Chose1 = menuGameObjects[4][16];
        MenuGameObject Line2Chose2 = menuGameObjects[9][16];
        if (Line2Chose1.isOpen) {
            stopPlay = 2048;
        } else if (Line2Chose2.isOpen) {
            stopPlay = 4096;
        } else {
            stopPlay = 8192;
        }
        MenuGameObject Line3Chose1 = menuGameObjects[6][22];
        MenuGameObject Line3Chose2 = menuGameObjects[10][22];
        if (Line3Chose1.isOpen) {
            spawnInRound = 1;
        } else if (Line3Chose2.isOpen) {
            spawnInRound = 2;
        } else {
            spawnInRound = 3;
        }
    }
}
