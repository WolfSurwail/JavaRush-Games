package com.wolfsurwail.testmenu;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.*;

public class TestMenu extends Game {
    private static final int MENU_WIDTH = 11;
    private static final int MENU_HEIGHT = 10;
    private MenuGameObject[][] menuGameObjects = new MenuGameObject[MENU_HEIGHT][MENU_WIDTH];


    @Override
    public void initialize() {
        drawMenu();
        showGrid(false);
    }

    public void drawMenu() {
        drawScreenMenu(MENU_WIDTH,MENU_HEIGHT, Color.GRAY);
        pasteText("хуй",1,1);
        drawSizeGameMenu5x1(0,1,Color.GRAY);
        drawSizeGameMenu5x1(2,1,Color.GRAY);
        drawSizeGameMenu5x1(4,1,Color.GRAY);
        drawSizeGameMenu5x1(0,6,Color.GRAY);
        drawSizeGameMenu5x1(2,6,Color.GRAY);
        drawSizeGameMenu5x1(4,6,Color.GRAY);

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
        openTileMenu(x, y);
        replaceMenu(x,y);
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

    private void pasteText(String s, int startX, int startY ) {
        for (char c : s.toCharArray()) {
            for (int x = startX; x < s.length(); x++) {
                setCellValueEx(x,startY, Color.SILVER, String.valueOf(c), Color.BLACK,80);
            }
        }
    }
}
