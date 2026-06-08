package loss;

import structure.Neuron;

public class BceLoss extends AbstractLossFunc
{
    @Override
    public float compute(Neuron [] predicted, float [] target) {
        validate(predicted, target);

        int size = predicted.length;
        float cost = 0.0f;

        for(int i = 0; i < size; i++) {
            float value = Math.max(0.00001f, Math.min(0.99999f, predicted[i].getValue()));
            cost += (target[i] * (float)Math.log(value) + (1.0f - target[i]) * (float)Math.log(1.0f - value));

            if(Float.isNaN(cost)) {
                System.out.println("NaN");
            }
        }


        return -cost/size;
    }

    @Override
    public void derive(Neuron[] predicted, float [] target) {
        validate(predicted, target);

        int size = predicted.length;

        for(int i = 0; i < size; i++) {
            float grad = predicted[i].getValue() - target[i];
            predicted[i].setGrad(grad);
        }
    }
}
