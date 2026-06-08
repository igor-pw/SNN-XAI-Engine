package xai;

import structure.Layer;
import structure.NeuralNetwork;
import structure.Neuron;

public class LayerwiseGradientFeatureAttribution
{
    private final float [] saliencyMaps;
    private final float [] sensitivityMaps;

    public LayerwiseGradientFeatureAttribution(int size) {
        saliencyMaps = new float[size];
        sensitivityMaps = new float[size];
    }

    public float [] computeLayerwiseGradientFeatureAttribution(float [] target, float [] input, NeuralNetwork neuralNetwork) {
        neuralNetwork.forward(input, false);
        neuralNetwork.backwardWithTargetGrad(target, input);

        float [] inputGrad = neuralNetwork.getLayer(0).computeInputGradient(input);
        computeMaps(input, inputGrad);

        float [] gradientAttribution = new float[input.length];

        for(int i = 0; i < input.length; i++) {
            float sign = Math.signum(inputGrad[i]);
            float combinedMaps = (float)Math.log(1 + Math.abs(saliencyMaps[i]*sensitivityMaps[i]));

            gradientAttribution[i] = sign*combinedMaps;
        }

        return gradientAttribution;
    }

    private void computeMaps(float [] input, float [] inputGrad) {
        for(int i = 0; i < input.length; i++) {
            saliencyMaps[i] = inputGrad[i]*input[i];
            sensitivityMaps[i] = inputGrad[i]*inputGrad[i];
        }
    }
}