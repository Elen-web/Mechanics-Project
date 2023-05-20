import java.util.Random;

public class BilliardSimulation {
    public static void main(String[] args) {
        simulateBilliard();
    }

    public static void simulateBilliard() {
        // Set up initial conditions
        Random random = new Random();
        double x = random.nextDouble() * 2 - 1;  // Initial x-coordinate (-1 to 1)
        double y = random.nextDouble() * 2 - 1;  // Initial y-coordinate (-1 to 1)
        double px = random.nextDouble() * 2 - 1;  // Initial x-component of momentum (-1 to 1)
        double py = random.nextDouble() * 2 - 1;  // Initial y-component of momentum (-1 to 1)

        // Normalize the momentum vector
        double magnitude = Math.sqrt(px * px + py * py);
        px /= magnitude;
        py /= magnitude;

        // Simulation parameters
        int numReflections = 10;  // Number of reflections to simulate

        // Simulate reflections
        for (int i = 0; i < numReflections; i++) {
            // Calculate the next position
            double nextX = x + px;
            double nextY = y + py;

            // Check for collision with the circle boundary
            if (nextX * nextX + nextY * nextY >= 1) {
                // Reflect the position
                nextX = x;
                nextY = y;

                // Calculate the new momentum
                double pX = (nextY * nextY - nextX * nextX) * px - 2 * nextX * nextY * py;
                double pY = -2 * nextX * nextY * px + (nextX * nextX - nextY * nextY) * py;

                px = pX;
                py = pY;
            }

            x = nextX;
            y = nextY;

            // Print the current position
            System.out.println("Position: (" + x + ", " + y + ")");
        }
    }
}
