package loss;

import structure.Neuron;

public class MseLoss extends AbstractLossFunc
{
    @Override
    public float compute(Neuron[] predicted, float [] target) {
        validate(predicted, target);

        int size = predicted.length;
        float loss = 0.0f;

        for(int i = 0; i < size; i++) {
            loss += (float)Math.pow(predicted[i].getValue() - target[i], 2);
        }

        return loss/size;
    }

    @Override
    public void derive(Neuron [] predicted, float [] target) {
        validate(predicted, target);

        int size = predicted.length;

        for(int i = 0; i < size; i++) {
            float grad = 2.0f * (predicted[i].getValue() - target[i]);
            predicted[i].setGrad(grad);
        }
    }

}
