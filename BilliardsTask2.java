import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalCircularBilliardSimulation {
    public static void main(String[] args) {
        int numReflections = 10;  // Number of reflections to simulate
        double deviationThreshold = 0.01;  // Deviation threshold to check for

        // Simulate billiard and save reflection points
        List<double[]> reflectionPoints = simulateBilliard(numReflections);

        // Test reversibility
        boolean isReversible = testReversibility(reflectionPoints, numReflections, deviationThreshold);

        // Print the result
        if (isReversible) {
            System.out.println("The motion is reversible.");
        } else {
            System.out.println("The motion is not reversible after " + numReflections + " reflections.");
        }
    }

    public static List<double[]> simulateBilliard(int numReflections) {
        // Set up initial conditions
        Random random = new Random();
        double x = random.nextDouble() * 2 - 1;  // Initial x-coordinate (-1 to 1)
        double y = random.nextDouble() * 2 - 1;  // Initial y-coordinate (-1 to 1)
        double px = random.nextDouble() * 5 + 5;  // Initial x-component of momentum (5 to 10)
        double py = random.nextDouble() * 5 + 5;  // Initial y-component of momentum (5 to 10)

        // Normalize the momentum vector
        double magnitude = Math.sqrt(px * px + py * py);
        px /= magnitude;
        py /= magnitude;

        // List to store reflection points
        List<double[]> reflectionPoints = new ArrayList<>();

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

            // Save the reflection point
            reflectionPoints.add(new double[]{x, y});
        }

        return reflectionPoints;
    }

    public static boolean testReversibility(List<double[]> reflectionPoints, int numReflections, double deviationThreshold) {
        // Reverse the momentum after n reflections
        List<double[]> reversedPath = simulateReversedBilliard(reflectionPoints, numReflections);

        // Check for deviation between the forward and reversed paths
        for (int i = 0; i < numReflections; i++) {
            double deltaX = Math.abs(reflectionPoints.get(i)[0] - reversedPath.get(i)[0]);
            double deltaY = Math.abs(reflectionPoints.get(i)[1] - reversedPath.get(i)[1]);

            if (deltaX > deviationThreshold || deltaY > deviationThreshold) {
                return false;
            }
        }

        return true;
    }

    public static List<double[]> simulateReversedBilliard(List<double[]> reflectionPoints, int numReflections) {
        // Get the last reflection point as the initial position
        double x = reflectionPoints.get(numReflections - 1)[0];
        double y = reflectionPoints.get(numReflections - 1)[1];

        // Reverse the momentum
        double px = -reflectionPoints.get(numReflections - 1)[0];
        double py = -reflectionPoints.get(numReflections - 1)[1];

        // List to store reversed reflection points
        List<double[]> reversedPath = new ArrayList<>();

        // Simulate reversed reflections
        for (int i = numReflections - 1; i >= 0; i--) {
            // Calculate the previous position
            double prevX = x - px;
            double prevY = y - py;

            // Reflect the position
            x = prevX;
            y = prevY;

            // Calculate the new momentum
            double pX = (y * y - x * x) * px - 2 * x * y * py;
            double pY = -2 * x * y * px + (x * x - y * y) * py;

            px = pX;
            py = pY;

            // Add the reversed reflection point
            reversedPath.add(0, new double[]{x, y});
        }

        return reversedPath;
    }
}
