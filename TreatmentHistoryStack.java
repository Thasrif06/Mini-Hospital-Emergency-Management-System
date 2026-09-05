package hospital.structures;

import hospital.model.TreatmentRecord;

/**
 * LIFO Stack of completed treatment records, implemented as a singly
 * linked list where push and pop both operate on the head ("top") of
 * the list, giving O(1) push/pop.
 */
public class TreatmentHistoryStack {

    private StackNode top;
    private int size;

    public TreatmentHistoryStack() {
        top = null;
        size = 0;
    }

    /** Pushes a newly completed treatment record onto the stack. */
    public void push(TreatmentRecord record) {
        StackNode newNode = new StackNode(record);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /** Removes and returns the most recently completed treatment record, or null if empty. */
    public TreatmentRecord pop() {
        if (isEmpty()) {
            return null;
        }
        TreatmentRecord record = top.record;
        top = top.next;
        size--;
        return record;
    }

    /** Returns (without removing) the most recently completed treatment record. */
    public TreatmentRecord peek() {
        return isEmpty() ? null : top.record;
    }

    /** Prints every treatment record, most recently completed first. */
    public void display() {
        if (isEmpty()) {
            System.out.println("   No treatment records available.");
            return;
        }
        StackNode current = top;
        int count = 1;
        while (current != null) {
            System.out.println("   " + count + ". " + current.record);
            current = current.next;
            count++;
        }
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
