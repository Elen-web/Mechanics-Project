public class Spring {
    private double k;

    public Spring() {
        this.k = 1.0;
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    private void setStiffness(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        int numSteps = (int) Math.ceil(t / dt);
        double[] x = new double[numSteps];

        double omega = Math.sqrt(k);
        double A = x0;
        double B = v0 / omega;

        for (int i = 0; i < numSteps; i++) {
            double t_i = i * dt;
            x[i] = A * Math.cos(omega * t_i) + B * Math.sin(omega * t_i);
        }

        return x;
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        int numSteps = (int) Math.ceil((t1 - t0) / dt);
        double[] x = new double[numSteps];

        double omega = Math.sqrt(k);
        double A = x0;
        double B = (v0 - x0 * omega) / omega;

        for (int i = 0; i < numSteps; i++) {
            double t_i = t0 + i * dt;
            x[i] = A * Math.cos(omega * t_i) + B * Math.sin(omega * t_i);
        }

        return x;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        double omega = Math.sqrt(k / m);
        int numSteps = (int) Math.ceil((t1 - t0) / dt);
        double[] x = new double[numSteps];

        double A = x0;
        double B = (v0 - x0 * omega) / omega;

        for (int i = 0; i < numSteps; i++) {
            double t_i = t0 + i * dt;
            x[i] = A * Math.cos(omega * t_i) + B * Math.sin(omega * t_i);
        }

        return x;
    }

    public Spring inSeries(Spring that) {
        double kEquivalent = this.k + that.k;
        return new Spring(kEquivalent);
    }

    public Spring inParallel(Spring that) {
        double kEquivalent = 1.0 / (1.0/this.k + 1.0/that.k);
        return new Spring(kEquivalent);
    }
}
