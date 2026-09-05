package hospital.test;

import hospital.model.Patient;
import hospital.model.TreatmentRecord;
import hospital.model.Visit;
import hospital.structures.EmergencyQueue;
import hospital.structures.PatientBST;
import hospital.structures.TreatmentHistoryStack;
import hospital.structures.VisitHistory;

/**
 * Lightweight, dependency-free sanity checks for each data structure.
 * Not a substitute for a real test framework (JUnit, etc.), but enough
 * to demonstrate that the core operations behave as expected.
 *
 * Run with: java -cp out hospital.test.DataStructureTests
 */
public class DataStructureTests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testBST();
        testQueue();
        testStack();
        testVisitHistory();

        System.out.println("\n---------------------------------");
        System.out.println("Passed: " + passed + "   Failed: " + failed);
        System.out.println("---------------------------------");
    }

    private static void testBST() {
        System.out.println("== PatientBST ==");
        PatientBST bst = new PatientBST();
        bst.insert(new Patient(50, "Alice", 30, "0770000000", "Flu"));
        bst.insert(new Patient(20, "Bob", 40, "0770000001", "Sprain"));
        bst.insert(new Patient(80, "Cara", 25, "0770000002", "Migraine"));

        check("search existing", bst.search(20) != null);
        check("search missing", bst.search(999) == null);
        check("delete existing", bst.delete(20));
        check("search after delete", bst.search(20) == null);
        check("insert duplicate rejected", !bst.insert(new Patient(50, "Dup", 1, "x", "y")));
        check("size after ops", bst.size() == 2);
    }

    private static void testQueue() {
        System.out.println("== EmergencyQueue ==");
        EmergencyQueue queue = new EmergencyQueue();
        check("new queue is empty", queue.isEmpty());

        Patient p1 = new Patient(1, "P1", 20, "x", "cond");
        Patient p2 = new Patient(2, "P2", 21, "x", "cond");
        queue.enqueue(p1);
        queue.enqueue(p2);

        check("FIFO order (first out)", queue.dequeue() == p1);
        check("FIFO order (second out)", queue.dequeue() == p2);
        check("empty after dequeuing all", queue.isEmpty());
        check("dequeue on empty returns null", queue.dequeue() == null);
    }

    private static void testStack() {
        System.out.println("== TreatmentHistoryStack ==");
        TreatmentHistoryStack stack = new TreatmentHistoryStack();
        check("new stack is empty", stack.isEmpty());

        TreatmentRecord r1 = new TreatmentRecord(1, "P1", "Flu", "2026-01-01");
        TreatmentRecord r2 = new TreatmentRecord(2, "P2", "Sprain", "2026-01-02");
        stack.push(r1);
        stack.push(r2);

        check("LIFO order (last in, first out)", stack.pop() == r2);
        check("LIFO order (then the first one)", stack.pop() == r1);
        check("empty after popping all", stack.isEmpty());
        check("pop on empty returns null", stack.pop() == null);
    }

    private static void testVisitHistory() {
        System.out.println("== VisitHistory ==");
        VisitHistory history = new VisitHistory();
        check("new history is empty", history.isEmpty());

        Visit v1 = new Visit(1, "2026-01-01", "Dr. X", "Cold", "Rest");
        Visit v2 = new Visit(2, "2026-02-01", "Dr. Y", "Flu", "Medication");
        history.addVisit(v1);
        history.addVisit(v2);

        check("search finds visit", history.searchVisit(2) == v2);
        check("remove existing visit", history.removeVisit(1));
        check("removed visit no longer found", history.searchVisit(1) == null);
        check("size after ops", history.size() == 1);
    }

    private static void check(String label, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + label);
            passed++;
        } else {
            System.out.println("  [FAIL] " + label);
            failed++;
        }
    }
}
