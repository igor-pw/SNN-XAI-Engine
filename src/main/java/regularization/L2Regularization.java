package regularization;

import structure.Layer;

public class L2Regularization
{
    private final double decay;

    public L2Regularization() {
        this.decay = 0.0001;
    }

    public L2Regularization(double decay) {
        this.decay = decay;
    }

    public double regulate(Layer [] layer) {
        double loss = 0.0;
        for(int i = 0; i < layer.length; i++) {
            double [][] weight = layer[i].getWeight();
            double [][] weightGrad = layer[i].getWeightGrad();

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
