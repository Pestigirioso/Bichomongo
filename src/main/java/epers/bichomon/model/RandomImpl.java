package epers.bichomon.model;

import java.util.Random;

public class RandomImpl implements IRandom {
    @Override
    public int getInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @Override
    public double getDouble(double min, double max) {
        return new Random().nextDouble() * (max - min) + min;
    }
}
