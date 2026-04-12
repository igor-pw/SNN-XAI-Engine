package loss;

import structure.Scalar;

public class CceLoss extends AbstractLossFunc
{
    @Override
    public double compute(Scalar[] predicted, double [] target) {
        validate(predicted, target);

        int size = predicted.length;
        double cost = 0.0;

        for(int i = 0; i < size; i++) {
            double value = Math.max(EPSILON, Math.min(1 - EPSILON, predicted[i].getValue()));
            cost += (target[i] * Math.log(value));
        }

        return -cost;
    }
}
