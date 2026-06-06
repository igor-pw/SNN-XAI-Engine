package regularization;

import structure.Neuron;

public class LabelSmoothing
{
    private final double epsilon;

    public LabelSmoothing() {
        epsilon = 0.0;
    }

    public LabelSmoothing(double epsilon) {
        this.epsilon = epsilon;
    }

    public void regulate(double [][] target) {
        for(int i = 0; i < target.length; i++) {
            for(int j = 0; j < target[0].length; j++) {
                target[i][j] = target[i][j] * (1.0 - epsilon) + epsilon / target[0].length;
            }
        }
    }
}
