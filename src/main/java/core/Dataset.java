package core;

import normalization.Normalizer;

public class Dataset
{
    private double [][] features;
    private double [] target;

    public Dataset(double [][] features, double [] target) {
        this.features = features;
        this.target = target;
    }

    public void normalize(Normalizer normalizer) {
        normalizer.normalize(features);
    }

    public double [][] getFeatures() { return features; }
    public double [] getTarget() { return target; }
}
