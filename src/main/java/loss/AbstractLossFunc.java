package loss;

import structure.Scalar;

public abstract class AbstractLossFunc
{
    public static double EPSILON = 1e-15;

    public abstract double compute(Scalar [] predicted, double [] target);
    public abstract void derive(Scalar [] predicted, double [] target);

    protected void validate(Scalar [] predicted,  double [] target) {
        if(predicted.length != target.length) {
            throw new IllegalArgumentException("Mismatched array sizes");
        }
    }
}
