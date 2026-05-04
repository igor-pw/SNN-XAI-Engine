package optimization;

import structure.Scalar;

import java.util.List;

public class NAdam implements Optimizer
{
    private static final double beta1 = 0.9;
    private static final double beta2 = 0.999;
    private static final double epsilon = 1e-5;
    private final double [] momentum;
    private final double [] velocity;
    private int steps = 0;

    public NAdam(int size) {
        momentum = new double[size];
        velocity = new double[size];
    }

    @Override
    public void optimize(List<Scalar> parameter, double learningRate, int batch) {
        steps++;
        double b1 = 1 - Math.pow(beta1, steps);
        double b2 = 1 - Math.pow(beta2, steps);

        for(int i = 0; i < parameter.size(); i++) {
            double grad = parameter.get(i).getGrad()/batch;
            //grad = Math.max(-5.0, Math.min(5.0, grad));

            momentum[i] = (beta1*momentum[i] + (1-beta1)*grad);
            velocity[i] = (beta2*velocity[i] + (1-beta2)*grad*grad);

            double mHat = (momentum[i]/b1);
            double vHat = (velocity[i]/b2);
            double gHat = grad/b1;

            //Nesterov momentum
            mHat = beta1*mHat + (1-beta1)*gHat;

            double newValue = parameter.get(i).getValue() - (learningRate*mHat)/(Math.sqrt(vHat) + epsilon);
            parameter.get(i).setValue(newValue);
        }
    }
}
