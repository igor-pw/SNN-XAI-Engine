package optimization;

import structure.Layer;

import java.io.Serializable;

public interface Optimizer extends Serializable
{
    void optimize(Layer[] layer, double learningRate, int batch);
    void init(int weightSize, int biasSize);
}
