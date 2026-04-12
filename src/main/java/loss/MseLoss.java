package loss;

import structure.Scalar;

public class MseLoss extends AbstractLossFunc
{
    @Override
    public double compute(Scalar [] predicted, double [] target) {
        validate(predicted, target);

        int size = predicted.length;
        double loss = 0.0;

        for(int i = 0; i < size; i++) {
            loss += Math.pow(predicted[i].getValue() - target[i], 2);
        }

        return loss/size;
    }

}
