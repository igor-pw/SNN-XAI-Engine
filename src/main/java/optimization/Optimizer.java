package optimization;

import structure.Layer;
import structure.Scalar;

import java.util.List;

public interface Optimizer
{
    void optimize(Layer[] layer, double learningRate, int batch);
}
