package optimization.scheduler;

public interface Scheduler {

    float step(float learningRate, int epoch);
}
