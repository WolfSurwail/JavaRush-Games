package com.wolfsurwail.testmenu;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

public class TestMenu extends Game {
    private static final int WIDTH = 11;
    private static final int HEIGHT = 5;
    private MenuGameObject[][] menuGameObjects = new MenuGameObject[HEIGHT][WIDTH];


    @Override
    public void initialize() {
        drawMenu();
        showGrid(false);
    }

    public void drawMenu() {
        drawScreenMenu(WIDTH,HEIGHT, Color.GRAY);
        drawSizeGameMenu5x1(0,1,Color.GRAY);
        drawSizeGameMenu5x1(2,1,Color.GRAY);
        drawSizeGameMenu5x1(3,1,Color.GRAY);

    }

    public void drawSizeGameMenu5x1(int startX, int startY,Color color) {
        for (int x = startX; x < startX + 5; x++) {
            setCellColor(x,startY,color);
        }
        setCellColor(startX + 2,startY + 2, Color.RED);
        setMenuGameObjects(startX + 2,startY + 2);
    }

    public void drawScreenMenu(int WIDTH, int HEIGHT, Color color) {
        setScreenSize(WIDTH,HEIGHT);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i,j,color,"");
            }
        }
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        openTile(x, y);
        replaceMenu(x,y);
    }

    private void openTile(int x, int y) {
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

    private void replaceMenu(int lineX, int y) {
        if(checkMenuIsAnyOpen()) {
            for (int x = lineX; x < WIDTH; x++) {
                setCellColor(x,y,Color.RED);
            }
        }
    }

    private boolean checkMenuIsAnyOpen() {
        int count = 0;
        for (int y = 0; y < menuGameObjects.length; y++) {
            for (int x = 0; x < menuGameObjects.length; x++) {
                if (this.menuGameObjects[y][x] != null) {
                    boolean flag = this.menuGameObjects[y][x].isFlag;
                    if (flag) {
                        count++;
                        if (count > 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
