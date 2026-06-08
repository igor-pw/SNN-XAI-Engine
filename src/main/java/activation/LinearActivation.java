package activation;

public class LinearActivation implements HiddenActivation
{
    @Override
    public float activate(float input) {
        return input;
    }

    @Override
    public float derive(float input) {
        return 1.0f;
    }
}
