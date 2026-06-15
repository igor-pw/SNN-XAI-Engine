package execution;

import data.Dataset;
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

    private Explainer(Builder builder, NeuralNetwork neuralNetwork, int dataSize) {
        this.neuralNetwork = neuralNetwork;
        this.alpha = (float)builder.alpha;
        this.beta = (float)builder.beta;
        layerwiseRelevancePropagation = new LayerwiseRelevancePropagation(alpha, beta);
        layerwiseGradientFeatureAttribution = new LayerwiseGradientFeatureAttribution(dataSize);
    }

    public static class Builder {
        private double alpha = 1.0;
        private double beta = 0.0;

        public Builder alpha(double alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder beta(double beta) {
            this.beta = beta;
            return this;
        }

        public Explainer build(NeuralNetwork neuralNetwork, int dataSize) {
            return new Explainer(this, neuralNetwork, dataSize);
        }
    }


    public float [] performLayerwiseRelevancePropagation(float [] input) {
       return layerwiseRelevancePropagation.computeLayerwiseRelevancePropagation(input, neuralNetwork);
    }

    public float [] performLayerwiseGradientFeatureAttribution(float [] target, float [] input) {
        return layerwiseGradientFeatureAttribution.computeLayerwiseGradientFeatureAttribution(target, input, neuralNetwork);
    }

    public int getPredictionIndex() { return layerwiseRelevancePropagation.getPredictionIndex(); }
}
