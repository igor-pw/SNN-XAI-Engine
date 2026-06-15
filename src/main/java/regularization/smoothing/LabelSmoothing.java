package regularization.smoothing;

public class LabelSmoothing implements Smoothing
{
    private final float epsilon;

    public LabelSmoothing(double epsilon) {
        this.epsilon = (float)epsilon;
    }

    @Override
    public void regulate(float [][] target) {
        for(int i = 0; i < target.length; i++) {
            for(int j = 0; j < target[0].length; j++) {
                target[i][j] = target[i][j] * (1.0f - epsilon) + epsilon / target[0].length;
            }
        }
    }
}
