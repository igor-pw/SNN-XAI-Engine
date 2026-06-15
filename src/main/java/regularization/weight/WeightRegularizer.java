package regularization.weight;

import structure.Layer;

public interface WeightRegularizer {

    float regulate(Layer[] layer);
}
