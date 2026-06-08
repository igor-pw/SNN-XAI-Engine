package regularization;

import structure.Neuron;

public class LabelSmoothing
{
    private final float epsilon;

    public LabelSmoothing() {
        epsilon = 0.0f;
    }

    public LabelSmoothing(float epsilon) {
        this.epsilon = epsilon;
    }

    public void regulate(float [][] target) {
        for(int i = 0; i < target.length; i++) {
            for(int j = 0; j < target[0].length; j++) {
                target[i][j] = target[i][j] * (1.0f - epsilon) + epsilon / target[0].length;
            }
        }
    }
}
