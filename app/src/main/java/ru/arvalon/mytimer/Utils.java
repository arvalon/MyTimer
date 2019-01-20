package ru.arvalon.mytimer;

import java.util.Random;

public class Utils {

    private static final Random RANDOM = new Random();

    static int randInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
