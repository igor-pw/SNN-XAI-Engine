package optimization.stopping;

import structure.NeuralNetwork;

public class NoOpStopping implements Stopping
{
    @Override
    public boolean shouldStop(float valLoss) {
        return false;
    }

}
