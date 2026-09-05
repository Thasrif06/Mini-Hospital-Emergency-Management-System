package hospital.structures;

import hospital.model.Visit;

/**
 * Singly Linked List holding one patient's previous hospital visits, in
 * the order they were added (oldest first). Every {@code Patient} owns
 * exactly one of these.
 */
public class VisitHistory {

    private VisitNode head;
    private int size;

    public VisitHistory() {
        head = null;
        size = 0;
    }

    /** Appends a new visit to the end of the list. */
    public void addVisit(Visit visit) {
        VisitNode newNode = new VisitNode(visit);
        if (head == null) {
            head = newNode;
        } else {
            VisitNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /** Removes the visit with the given visit ID. Returns true if something was removed. */
    public boolean removeVisit(int visitId) {
        if (head == null) {
            return false;
        }
        if (head.visit.getVisitId() == visitId) {
            head = head.next;
            size--;
            return true;
        }
        VisitNode current = head;
        while (current.next != null && current.next.visit.getVisitId() != visitId) {
            current = current.next;
        }
        if (current.next == null) {
            return false; // reached the end without finding a match
        }
        current.next = current.next.next;
        size--;
        return true;
    }

    /** Searches the list for a visit by visit ID. Returns null if not found. */
    public Visit searchVisit(int visitId) {
        VisitNode current = head;
        while (current != null) {
            if (current.visit.getVisitId() == visitId) {
                return current.visit;
            }
            current = current.next;
        }
        return null;
    }

    /** Prints every visit in the list, oldest first. */
    public void display() {
        if (head == null) {
            System.out.println("   No visit history recorded.");
            return;
        }
        VisitNode current = head;
        while (current != null) {
            System.out.println("   " + current.visit);
            current = current.next;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
