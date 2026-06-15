package optimization.scheduler;

public class NoOpScheduler implements Scheduler
{
    @Override
    public float step(float learningRate, int epoch) {
        return learningRate;
    }
}
