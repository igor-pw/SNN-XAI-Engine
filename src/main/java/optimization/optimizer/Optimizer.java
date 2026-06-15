package optimization.optimizer;

import structure.Layer;

import java.io.Serializable;

public interface Optimizer extends Serializable
{
    void optimize(Layer[] layer, float learningRate, int batch);
    void init(Layer [] layer);
}
