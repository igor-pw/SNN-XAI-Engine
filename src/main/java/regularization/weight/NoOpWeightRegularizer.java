package regularization.weight;

import structure.Layer;

public class NoOpWeightRegularizer implements WeightRegularizer
{
    @Override
    public float regulate(Layer[] layer) {
        return 0.0f;
    }
}
