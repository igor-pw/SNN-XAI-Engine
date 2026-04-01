package unit.structure;

import activation.ActivationFunc;
import activation.LinearActivation;
import activation.SeluActivation;
import activation.ReluActivation;
import structure.Scalar;

public abstract class StructureTest
{
    protected ActivationFunc linear = new LinearActivation();
    protected ActivationFunc relu = new ReluActivation();
    protected ActivationFunc selu = new SeluActivation();

    protected double [] getResult(Scalar[] output) {
        int size = output.length;
        double [] result = new double[size];

        for(int i = 0; i < size; i++) {
            result[i] = output[i].getValue();
        }

        return result;
    }
}


