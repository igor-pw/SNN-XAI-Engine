package loss;

import structure.Neuron;

public class CceLoss extends AbstractLossFunc
{
    @Override
    public float compute(Neuron[] predicted, float [] target) {
        validate(predicted, target);

        int size = predicted.length;
        float cost = 0.0f;

        for(int i = 0; i < size; i++) {
            float value = Math.max(EPSILON, Math.min(1 - EPSILON, predicted[i].getValue()));
            cost += (target[i] * (float)Math.log(value));
        }

        return -cost;
    }

    @Override
    public void derive(Neuron [] predicted, float [] target) {
        validate(predicted, target);

        int size = predicted.length;

        for(int i = 0; i < size; i++) {
            float grad = predicted[i].getValue() - target[i];
            predicted[i].setGrad(grad);
        }
    }
}
