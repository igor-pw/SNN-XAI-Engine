package structure;

import activation.ActivationFunc;
import core.Operator;

public class Layer
{
    private Scalar[][] weight;
    private final Scalar[] bias;
    private final Scalar[] output;
    private final ActivationFunc activation;

    public Layer(int prevSize, int currentSize, ActivationFunc activation) {
        weight = new Scalar[currentSize][prevSize];
        bias = new Scalar[currentSize];
        output = new Scalar[currentSize];
        this.activation = activation;

        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j < weight[i].length; j++) {
                weight[i][j] = new Scalar();
            }

            bias[i] = new Scalar();
            output[i] = new Scalar();
        }
    }

    public Scalar [] forward(Scalar [] input) {
        Operator operator = new Operator();
        int inputSize = input.length;
        int outputSize = output.length;

        // ? inputSize = this.inputSize;
        Scalar [] multiplyResult = new Scalar[inputSize];

        for(int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                multiplyResult[j] = operator.multiply(weight[i][j], input[j]);
            }

            for(int j = 0; j < inputSize - 1; j++) {
                output[i] = operator.add(multiplyResult[j], multiplyResult[j + 1]);
            }

            output[i] = operator.add(output[i], bias[i]);
        }

        return output;
    }

    public int getRows() { return weight.length; }
    public int getCols() { return weight[0].length; }
    public Scalar [][] getWeight() { return weight; }
    public Scalar [] getBias() { return bias; }
    public Scalar [] getOutput() { return output; }
}
