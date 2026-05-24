package optimization;

import structure.Layer;

public interface Optimizer
{
    void optimize(Layer[] layer, double learningRate, int batch);
}
