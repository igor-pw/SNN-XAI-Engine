package initializer;

import structure.Layer;
import structure.Scalar;

import java.util.Random;

public class LeCunInitializer implements Initializer
{
    private final Random rand = new Random();

    @Override
    public void initialize(Layer... layer) {
        double std;
        Scalar [][] weight;

        for(int i = 0; i < layer.length-1; i++) {
            weight = layer[i].getWeight();
            std = Math.sqrt(1.0 / layer[i].getRows());

            for(int j = 0; j < layer[i].getRows(); j++) {
                for(int k = 0; k < layer[i].getCols(); k++) {
                    weight[j][k].setValue(rand.nextGaussian()*std);
                }
            }
        }
    }
}
