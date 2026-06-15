package optimization.clipping;

import structure.Layer;

public class NoOpGradientClipping implements GradientClipping
{

    @Override
    public void clip(Layer[] layer) {
    }
}
