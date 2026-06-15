package optimization.stopping;

import structure.Layer;
import structure.NeuralNetwork;

public class EarlyStopping implements Stopping {
    private final int patience;
    private float bestValLoss = Float.MAX_VALUE;
    private int patienceCounter = 0;

    public EarlyStopping(int patience) {
        this.patience = patience;
    }

    @Override
    public boolean shouldStop(float valLoss) {
        if (valLoss < bestValLoss) {
            bestValLoss = valLoss;
            patienceCounter = 0;
        } else {
            patienceCounter++;
        }

        return patienceCounter >= patience;
    }
}
