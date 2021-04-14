package util;

import java.util.Random;

public class Utilisateur {
    public static int randomizer(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
