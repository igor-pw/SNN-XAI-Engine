public class Scalar
{
    private double value;
    private double grad;
    private double hessian;
    private Scalar[] parents;
    Runnable propagateGrad = null;
    Runnable propagateHessian = null;

    public Scalar() {
        value = 0.0;
        grad = 0.0;
        hessian = 0.0;
        parents = null;
    }
}
