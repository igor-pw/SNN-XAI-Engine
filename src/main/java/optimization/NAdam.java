package optimization;

import structure.Layer;

public class NAdam implements Optimizer
{
    private final double beta1;
    private final double beta2;
    private final double epsilon;
    private double [] weightMomentum;
    private double [] weightVelocity;
    private double [] biasMomentum;
    private double [] biasVelocity;
    private int steps = 0;

    private NAdam(Builder builder) {
        this.beta1 = builder.beta1;
        this.beta2 = builder.beta2;
        this.epsilon = builder.epsilon;
    }

    public void init(int weightSize, int biasSize) {
        weightMomentum = new double[weightSize];
        weightVelocity = new double[weightSize];
        biasMomentum = new double[biasSize];
        biasVelocity = new double[biasSize];
    }

    public static class Builder {
        private double beta1 = 0.9;
        private double beta2 = 0.999;
        private double epsilon = 1e-8;

        public Builder beta1(double beta1) {
            this.beta1 = beta1;
            return this;
        }

        public Builder beta2(double beta2) {
            this.beta2 = beta2;
            return this;
        }

        public Builder epsilon(double epsilon) {
            this.epsilon = epsilon;
            return this;
        }

        public NAdam build() {
            return new NAdam(this);
        }
    }

    @Override
    public void optimize(Layer [] layer, double learningRate, int batch) {
        steps++;
        double b1 = 1 - Math.pow(beta1, steps);
        double b2 = 1 - Math.pow(beta2, steps);
        int weightIndex = 0;
        int biasIndex = 0;

        for (int i = 0; i < layer.length; i++) {
            double[][] weight = layer[i].getWeight();
            double[][] weightGrad = layer[i].getWeightGrad();
            double[] bias = layer[i].getBias();
            double[] biasGrad = layer[i].getBiasGrad();

            for (int j = 0; j < weight.length; j++) {
                double grad = biasGrad[j] / batch;

                biasMomentum[biasIndex] = (beta1 * biasMomentum[biasIndex] + (1 - beta1) * grad);
                biasVelocity[biasIndex] = (beta2 * biasVelocity[biasIndex] + (1 - beta2) * grad * grad);

                double mHat = (biasMomentum[biasIndex] / b1);
                double vHat = (biasVelocity[biasIndex] / b2);
                double gHat = grad / b1;

                //Nesterov momentum
                mHat = beta1 * mHat + (1 - beta1) * gHat;

                bias[j] -= (learningRate * mHat) / (Math.sqrt(vHat) + epsilon);
                biasIndex++;

                for (int k = 0; k < weight[j].length; k++) {
                    grad = weightGrad[j][k] / batch;

                    weightMomentum[weightIndex] = (beta1 * weightMomentum[weightIndex] + (1 - beta1) * grad);
                    weightVelocity[weightIndex] = (beta2 * weightVelocity[weightIndex] + (1 - beta2) * grad * grad);

                    mHat = (weightMomentum[weightIndex] / b1);
                    vHat = (weightVelocity[weightIndex] / b2);
                    gHat = grad / b1;

                    //Nesterov momentum
                    mHat = beta1 * mHat + (1 - beta1) * gHat;

                    weight[j][k] -= (learningRate * mHat) / (Math.sqrt(vHat) + epsilon);
                    weightIndex++;
                }
            }
        }
    }
}
