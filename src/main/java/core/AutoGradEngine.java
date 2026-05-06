package core;

import activation.OutputActivation;
import loss.AbstractLossFunc;
import regularization.Regulator;
import structure.Neuron;

import java.util.*;


public class AutoGradEngine
{
    public static void backward(Neuron [] computationalGraph, Regulator dropout, Neuron [] predicted, double [] target, AbstractLossFunc lossFunc, OutputActivation outputActivation) {
        prepareGrads(predicted, target, lossFunc, outputActivation);

        for(int i = computationalGraph.length - 1; i >= 0; i--) {
                computationalGraph[i].multiplyGrad(dropout.derive(computationalGraph[i]));
                computationalGraph[i].backward(dropout);
        }
    }

    static void prepareGrads(Neuron [] predicted, double [] target, AbstractLossFunc lossFunc, OutputActivation outputActivation) {
        lossFunc.derive(predicted, target);
        outputActivation.derive(predicted, target);
    }
}
