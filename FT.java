public class FT {
    private double[] x; // input coordinates
    private int N; // number of input samples

    public FT(double[] x) {
        this.x = x;
        this.N = x.length;
    }

    // Compute the Fourier transform of the input coordinates
    public Complex[] transform() {
        Complex[] c = new Complex[N];
        for (int k = 0; k < N; k++) {
            c[k] = new Complex(0, 0);
            for (int n = 0; n < N; n++) {
                double angle = 2 * Math.PI * k * n / N;
                c[k] = c[k].plus(new Complex(x[n], 0).times(new Complex(Math.cos(angle), -Math.sin(angle))));
            }
        }
        return c;
    }

    // Compute the inverse Fourier transform of the input amplitudes
    public double[] inverseTransform(Complex[] c) {
        double[] x = new double[N];
        for (int n = 0; n < N; n++) {
            x[n] = 0;
            for (int k = 0; k < N; k++) {
                double angle = 2 * Math.PI * k * n / N;
                x[n] += c[k].re() * Math.cos(angle) - c[k].im() * Math.sin(angle);
            }
            x[n] /= N;
        }
        return x;
    }
}
