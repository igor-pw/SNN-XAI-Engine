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

    //for tests
    /*public static void backward(Scalar[] predicted) {
        Deque<Scalar> topologicallySortedQueue = topologicalSort(predicted);

        while(!topologicallySortedQueue.isEmpty()) {
            Scalar current = topologicallySortedQueue.pop();
            if(current.getParent() != null) {
                current.propagateGrad.run();
            }
        }
    }*/

    /*static Deque<Scalar> topologicalSort(Scalar [] input) {
        Deque<Scalar> topologicallySortedQueue = new ArrayDeque<>();
        Deque<Scalar> stack = new ArrayDeque<>();
        Set<Scalar> discovered = new HashSet<>();

        for(Scalar scalar : input) {
            if(!discovered.contains(scalar)) {
                stack.push(scalar);
            }

            while(!stack.isEmpty()) {
                Scalar current = stack.peek();

                if(!discovered.contains(current)) {
                    discovered.add(current);

                    if(current.getParent() != null) {
                        for(Scalar parent : current.getParent()) {
                            if(!discovered.contains(parent)) {
                                stack.push(parent);
                            }
                        }
                    }
                } else {
                    topologicallySortedQueue.addFirst(stack.pop());
                }
            }
        }

        return topologicallySortedQueue;
    }*/

    static void prepareGrads(Neuron [] predicted, double [] target, AbstractLossFunc lossFunc) {
        lossFunc.derive(predicted, target);
    }
}
