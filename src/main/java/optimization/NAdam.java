package optimization;

import structure.Layer;

public class NAdam implements Optimizer
{
    private final float beta1;
    private final float beta2;
    private final float momentumDecay;
    private final float epsilon;
    private float [][] weightMomentum;
    private float [][] weightVelocity;
    private float [][] biasMomentum;
    private float [][] biasVelocity;
    private float muAcc = 1.0f;
    private int steps = 0;

    private NAdam(Builder builder) {
        this.beta1 = builder.beta1;
        this.beta2 = builder.beta2;
        this.momentumDecay = builder.momentumDecay;
        this.epsilon = builder.epsilon;
    }

    public void init(Layer [] layer) {
        weightMomentum = new float[layer.length][];
        weightVelocity = new float[layer.length][];
        biasMomentum = new float[layer.length][];
        biasVelocity = new float[layer.length][];

        for(int i = 0; i < layer.length; i++) {
            int input = layer[i].getInputSize();
            int output = layer[i].getOutputSize();

            weightMomentum[i] = new float[input*output];
            weightVelocity[i] = new float[input*output];
            biasMomentum[i] = new float[output];
            biasVelocity[i] = new float[output];
        }
    }

    public static class Builder {
        private float beta1 = 0.9f;
        private float beta2 = 0.999f;
        private float momentumDecay = 4e-3f;
        private float epsilon = 1e-8f;

        public Builder beta1(float beta1) {
            this.beta1 = beta1;
            return this;
        }

        public Builder beta2(float beta2) {
            this.beta2 = beta2;
            return this;
        }

        public Builder momentumDecay(float momentumDecay) {
            this.momentumDecay = momentumDecay;
            return this;
        }

        public Builder epsilon(float epsilon) {
            this.epsilon = epsilon;
            return this;
        }

        public NAdam build() {
            return new NAdam(this);
        }
    }

    @Override
    public void optimize(Layer [] layer, float learningRate, int batch) {
        steps++;
        float mu = beta1 * (1.0f - 0.5f * (float) Math.pow(0.96f, steps * momentumDecay));
        float muNext = beta1 * (1.0f - 0.5f * (float) Math.pow(0.96f, (steps + 1.0f) * momentumDecay));
        muAcc *= mu;

        for (int i = 0; i < layer.length; i++) {
            float [][] weight = layer[i].getWeight();
            float [][] weightGrad = layer[i].getWeightGrad();
            float [] bias = layer[i].getBias();
            float [] biasGrad = layer[i].getBiasGrad();

            final int layerIndex = i;
            final int weightSize = weight[0].length;

            java.util.stream.IntStream.range(0, bias.length).parallel().forEach(j -> {
                float gradBias = biasGrad[j] / batch;

                biasMomentum[layerIndex][j] = beta1 * biasMomentum[layerIndex][j] + (1.0f - beta1) * gradBias;
                biasVelocity[layerIndex][j] = beta2 * biasVelocity[layerIndex][j] + (1.0f - beta2) * gradBias * gradBias;

                float mHatBias = muNext * biasMomentum[layerIndex][j] / (1.0f - muAcc * muNext) + (1.0f - mu) *  gradBias / (1.0f - muAcc);
                float vHatBias = biasVelocity[layerIndex][j] / (1.0f - (float) Math.pow(beta2, steps));

                bias[j] -= learningRate * mHatBias / ((float) Math.sqrt(vHatBias) + epsilon);

                for (int k = 0; k < weightSize; k++) {
                    float gradWeight = weightGrad[j][k] / batch;
                    int index = j * weightSize + k;

                    weightMomentum[layerIndex][index] = beta1 * weightMomentum[layerIndex][index] + (1.0f - beta1) * gradWeight;
                    weightVelocity[layerIndex][index] = beta2 * weightVelocity[layerIndex][index] + (1.0f - beta2) * gradWeight * gradWeight;

                    float mHatWeight = muNext * weightMomentum[layerIndex][index] / (1.0f - muAcc * muNext) + (1.0f - mu) * gradWeight / (1.0f - muAcc);
                    float vHatWeight = weightVelocity[layerIndex][index] / (1.0f - (float) Math.pow(beta2, steps));

                    weight[j][k] -= learningRate * mHatWeight / ((float) Math.sqrt(vHatWeight) + epsilon);
                }
            });
        }
    }











        /*steps++;
        float b1 = 1.0f - betaPower1;
        float b2 = 1.0f - betaPower2;
        int weightIndex = 0;
        int biasIndex = 0;

        for (int i = 0; i < layer.length; i++) {
            float[][] weight = layer[i].getWeight();
            float[][] weightGrad = layer[i].getWeightGrad();
            float[] bias = layer[i].getBias();
            float[] biasGrad = layer[i].getBiasGrad();

            for (int j = 0; j < weight.length; j++) {
                float grad = biasGrad[j] / batch;

                if(Float.isNaN(bias[j]) || Float.isInfinite(bias[j])) {
                   new Throwable("bias NaN").printStackTrace();
                   System.exit(1);
                }

                biasMomentum[biasIndex] = (beta1 * biasMomentum[biasIndex] + (1.0f - beta1) * grad);
                biasVelocity[biasIndex] = (beta2 * biasVelocity[biasIndex] + (1.0f - beta2) * grad * grad);

                float mHat = (biasMomentum[biasIndex] / b1);
                float vHat = (biasVelocity[biasIndex] / b2);
                float gHat = grad / b1;

                //Nesterov momentum
                mHat = beta1 * mHat + (1.0f - beta1) * gHat;

                bias[j] -= (learningRate * mHat) / (float)(Math.sqrt(vHat) + epsilon);
                biasIndex++;

                for (int k = 0; k < weight[j].length; k++) {
                    grad = weightGrad[j][k] / batch;

                    if(Float.isNaN(weight[j][k]) || Float.isInfinite(weight[j][k])) {
                        new Throwable("weight NaN").printStackTrace();
                        System.exit(1);
                    }


                    weightMomentum[weightIndex] = (beta1 * weightMomentum[weightIndex] + (1.0f - beta1) * grad);
                    weightVelocity[weightIndex] = (beta2 * weightVelocity[weightIndex] + (1.0f - beta2) * grad * grad);

                    mHat = (weightMomentum[weightIndex] / b1);
                    vHat = (weightVelocity[weightIndex] / b2);
                    gHat = grad / b1;

                    //Nesterov momentum
                    mHat = beta1 * mHat + (1.0f - beta1) * gHat;

                    weight[j][k] -= (learningRate * mHat) / (float)(Math.sqrt(vHat) + epsilon);
                    weightIndex++;
                }
            }
        }

        betaPower1 *= beta1;
        betaPower2 *= beta2;
    }*/
}
