package optimization;

import structure.Scalar;

import java.util.List;

public interface Optimizer
{
    void optimize(List<Scalar> parameter, double learningRate, int batch);
}
