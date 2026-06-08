package initialization;

import structure.Layer;

import java.util.Random;

public class LeCunInitializer implements Initializer
{
    private final Random rand;

    public LeCunInitializer(long seed) {
        rand = new Random(seed);
    }

    @Override
    public void initialize(Layer... layer) {
        float std;
        float [][] weight;

        for(int i = 0; i < layer.length; i++) {
            weight = layer[i].getWeight();
            std = (float)Math.sqrt(1.0 / layer[i].getInputSize());

            for(int j = 0; j < layer[i].getOutputSize(); j++) {
                for(int k = 0; k < layer[i].getInputSize(); k++) {
                    weight[j][k] = (float)rand.nextGaussian()*std;
                }
            }
        }
    }
}
