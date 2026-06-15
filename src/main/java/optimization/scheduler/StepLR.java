package optimization.scheduler;

public class StepLR implements Scheduler {

    private final float gamma;
    private final int epochStep;

    private StepLR(Builder builder) {
        this.gamma = (float)builder.gamma;
        this.epochStep = builder.epochStep;
    }

    public static class Builder {
        double gamma = 1.0;
        int epochStep = 1;

        public Builder gamma(double gamma) {
            this.gamma = gamma;
            return this;
        }

        public Builder epochStep(int epochStep) {
            this.epochStep = epochStep;
            return this;
        }

        public StepLR build() {
            return new StepLR(this);
        }

    }

    @Override
    public float step(float learningRate, int epoch) {
        if(epoch % epochStep == 0) {
            return learningRate*gamma;
        }

        return learningRate;
    }
}
