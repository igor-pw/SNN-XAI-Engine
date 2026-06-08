package execution;

import structure.Layer;
import structure.NeuralNetwork;
import structure.Neuron;
import xai.LayerwiseGradientFeatureAttribution;
import xai.LayerwiseRelevancePropagation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explainer
{
    private final NeuralNetwork neuralNetwork;
    private final LayerwiseRelevancePropagation layerwiseRelevancePropagation;
    private final LayerwiseGradientFeatureAttribution layerwiseGradientFeatureAttribution;
    private float alpha = 1.0f;
    private float beta = 0.0f;
    private int predictionIndex = 0;

    public Explainer(NeuralNetwork neuralNetwork, float alpha, float beta, int size) {
        this.neuralNetwork = neuralNetwork;
        layerwiseRelevancePropagation = new LayerwiseRelevancePropagation(alpha, beta);
        layerwiseGradientFeatureAttribution = new LayerwiseGradientFeatureAttribution(size);
    }

    public float [] performLayerwiseRelevancePropagation(float [] input, NeuralNetwork neuralNetwork) {
       return layerwiseRelevancePropagation.computeLayerwiseRelevancePropagation(input, neuralNetwork);
    }

    public float [] performLayerwiseGradientFeatureAttribution(float [] target, float [] input, NeuralNetwork neuralNetwork) {
        return layerwiseGradientFeatureAttribution.computeLayerwiseGradientFeatureAttribution(target, input, neuralNetwork);
    }

    public int getPredictionIndex() { return layerwiseRelevancePropagation.getPredictionIndex(); }
}
