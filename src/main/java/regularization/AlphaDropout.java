package regularization;

import structure.Neuron;

import java.util.Random;

public class AlphaDropout implements Regulator
{
    private static final double saturation = -1.758099;
    private final double q;
    private final double a;
    private final double b;
    private final boolean [] dropoutMask;
    private final Random rand = new Random(42);

    public AlphaDropout(int size, double p) {
        dropoutMask = new boolean[size];
        q = 1.0 - p;
        a = Math.pow((q + Math.pow(saturation, 2)*q*(1.0 - q)), -0.5);
        b = -Math.pow((q + Math.pow(saturation, 2)*q*(1.0 - q)), -0.5)*((1.0 - q)*saturation);
    }

    @Override
    public void regulate(Neuron [] neuron) {

        if(b == 1.0) return;

        for(int i = 0; i < dropoutMask.length; i++) {
            double r = rand.nextDouble(1.0);
            double d = q <= r ? 0.0 : 1.0;

            double value = neuron[i].getValue();
            neuron[i].setValue(a * (value * d + saturation * (1.0 - d)) + b);

            dropoutMask[i] = (d == 0.0);
        }
    }

    public double derive(int i) {
        if(q == 1.0) return 1.0;

        return dropoutMask[i] ? 0.0 : a;
    }
}
