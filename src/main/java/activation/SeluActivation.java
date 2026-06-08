package activation;

public class SeluActivation implements HiddenActivation
{
    public final static float lambda = 1.0507009873554804934193349852946f;
    public final static float alfa = 1.6732632423543772848170429916717f;

    @Override
    public float activate(float input) {
        //if(input > 88.0f) input = 88.0f;
        //if(input < -88.0f) input = -88.0f;
        return input > 0.0f ? lambda * input : lambda * alfa * (float)(Math.exp(input) - 1.0f);
    }

    @Override
    public float derive(float input) {
        return input > 0.0f ? lambda : lambda * alfa * (float)Math.exp(input);
    }
}