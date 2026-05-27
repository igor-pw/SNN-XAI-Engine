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
    private double alpha = 1.0;
    private double beta = 0.0;
    private int predictionIndex = 0;

    public Explainer(NeuralNetwork neuralNetwork, double alpha, double beta, int size) {
        this.neuralNetwork = neuralNetwork;
        layerwiseRelevancePropagation = new LayerwiseRelevancePropagation(alpha, beta);
        layerwiseGradientFeatureAttribution = new LayerwiseGradientFeatureAttribution(size);
    }

    public double [] performLayerwiseRelevancePropagation(double [] input, NeuralNetwork neuralNetwork) {
       return layerwiseRelevancePropagation.computeLayerwiseRelevancePropagation(input, neuralNetwork);
    }

    public double [] performLayerwiseGradientFeatureAttribution(double [] target, double [] input, NeuralNetwork neuralNetwork) {
        return layerwiseGradientFeatureAttribution.computeLayerwiseGradientFeatureAttribution(target, input, neuralNetwork);
    }

    public int getPredictionIndex() { return layerwiseRelevancePropagation.getPredictionIndex(); }
}
