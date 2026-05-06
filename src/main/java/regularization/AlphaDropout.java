package regularization;

import structure.Neuron;

import java.util.Random;

public class AlphaDropout implements Regulator
{
    private static final double saturation = -1.758099;
    private final double q;
    private final double a;
    private final double b;
    private final Random rand = new Random(42);

    public AlphaDropout(double p) {
        q = 1.0 - p;
        a = Math.pow((q + Math.pow(saturation, 2)*q*(1.0 - q)), -0.5);
        b = -Math.pow((q + Math.pow(saturation, 2)*q*(1.0 - q)), -0.5)*((1.0 - q)*saturation);
    }

    @Override
    public boolean regulate(Neuron neuron) {

        double r = rand.nextDouble(1.0);
        double d = q <= r ? 0.0 : 1.0;

        double value = neuron.getValue();
        neuron.setValue(a*(value*d + saturation*(1.0 -d)) + b);

        return d == 0.0;
    }

    public double derive(Neuron neuron) {
        return neuron.getDropoutMask() ? 0.0 : a;
    }
}
