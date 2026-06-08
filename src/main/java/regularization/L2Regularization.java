package regularization;

import structure.Layer;

public class L2Regularization
{
    private final float decay;

    public L2Regularization() {
        this.decay = 0.0001f;
    }

    public L2Regularization(float decay) {
        this.decay = decay;
    }

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
