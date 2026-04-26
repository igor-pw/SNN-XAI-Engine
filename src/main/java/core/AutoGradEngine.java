package core;

import loss.AbstractLossFunc;
import structure.Neuron;
import structure.Scalar;

import java.util.*;


public class AutoGradEngine
{
    public static void backward(Neuron [] computationalGraph, Neuron [] predicted, double [] target, AbstractLossFunc lossFunc) {
        //Deque<Scalar> topologicallySortedQueue = topologicalSort(predicted);
        prepareGrads(predicted, target, lossFunc);

        //while(!topologicallySortedQueue.isEmpty()) {
        for(int i = computationalGraph.length - 1; i >= 0; i--) {
                computationalGraph[i].backward();
        }
    }

    static void prepareGrads(Neuron [] predicted, double [] target, AbstractLossFunc lossFunc) {
        lossFunc.derive(predicted, target);
    }
}
