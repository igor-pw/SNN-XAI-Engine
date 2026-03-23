package structure;

import activation.ActivationFunc;
import core.ScalarPool;

public class Layer
{
    private Scalar[][] weight;
    private final Scalar[] bias;
    private final Scalar[] output;
    private final ActivationFunc activation;

    public Layer(int currentSize, int nextSize, ScalarPool scalarPool, Scalar[] parameter, ActivationFunc activation) {
        weight = new Scalar[currentSize][nextSize];
        bias = new Scalar[currentSize];
        output = new Scalar[currentSize];
        this.activation = activation;

        if(nextSize == 0) {
            weight = null;
        }


        if(weight != null) {
            for(int i = 0; i < weight.length; i++) {
                for(int j = 0; j < weight[i].length; j++) {
                    weight[i][j] = scalarPool.borrow();
                }

                bias[i] = scalarPool.borrow();
                output[i] = scalarPool.borrow();
            }
        }
        else {
            for(int i =0; i < output.length; i++) {
                bias[i] = scalarPool.borrow();
                output[i] = scalarPool.borrow();
            }
        }
    }

    public int getRows() { return weight.length; }
    public int getCols() { return weight[0].length; }
    public Scalar [][] getWeight() { return weight; }
}
