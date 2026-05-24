package structure;

import activation.HiddenActivation;
import regularization.AlphaDropout;
import regularization.Regulator;

import java.util.stream.IntStream;

public class Layer
{
    private final double [][] weight;
    private final double [][] weightGrad;
    private final double [] bias;
    private final double [] biasGrad;
    private final double [] activationInput;
    private final Neuron [] output;
    private final HiddenActivation activation;
    private final Regulator dropout;

    public Layer(int prevSize, int currentSize, HiddenActivation activation, double probability) {
        weight = new double[currentSize][prevSize];
        weightGrad = new double[currentSize][prevSize];
        bias = new double[currentSize];
        biasGrad = new double[currentSize];
        activationInput = new double[currentSize];
        output = new Neuron[currentSize];

        this.activation = activation;

        for (int i = 0; i < weight.length; i++) {
            output[i] = new Neuron();
            bias[i] = 0.1;
        }

        dropout = new AlphaDropout(currentSize, probability);
    }

    public Neuron [] forward(double [] input, boolean training) {

        if(training) dropout.regulate(output);

        IntStream.range(0, output.length).parallel().forEach(i -> {
            activationInput[i] = bias[i];

            for(int j = 0; j < input.length; j++) {
                activationInput[i] += weight[i][j]*input[j];
            }

            double value = activation.activate(activationInput[i]);
            output[i].setValue(value);
        });

        return output;
    }

    public Neuron [] forward(Neuron [] input, boolean training) {

        if(training) dropout.regulate(output);

        IntStream.range(0, output.length).parallel().forEach(i -> {
        //for(int i = 0; i < output.length; i++) {
            activationInput[i] = bias[i];

            for(int j = 0; j < input.length; j++) {
                activationInput[i] += weight[i][j]*input[j].value;
            }

            double value = activation.activate(activationInput[i]);
            output[i].setValue(value);
       });

       return output;
    }

    public void backward(Neuron [] input) {
        for(int i = 0; i < output.length; i++) {
            //alphaDropout
            output[i].multiplyGrad(dropout.derive(i));

            double delta = output[i].grad * activation.derive(activationInput[i]);

            for(int j = 0; j < input.length; j++) {
                weightGrad[i][j] += input[j].value * delta;

                input[j].grad += weight[i][j] * delta;
            }

            biasGrad[i] += delta;
        }
    }

    public void backward(double [] input) {
        for(int i = 0; i < output.length; i++) {
            //alphaDropout
            output[i].multiplyGrad(dropout.derive(i));

            double delta = output[i].grad * activation.derive(activationInput[i]);

            for(int j = 0; j < input.length; j++) {
                weightGrad[i][j] += input[j] * delta;
            }

            biasGrad[i] += delta;
        }
    }

    public int getOutputSize() { return weight.length; }
    public int getInputSize() { return weight[0].length; }
    public double [][] getWeight() { return weight; }
    public double [][] getWeightGrad() { return weightGrad; }
    public double [] getBias() { return bias; }
    public double [] getBiasGrad() { return biasGrad; }
    public Neuron [] getOutput() { return output; }
    public HiddenActivation getActivation() { return activation; }
}
