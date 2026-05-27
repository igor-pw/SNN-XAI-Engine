package xai;

import structure.Layer;
import structure.NeuralNetwork;
import structure.Neuron;

public class LayerwiseGradientFeatureAttribution
{
    private final double [] saliencyMaps;
    private final double [] sensitvityMaps;

    public LayerwiseGradientFeatureAttribution(int size) {
        saliencyMaps = new double[size];
        sensitvityMaps = new double[size];
    }

    public double [] computeLayerwiseGradientFeatureAttribution(double [] target, double [] input, NeuralNetwork neuralNetwork) {
        neuralNetwork.forward(input, false);
        neuralNetwork.backwardWithTargetGrad(target, input);

        double [] inputGrad = neuralNetwork.getLayer(0).computeInputGradient(input);
        computeMaps(input, inputGrad);

        double [] gradientAttribution = new double[input.length];

        for(int i = 0; i < input.length; i++) {
            double sign = Math.signum(inputGrad[i]);
            double combinedMaps = Math.log(1 + Math.abs(saliencyMaps[i]*sensitvityMaps[i]));

            gradientAttribution[i] = sign*combinedMaps;
        }

        return gradientAttribution;
    }

    private void computeMaps(double [] input, double [] inputGrad) {
        for(int i = 0; i < input.length; i++) {
            saliencyMaps[i] = inputGrad[i]*input[i];
            sensitvityMaps[i] = inputGrad[i]*inputGrad[i];
        }
    }
}