package core;

import normalization.Normalizer;

import java.util.*;

public class Dataset
{
    private double [][] features;
    private double [][] target;

    public Dataset(double [][] features, double [][] target) {
        this.features = features;
        this.target = target;
    }

    public void normalize(Normalizer normalizer) {
        features = normalizer.normalize(features);
    }

    public void shuffle() {
        int size = features.length;
        List<Integer> index = new ArrayList<>(size);

        for(int i = 0; i < size; i++) {
            index.add(i);
        }

        Collections.shuffle(index, new Random(337609));

        double [][] shuffledFeatures = new double[size][];
        double [][] shuffledTarget = new double[size][];

        for(int i = 0; i < size; i++) {
            int newIndex = index.get(i);
            shuffledFeatures[i] = features[newIndex];
            shuffledTarget[i] = target[newIndex];
        }

        features = shuffledFeatures;
        target = shuffledTarget;
    }

    public void toOneHotEncoding(int size) {

        for(int i = 0; i < target.length; i++) {
            double [] oneHot = new double[size];
            int label = (int) target[i][0];
            oneHot[label] = 1.0;
            target[i] = oneHot;
        }
    }

    public double [][] getFeatures() { return features; }
    public double [][] getTarget() { return target; }
}
