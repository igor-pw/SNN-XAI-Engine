package loss;

import structure.Neuron;
import structure.Scalar;

public class CceLoss extends AbstractLossFunc
{
    @Override
    public double compute(Neuron[] predicted, double [] target) {
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
    public void derive(Neuron [] predicted, double [] target) {
        validate(predicted, target);

        int size = predicted.length;

        for(int i = 0; i < size; i++) {
            double grad = predicted[i].getValue() - target[i];

            for(int j = 0; j < predicted[i].getWeight().length; j++) {
                predicted[i].getWeight()[j].setGrad(grad);
                predicted[i].getInput()[j].setGrad(grad);
                predicted[i].getBias().setGrad(grad);
                predicted[i].setGrad(1.0);
            }
        }
    }
}
