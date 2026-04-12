package core;

import structure.Scalar;

import java.util.*;


public class AutoGradEngine
{
    private final List<Scalar> stack = new Stack<>();
    private final Set<Scalar> visited = new HashSet<>();
    private final Deque<Scalar> queue = new ArrayDeque<>();

    public void backward(Scalar[] input) {
        //to do
    }

    private void topologicalSort(Scalar [] input) {
        //to do
    }
}
