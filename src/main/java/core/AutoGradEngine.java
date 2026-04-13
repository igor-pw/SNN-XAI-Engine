package core;

import loss.AbstractLossFunc;
import structure.Scalar;

import java.util.*;


public class AutoGradEngine
{

    public static void backward(Scalar[] predicted, double [] target, AbstractLossFunc lossFunc) {
        Deque<Scalar> topologicallySortedQueue = topologicalSort(predicted);
        prepareGrads(predicted, target, lossFunc);

        while(!topologicallySortedQueue.isEmpty()) {
            Scalar current = topologicallySortedQueue.pop();
            if(current.getParent() != null) {
                current.propagateGrad.run();
            }
        }
    }

    //for tests
    public static void backward(Scalar[] predicted) {
        Deque<Scalar> topologicallySortedQueue = topologicalSort(predicted);

        while(!topologicallySortedQueue.isEmpty()) {
            Scalar current = topologicallySortedQueue.pop();
            if(current.getParent() != null) {
                current.propagateGrad.run();
            }
        }
    }

    static Deque<Scalar> topologicalSort(Scalar [] input) {
        Deque<Scalar> topologicallySortedQueue = new ArrayDeque<>();
        Deque<Scalar> queue = new ArrayDeque<>();
        Set<Scalar> visited = new HashSet<>();

        for(Scalar scalar : input) {
            queue.push(scalar);

            while(!queue.isEmpty()) {
               Scalar current = queue.peek();

               boolean allParentsVisited = true;
               if (current.getParent() != null) {
                   Scalar [] parents = current.getParent();

                   for(Scalar parent : parents) {
                       if(visited.add(parent)) {
                           queue.push(parent);
                           allParentsVisited = false;
                           break;
                       }
                   }
               }

               if(allParentsVisited) {
                   topologicallySortedQueue.addFirst(queue.pop());
               }
            }
        }

        System.out.println(topologicallySortedQueue);
        return topologicallySortedQueue;
    }

    static void prepareGrads(Scalar [] predicted, double [] target, AbstractLossFunc lossFunc) {
        lossFunc.derive(predicted, target);
    }
}
