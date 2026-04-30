package structure;

import activation.HiddenActivation;

public class Layer
{
    private final Scalar[][] weight;
    private final Scalar[] bias;
    private final Neuron [] output;
    private final HiddenActivation activation;

    public Layer(int prevSize, int currentSize, HiddenActivation activation) {
        weight = new Scalar[currentSize][prevSize];
        bias = new Scalar[currentSize];
        output = new Neuron[currentSize];
        this.activation = activation;

        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j < weight[i].length; j++) {
                weight[i][j] = new Scalar();
            }

            bias[i] = new Scalar(0.1);
        }
    }

    public int getOutputSize() { return weight.length; }
    public int getInputSize() { return weight[0].length; }
    public Scalar [][] getWeight() { return weight; }
    public Scalar [] getBias() { return bias; }
    public Neuron [] getOutput() { return output; }
    public HiddenActivation getActivation() { return activation; }
}
