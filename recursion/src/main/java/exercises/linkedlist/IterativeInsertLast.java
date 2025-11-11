package exercises.linkedlist;

public class IterativeInsertLast implements InsertLastStrategy {

    @Override
    public ListNode insertLast(ListNode head, int value) {
        // Create a new node to insert
        ListNode newNode = new ListNode(value);

        // Case 1: if the list is empty, the new node becomes the head
        if (head == null) {
            return newNode;
        }

        // Case 2: otherwise, walk to the end (tail) of the list
        ListNode current = head;
        while (current.next != null) {
            current = current.next; // move to next node
        }

        // Now 'current' is at the last node — attach the new node
        current.next = newNode;

        // Return the (original) head of the list
        return head;
    }

    private static long usedBytes() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public static void main(String[] args) {
        // Run with: -Xms64m -Xmx64m -Xlog:gc*
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 5000;
        int trials = args.length > 1 ? Integer.parseInt(args[1]) : 3;

        // Build initial list
        ListNode head = null;
        for (int i = 0; i < n; i++) {
            ListNode node = new ListNode(i);
            if (head == null) head = node;
            else { ListNode p = head; while (p.next != null) p = p.next; p.next = node; }
        }

        IterativeInsertLast strat = new IterativeInsertLast();

        System.out.println("=== Iterative insertLast demo ===");
        System.out.println("List length = " + LinkedListFixtures.length(head));
        System.out.println("VM flags tip: -Xms64m -Xmx64m -Xlog:gc*");

        for (int t = 1; t <= trials; t++) {
            long beforeMem = usedBytes();
            long start = System.nanoTime();

            head = strat.insertLast(head, 99);

            long end = System.nanoTime();
            long afterMem = usedBytes();

            System.gc();

            // Stack depth is effectively constant for iterative solution; report 1
            int approxDepth = 1;

            System.out.printf(
                    "Trial %d: time=%.3f ms, usedBefore=%.2f MB, usedAfter=%.2f MB, approxDepth=%d%n",
                    t, (end - start) / 1e6, beforeMem / (1024.0 * 1024.0), afterMem / (1024.0 * 1024.0),
                    approxDepth
            );
        }
    }
}

