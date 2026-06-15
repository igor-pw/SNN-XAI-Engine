package optimization.stopping;

import structure.NeuralNetwork;

public interface Stopping
{
    boolean shouldStop(float valLoss);
}
