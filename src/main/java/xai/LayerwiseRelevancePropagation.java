package xai;

import structure.Layer;
import structure.NeuralNetwork;
import structure.Neuron;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LayerwiseRelevancePropagation
{
    private final double alpha;
    private final double beta;
    private int predictionIndex = 0;

    public LayerwiseRelevancePropagation(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public double [] computeLayerwiseRelevancePropagation(double [] input, NeuralNetwork neuralNetwork) {
        Layer[] layer = neuralNetwork.getLayer();

        double [] predict = predict(input, neuralNetwork);

        double [] relevance = new double [predict.length];

        if(relevance.length > 1) {
            predictionIndex = argMax(predict);
            relevance[predictionIndex] = 1.0;
        }
        else {
            relevance[0] = 1.0;
        }

        for(int i = layer.length - 1; i >= 1; i--) {
            relevance = compute(layer[i], layer[i-1].getDoubleOutput(), relevance);
        }

        return compute(layer[0], input, relevance);
    }

    private double [] compute(Layer layer, double [] input, double [] relevance) {
        double [][] weight = layer.getWeight();
        double [] newRelevance = new double[input.length];

        double [] positiveSum = new double[relevance.length];
        double [] negativeSum = new double[relevance.length];

        for(int j = 0; j < relevance.length; j++) {
            for(int k = 0; k < newRelevance.length; k++) {
                double value = input[k]*weight[j][k];
                positiveSum[j] += Math.max(0, value);
                negativeSum[j] += Math.min(0, value);
            }
        }

        for(int i = 0; i < newRelevance.length; i++) {
            for(int j = 0; j < relevance.length; j++) {

                double positiveValue = Math.max(0, input[i]*weight[j][i]);
                double negativeValue = Math.min(0, input[i]*weight[j][i]);

                newRelevance[i] += relevance[j]*((alpha*(positiveValue/positiveSum[j]) - beta*(negativeValue/negativeSum[j])));
            }
        }

        return newRelevance;
    }

    private double [] predict(double [] input, NeuralNetwork neuralNetwork) {
        Neuron[] scalarResult = neuralNetwork.forward(input, false);

        int size = scalarResult.length;

        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = scalarResult[i].getValue();
        }

        return result;
    }

    private int argMax(double [] output) {
        int bestIndex = 0;

        for(int i = 0; i < output.length; i++) {
            if(output[i] > output[bestIndex]) {
                bestIndex = i;
            }
        }

        return bestIndex;
    }



    public int getPredictionIndex() { return predictionIndex; }
}
