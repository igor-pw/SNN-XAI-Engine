package xai;

import structure.NeuralNetwork;

public class GenerativeVisualAttribution
{
    /*public void generateFeaturePattern(float [] input, float [] target, NeuralNetwork neuralNetwork, float learningRate) {
        neuralNetwork.forward(input, false);
        neuralNetwork.backwardWithTargetGrad(target, input);

        float [] inputGrad = neuralNetwork.getLayer(0).computeInputGradient(input);

        for(int i = 0; i < input.length; i++) {
            input[i] += learningRate*inputGrad[i];

            if (input[i] < 0.0) input[i] = 0.0f;
            if (input[i] > 1.0) input[i] = 1.0f;


        }
    }*/

}
