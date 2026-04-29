package operator;

import activation.HiddenActivation;
import structure.Scalar;

public class ForwardOperator
{
    /*public static Scalar add(Scalar x1, Scalar x2) {
        double x1_value = x1.getValue();
        double x2_value = x2.getValue();

        Scalar result = new Scalar(x1_value + x2_value, x1, x2);
        result.propagateGrad = BackwardOperator.add(result);

        return result;
    }

    public static Scalar add(Scalar [] scalar) {
        double sum = 0.0;
        for(Scalar nScalar : scalar) {
            sum += nScalar.getValue();
        }

        Scalar sumResult = new Scalar(sum, scalar);
        sumResult.propagateGrad = BackwardOperator.add(sumResult);
        sumResult.forwardOperator = ForwardOperator.add(sumResult);

        return sumResult;
    }

    public static Runnable add(Scalar scalar) {
        return () -> {
            Scalar[] parent = scalar.getParent();

            double sum = 0.0;

            for (Scalar value : parent) {
                sum += value.getValue();
            }

            scalar.setValue(sum);
        };
    }

    public static Scalar multiply(Scalar x1, Scalar x2) {
        double x1_value = x1.getValue();
        double x2_value = x2.getValue();

        Scalar result = new Scalar(x1_value * x2_value, x1, x2);
        result.propagateGrad = BackwardOperator.multiply(result);
        result.forwardOperator = ForwardOperator.multiply(result);

        return result;
    }

    public static Runnable multiply(Scalar scalar) {
        return () -> {
            Scalar [] parent = scalar.getParent();

            double mul = parent[0].getValue() * parent[1].getValue();

            scalar.setValue(mul);
        };
    }

    public static Scalar [] activate(Scalar [] input, HiddenActivation activationFunc) {
        Scalar [] output = new Scalar[input.length];

        for(int i = 0; i < input.length; i++) {
            output[i] = new Scalar(input[i]);
            output[i].propagateGrad = BackwardOperator.activate(output[i]);
            output[i].forwardOperator = ForwardOperator.activate(output[i], activationFunc);
        }

        activationFunc.activate(output);

        return output;
    }

    public static Runnable activate(Scalar scalar, HiddenActivation activationFunc) {
        return () -> {
            Scalar parent = scalar.getParent()[0];

            scalar.setValue(parent.getValue());

            activationFunc.activate(scalar);
        };
    }

    public static Scalar compute(Scalar [] weight, Scalar [] input, Scalar bias, HiddenActivation activationFunc) {
        int size = weight.length;
        double sum = bias.getValue();

        for(int i = 0; i < size; i++) {
            sum += weight[i].getValue() * input[i].getValue();
        }
    }*/
}
