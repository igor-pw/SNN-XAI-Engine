package structure;

public class Scalar
{
    private double value;
    private double grad;
    private double hessian;

    public Runnable forwardOperator = null;

    public Runnable propagateGrad = null;
    public Runnable propagateHessian = null;

    public Scalar() {
        value = 0.0;
        grad = 0.0;
        hessian = 0.0;
    }

    public Scalar(double value) {
        this.value = value;
        grad = 0.0;
        hessian = 0.0;
    }

    public void setValue(double value) { this.value = value; }
    public double getValue() { return value; }
    public void setGrad(double grad) { this.grad = grad; }
    public double getGrad() { return grad; }
    public void addGrad(double grad) { this.grad += grad; }
}




