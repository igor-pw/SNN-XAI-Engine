package loss;

import structure.Scalar;

public interface LossFunc
{
    double compute(Scalar [] predicted, double [] target);
    void derive(Scalar [] predicted, double [] target);
}
