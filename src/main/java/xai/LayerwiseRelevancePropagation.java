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
    private final float alpha;
    private final float beta;
    private int predictionIndex = 0;

    public LayerwiseRelevancePropagation(float alpha, float beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public float [] computeLayerwiseRelevancePropagation(float [] input, NeuralNetwork neuralNetwork) {
        Layer[] layer = neuralNetwork.getLayer();

        float [] predict = predict(input, neuralNetwork);

        float [] relevance = new float [predict.length];

        if(relevance.length > 1) {
            predictionIndex = argMax(predict);
            relevance[predictionIndex] = 1.0f;
        }
        else {
            relevance[0] = 1.0f;
        }

        for(int i = layer.length - 1; i >= 1; i--) {
            relevance = compute(layer[i], layer[i-1].getDoubleOutput(), relevance);
        }

        return compute(layer[0], input, relevance);
    }

    private float [] compute(Layer layer, float [] input, float [] relevance) {
        float [][] weight = layer.getWeight();
        float [] newRelevance = new float[input.length];

        float [] positiveSum = new float[relevance.length];
        float [] negativeSum = new float[relevance.length];

        for(int j = 0; j < relevance.length; j++) {
            for(int k = 0; k < newRelevance.length; k++) {
                float value = input[k]*weight[j][k];
                positiveSum[j] += Math.max(0, value);
                negativeSum[j] += Math.min(0, value);
            }
        }

        for(int i = 0; i < newRelevance.length; i++) {
            for(int j = 0; j < relevance.length; j++) {

                float positiveValue = Math.max(0, input[i]*weight[j][i]);
                float negativeValue = Math.min(0, input[i]*weight[j][i]);

                newRelevance[i] += relevance[j]*((alpha*(positiveValue/positiveSum[j]) - beta*(negativeValue/negativeSum[j])));
            }
        }

        return newRelevance;
    }

    private float [] predict(float [] input, NeuralNetwork neuralNetwork) {
        Neuron[] scalarResult = neuralNetwork.forward(input, false);

        int size = scalarResult.length;

        float [] result = new float[size];

        for(int i = 0; i < size; i++) {
            result[i] = scalarResult[i].getValue();
        }

        return result;
    }

    private int argMax(float [] output) {
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
