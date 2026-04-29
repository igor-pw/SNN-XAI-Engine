package core;

import activation.OutputActivation;
import loss.AbstractLossFunc;
import structure.Neuron;

import java.util.*;


public class AutoGradEngine
{
    public static void backward(Neuron [] computationalGraph, Neuron [] predicted, double [] target, AbstractLossFunc lossFunc, OutputActivation outputActivation) {
        prepareGrads(predicted, target, lossFunc, outputActivation);

        for(int i = computationalGraph.length - 1; i >= 0; i--) {
                computationalGraph[i].backward();
        }
    }

    static void prepareGrads(Neuron [] predicted, double [] target, AbstractLossFunc lossFunc, OutputActivation outputActivation) {
        lossFunc.derive(predicted, target);
        outputActivation.derive(predicted, target);
    }
}
