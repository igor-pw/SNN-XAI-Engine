package regularization.weight;

import structure.Layer;

public class L2Regularizer implements WeightRegularizer
{
    private final float decay;

    public L2Regularizer(double decay) {
        this.decay = (float)decay;
    }

    @Override
    public float regulate(Layer [] layer) {
        float loss = 0.0f;
        for(int i = 0; i < layer.length; i++) {
            float [][] weight = layer[i].getWeight();
            float [][] weightGrad = layer[i].getWeightGrad();

            for(int j = 0; j < weight.length; j++) {
                for(int k = 0; k < weight[j].length; k++) {
                    loss += weight[j][k]*weight[j][k];
                    weightGrad[j][k] += decay*weight[j][k];
                }
            }
        }

        loss *= decay/2;
        return loss;
    }
}
