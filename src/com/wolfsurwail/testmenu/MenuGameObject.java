package com.wolfsurwail.testmenu;

public class MenuGameObject {
    public int x;
    public int y;
    public boolean isOpen;
    public boolean isFlag;

    MenuGameObject(int x, int y, boolean isFlag, boolean isOpen) {
        this.x = x;
        this.y = y;
        this.isFlag = isFlag;
        this.isOpen = isOpen;
    }
}
