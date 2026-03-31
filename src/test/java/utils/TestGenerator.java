package utils;

import activation.ActivationFunc;
import activation.SeluActivation;
import initializer.Initializer;
import initializer.LeCunInitializer;
import jdk.jshell.spi.ExecutionControl;
import structure.Layer;
import structure.NeuralNetwork;
import structure.Scalar;

import java.util.Random;

public class TestGenerator
{
    private static final long SEED = 337609;
    private ActivationFunc selu = new SeluActivation();
    private Initializer lecun = new LeCunInitializer();
    private Random random = new Random(SEED);

    public Layer initDefinedLayer(double [][] weight, double [] bias) {
        int inputSize = weight[0].length;
        int outputSize = weight.length;
        Layer layer = new Layer(inputSize, outputSize, selu);

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                layer.getWeight()[i][j].setValue(weight[i][j]);
            }

            layer.getBias()[i].setValue(bias[i]);
        }

        return layer;
    }

    public Layer initEqualWeightsLayer(int inputSize, int outputSize, double value) {
        Layer layer = new Layer(inputSize, outputSize, selu);

        for(int i = 0; i < outputSize; i++) {
            for(int j = 0; j < inputSize; j++) {
                layer.getWeight()[i][j].setValue(value);
            }

            layer.getBias()[i].setValue(value);
        }

        return layer;
    }

    public Layer initRandomLayer(int inputSize, int outputSize) {
        Layer layer = new Layer(inputSize, outputSize, selu);
        lecun.initialize(layer);

        return layer;
    }

    public Scalar [] initDefinedScalarVector(double... value) {
        int size = value.length;
        Scalar [] result = new Scalar[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Scalar(value[i]);
        }

        return result;
    }

    public Scalar[] initRandomScalarVector(int size, double bound) {
        Scalar [] result = new Scalar[size];
        double value;

        for(int i = 0; i < size; i++) {
            value = random.nextDouble(bound);
            result[i] = new Scalar(value);
        }

        return result;
    }

    public NeuralNetwork initDefinedNeuralNetwork(int [] structure, Scalar [][] bias, Scalar [][] ... weight) {
        throw new RuntimeException("Not implemented yet...");
    }

    public NeuralNetwork initRandomNeuralNetwork(int [] structure) {
        throw new RuntimeException("Not implemented yet...");
    }

    public NeuralNetwork initEqualsWeightsNeuralNetwork(int [] structure, double value) {
        throw new RuntimeException("Not implemented yet...");
    }
}
