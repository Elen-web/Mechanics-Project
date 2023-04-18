# Mechanics-Project
The project aims at design and simulation of a mechanical converter that converts binary representation of a number into its value in the decimal numeral system. 
BOOKS - "Introduction to Mechanics" by D. Kleppner and R. Kolenkow, specifically Chapter 3 on Hook's Law and Simple Harmonic Motion. This will provide a good foundation for understanding the physics behind the spring oscillator.
"An Introduction to Computer Simulation Methods: Applications to Physical System" by H. Gould, J. Tobochnik, and W. Christian, specifically Chapter 9 on Normal Modes and Waves. potential task 1(not finished)





    

    public class Task1 {
    private double k = 1.0; 
    
    public Task1() {}
    public Task1(double k) {
        this.k = k;
    }
    

    public double getStiffness() {
        return k;
    }
    private void setStiffness(double k) {
        this.k = k;
    }
    
  
    public double[] move(double t, double dt, double x0, double v0) {
        double[] result = new double[(int) (t/dt) + 1];
        double x = x0;
        double v = v0;
        for (int i = 0; i < result.length; i++) {
            result[i] = x;
            double a = -k * x;
            v += a * dt;
            x += v * dt;
        }
        return result;
    }
    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0);
    }
    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        double[] result = new double[(int) ((t1-t0)/dt) + 1];
        double x = x0;
        double v = v0;
        for (int i = 0; i < result.length; i++) {
            result[i] = x;
            double a = -k * x;
            v += a * dt;
            x += v * dt;
        }
        return result;
    }
  
