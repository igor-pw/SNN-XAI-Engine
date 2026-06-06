package optimization;

import structure.Layer;

import java.io.Serializable;

public class GradientNormClipping implements Serializable
{
    private final double maxNorm;

    public GradientNormClipping(double maxNorm) {
        if(maxNorm <= 0.0) {
            throw new IllegalArgumentException("Invalid maxNorm value");
        }
        this.maxNorm = maxNorm;
    }

    public void optimize(Layer[] layer) {
        double gradientNorm = 0.0;
        for(int i = 0; i < layer.length; i++) {
            double [] biasGrad = layer[i].getBiasGrad();
            double [][] weightGrad = layer[i].getWeightGrad();

            for(int j = 0; j < weightGrad.length; j++) {
                gradientNorm += biasGrad[j]*biasGrad[j];

                for(int k = 0; k < weightGrad[j].length; k++) {
                    gradientNorm += weightGrad[j][k]*weightGrad[j][k];
                }
            }
        }

        gradientNorm = Math.sqrt(gradientNorm);
        gradientNorm = maxNorm/(Math.max(maxNorm, gradientNorm));

        for(int i = 0; i < layer.length; i++) {
            double [] biasGrad = layer[i].getBiasGrad();
            double [][] weightGrad = layer[i].getWeightGrad();

            for(int j = 0; j < weightGrad.length; j++) {
                biasGrad[j] *= gradientNorm;

                for(int k = 0; k < weightGrad[j].length; k++) {
                    weightGrad[j][k] *= gradientNorm;
                }
            }
        }
    }
}
