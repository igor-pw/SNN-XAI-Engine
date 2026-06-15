package regularization.dropout;

import structure.Neuron;

import java.util.Random;

public class AlphaDropout implements Dropout
{
    private static final float saturation = -1.758099f;
    private final float q;
    private final float a;
    private final float b;
    private final boolean [] dropoutMask;
    private final Random rand = new Random(42);

    public AlphaDropout(int size, float p) {
        dropoutMask = new boolean[size];
        q = 1.0f - p;
        a = (float)Math.pow((q + Math.pow(saturation, 2)*q*(1.0f - q)), -0.5f);
        b = (float)-Math.pow((q + Math.pow(saturation, 2)*q*(1.0f - q)), -0.5f)*((1.0f - q)*saturation);
    }

    @Override
    public void regulate(Neuron [] neuron) {

        if(b == 1.0) return;

        for(int i = 0; i < dropoutMask.length; i++) {
            float r = rand.nextFloat(1.0f);
            float d = q <= r ? 0.0f : 1.0f;

            float value = neuron[i].getValue();
            neuron[i].setValue(a * (value * d + saturation * (1.0f - d)) + b);

            dropoutMask[i] = (d == 0.0f);
        }
    }

    public float derive(int i) {
        if(q == 1.0) return 1.0f;

        return dropoutMask[i] ? 0.0f : a;
    }
}
