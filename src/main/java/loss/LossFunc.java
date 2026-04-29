package loss;

import structure.Neuron;
import structure.Scalar;

public interface LossFunc
{
    double compute(Neuron [] predicted, double [] target);
    void derive(Neuron [] predicted, double [] target);
}
