package exercises.linkedlist;

public class RecursiveInsertLast implements InsertLastStrategy {

    // For demo metrics (not required for a production impl)
    int observedDepth = 0; // package-private so the demo can read it if needed

    @Override
    public ListNode insertLast(ListNode head, int value) {
        // Base case 1: empty list → new node becomes the head
        if (head == null) {
            return new ListNode(value);
        }

        // Base case 2: if this node is the tail → attach new node
        if (head.next == null) {
            head.next = new ListNode(value);
            return head;
        }

        // Recursive case: move one step deeper
        head.next = insertLast(head.next, value);

        // Return the (unchanged) head reference as recursion unwinds
        return head;
    }

    // Simple memory helpers for the demo
    private static long usedBytes() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 50000;
        int trials = args.length > 1 ? Integer.parseInt(args[1]) : 3;

        // Build an initial list (iteratively to avoid skewing recursion demo)
        ListNode head = LinkedListFixtures.fromArray();
        for (int i = 0; i < n; i++) {
            ListNode node = new ListNode(i);
            if (head == null) head = node;
            else { ListNode p = head; while (p.next != null) p = p.next; p.next = node; }
        }

        RecursiveInsertLast strat = new RecursiveInsertLast();

        System.out.println("=== Recursive insertLast demo ===");
        System.out.println("List length = " + LinkedListFixtures.length(head));

        for (int t = 1; t <= trials; t++) {
            long beforeMem = usedBytes();
            long start = System.nanoTime();

            // Insert at tail (recursion depth ≈ list length)
            try {
                head = strat.insertLast(head, 42);
            } catch (StackOverflowError e) {
                System.err.println("Stack overflow occurred at trial " + t + " — list too deep for recursive insert.");
                break;
            }

            long end = System.nanoTime();
            long afterMem = usedBytes();

            // GC nudge (not guaranteed)
            System.gc();

            System.out.printf(
                    "Trial %d: time=%.3f ms, usedBefore=%.2f MB, usedAfter=%.2f MB, approxDepth=%d%n",
                    t, (end - start) / 1e6, beforeMem / (1024.0 * 1024.0), afterMem / (1024.0 * 1024.0),
                    strat.observedDepth
            );
        }
    }
}
