package structure;

public class Scalar
{
    private double value;
    private double grad;
    private double hessian;
    private Scalar[] parent;
    Runnable propagateGrad = null;
    Runnable propagateHessian = null;

    public Scalar() {
        value = 0.0;
        grad = 0.0;
        hessian = 0.0;
        parent = null;
    }

    public Scalar(double value) {
        this.value = value;
        grad = 0.0;
        hessian = 0.0;
        parent = null;
    }

    public Scalar(double value, Scalar... parent) {
        this.value = value;
        grad = 0.0;
        hessian = 0.0;
        this.parent = new Scalar[parent.length];

        for(int i = 0; i < parent.length; i++) {
            this.parent[i] = parent[i];
        }
    }

    public void setValue(double value) { this.value = value; }
    public double getValue() { return value; }
    public Scalar[] getParent() { return parent; }
}
