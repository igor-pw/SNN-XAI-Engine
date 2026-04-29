package core;

import normalization.Normalizer;

import java.util.*;

public class Dataset
{
    private double [][] features;
    private double [][] target;
    private final int [] index;
    private final Random random = new Random(42);

    public Dataset(double [][] features, double [][] target) {
        this.features = features;
        this.target = target;

        index = new int[features.length];
        for(int i = 0; i < features.length; i++) {
            index[i] = i;
        }
    }

    public void normalize(Normalizer normalizer) {
        features = normalizer.normalize(features);
    }

    public void shuffle() {
        int size = index.length;

        for(int i = size-1; i > 0; i--) {
            int j = random.nextInt(i+1);
            int temp = index[i];
            index[i] = index[j];
            index[j] = temp;
        }
    }

    public void toOneHotEncoding(int size) {

        for(int i = 0; i < target.length; i++) {
            double [] oneHot = new double[size];
            int label = (int) target[i][0];
            oneHot[label] = 1.0;
            target[i] = oneHot;
        }
    }

    public double [] getFeatures(int i) { return features[index[i]]; }
    public double [][] getFeatures() { return features; }
    public double [] getTarget(int i) { return target[index[i]]; }
    public double [][] getTarget() { return target; }
}
