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

    @Override
    public void derive(Scalar [] predicted, double [] target) {
        validate(predicted, target);

        int size = predicted.length;

        for(int i = 0; i < size; i++) {
            double grad = predicted[i].getValue() - target[i];

            predicted[i].getParent()[0].setGrad(grad);
            predicted[i].setGrad(1.0);
        }
    }
}
