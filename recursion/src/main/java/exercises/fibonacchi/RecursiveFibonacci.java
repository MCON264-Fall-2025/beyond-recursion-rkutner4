package exercises.fibonacchi;

public class RecursiveFibonacci implements FibonacciStrategy {

    @Override
    public long compute(int n) {
        // Base cases
        if (n <= 0) return 0;
        if (n == 1) return 1;

        // Recursive case: F(n) = F(n-1) + F(n-2)
        return compute(n - 1) + compute(n - 2);
    }

    public static void main(String[] args) {
        RecursiveFibonacci fibonacci = new RecursiveFibonacci();
        int N = 50; // 50 will take extremely long!

        System.out.printf("%-10s| %-20s |%-20s|%n", "n", "Fibonacci(n)", "Time (ms)");

        long totalStart = System.currentTimeMillis();

        for (int i = 0; i <= N; i++) {
            long start = System.currentTimeMillis();
            long value = fibonacci.compute(i);
            long end = System.currentTimeMillis();

            long elapsed = end - start;
            System.out.printf("%-10d| %-20d| %-20d|%n", i, value, elapsed);

            // Small pause so students can observe progression clearly
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Stop if computation gets too long (> 10 seconds)
            if (elapsed > 10000) {
                System.out.println("\n⚠️ Computation is getting too expensive — stopping demo.");
                break;
            }
        }

        long totalEnd = System.currentTimeMillis();
        System.out.println("\nTotal elapsed time: " + (totalEnd - totalStart) + " ms");
    }
}
