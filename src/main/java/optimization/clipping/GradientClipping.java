package optimization.clipping;

import structure.Layer;

import java.io.Serializable;

public interface GradientClipping extends Serializable
{
    void clip(Layer[] layer);
}
