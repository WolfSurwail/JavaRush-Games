package com.wolfsurwail.racer;

import com.javarush.engine.cell.Color;

import java.util.Random;

public class ShapeMatrix {
    private static final int int1 = getRandomInt();
    private static final int int2 = getRandomInt();
    private static final int int3 = getRandomInt();
    private static final int int4 = getRandomInt();
    private static final int int5 = getRandomInt();
    private static final int int6 = getRandomInt();
    private static final int int7 = getRandomInt();
    public static final int int8 = getRandomInt();

    public static final int[][] PLAYER = new int[][]{
            {0, 0, int1, int1, 0, 0},
            {1, 0, int1, int1, 0, 1},
            {1, 1, int1, int1, 1, 1},
            {1, 0, int1, int1, 0, 1},
            {0, 0, int1, int1, 0, 0},
            {1, 0, int1, int1, 0, 1},
            {1, 1, int1, int1, 1, 1},
            {1, 0, int1, int1, 0, 1}
    };

    public static int getRandomInt() {
        Random random = new Random();
        return random.nextInt(50) + 1;
    }

    public static final int[][] PLAYER_DEAD = new int[][]{
            {0, 0, 5, 5, 0, 0},
            {1, 0, 5, 5, 0, 1},
            {1, 1, 5, 5, 1, 1},
            {1, 0, 5, 5, 0, 1},
            {0, 0, 5, 5, 0, 0},
            {1, 0, 5, 5, 0, 1},
            {1, 1, 5, 5, 1, 1},
            {1, 0, 5, 5, 0, 1}
    };

    public static final int[][] SPORT_CAR = new int[][]{
            {int2, 0, int2, int2, 0, int2},
            {int2, 1, int2, int2, 1, int2},
            {int2, 0, int2, int2, 0, int2},
            {0, 0, int2, int2, 0, 0},
            {int2, 0, int2, int2, 0, int2},
            {int2, 1, int2, int2, 1, int2},
            {int2, 0, int2, int2, 0, int2},
            {0, 0, int2, int2, 0, 0},
    };

    public static final int[][] DRUNK_CAR = new int[][]{
            {int3, 0, int3, int3, 0, int3},
            {int3, 1, int3, int3, 1, int3},
            {int3, 0, int3, int3, 0, int3},
            {0, 0, int3, int3, 0, 0},
            {int3, 0, int3, int3, 0, int3},
            {int3, 1, int3, int3, 1, int3},
            {int3, 0, int3, int3, 0, int3},
            {0, 0, int3, int3, 0, 0},
    };

    public static final int[][] PASSENGER_CAR = new int[][]{
            {0, int4, int4, int4, int4, 0},
            {int4, int4, 1, 1, int4, int4},
            {int4, 1, int4, int4, 1, int4},
            {int4, int4, int4, int4, int4, int4},
            {int4, 1, int4, int4, 1, int4},
            {int4, int4, int4, int4, int4, int4},
            {int4, 1, int4, int4, 1, int4},
            {int4, int4, 1, 1, int4, int4},
            {0, int4, int4, int4, int4, 0}
    };

    public static final int[][] TRUCK = new int[][]{
            {0, int5, int5, int5, int5, 0},
            {int5, int5, int5, int5, int5, int5},
            {int5, 1, int5, int5, 1, int5},
            {int5, 1, int5, int5, 1, int5},
            {int5, int5, int5, int5, int5, int5},
            {int5, 1, int5, int5, 1, int5},
            {int5, 1, int5, int5, 1, int5},
            {int5, int5, int5, int5, int5, int5},
            {0, int5, int5, int5, int5, 0},
            {0, 1, 1, 1, 1, 0},
            {0, int5, int5, int5, int5, 0},
            {int5, int5, int5, int5, int5, int5},
            {int5, 1, 2, 1, 1, int5},
            {int5, 1, 2, 1, 1, int5},
            {0, int5, int5, int5, int5, 0},
    };

    public static final int[][] BUS = new int[][]{
            {0, int6, int6, int6, int6, 0},
            {int6, int6, int6, int6, int6, int6},
            {int6, 1, int6, int6, 1, int6},
            {int6, 1, int6, int6, 1, int6},
            {int6, int6, int6, int6, int6, int6},
            {int6, 1, int6, int6, 1, int6},
            {int6, 1, int6, int6, 1, int6},
            {int6, int6, int6, int6, int6, int6},
            {int6, 1, int6, int6, 1, int6},
            {int6, 1, int6, int6, 1, int6},
            {int6, int6, int6, int6, int6, int6},
            {int6, 1, 2, 1, 1, int6},
            {int6, 1, 2, 1, 1, int6},
            {int6, 1, 2, 1, 1, int6},
            {0, int6, int6, int6, int6, 0},
    };

    public static final int[][] ROAD_MARKING = new int[][]{
            {int8},
            {int8},
            {int8},
            {int8},
    };

    public static final int[][] FINISH_LINE = new int[][]{
            {2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0}
    };

    public static final int[][] THORN = new int[][]{
            {2, 0, 0, 0, 2, 0},
            {int7, 1, 2, 0, int7, 1},
            {0, 0, int7, 1, 0, 0},
            {2, 0, 0, 0, 2, 0},
            {int7, 1, 2, 0, int7, 1},
            {0, 0, int7, 1, 0, 0}
    };
}