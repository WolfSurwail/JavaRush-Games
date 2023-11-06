package com.wolfsurwail.snake;
import com.javarush.engine.cell.*;

import java.util.Random;

public class Apple extends GameObject {
    private static final String APPLE_SIGN = generateAppleSign();
    public boolean isAlive = true;
    private static Color randomColorApple = getRandomColor();

    public Apple(int x, int y) {
        super(x, y);
    }

    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, randomColorApple, 75);
    }

    public static Color getRandomColor() {
        Color[] colors = Color.values();
        Random random = new Random();
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    public static String generateAppleSign() {
        String[] strings = {"\uD83C\uDF4F","\uD83E\uDD6D","\uD83C\uDF4D","\uD83C\uDF4C","\uD83C\uDF4B","\uD83C\uDF4A","\uD83C\uDF49","\uD83C\uDF48","\uD83C\uDF47"};
        Random random = new Random();
        int index = random.nextInt(strings.length);
        return strings[index];
    }
}
