package hospital.structures;

import hospital.model.Patient;

/**
 * FIFO Queue of patients waiting for emergency treatment, implemented as
 * a singly linked list with separate front and rear pointers so that
 * both enqueue and dequeue run in O(1).
 */
public class EmergencyQueue {

    private QueueNode front;
    private QueueNode rear;
    private int size;

    public EmergencyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /** Adds a patient to the back (rear) of the queue. */
    public void enqueue(Patient patient) {
        QueueNode newNode = new QueueNode(patient);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /** Removes and returns the patient at the front of the queue, or null if empty. */
    public Patient dequeue() {
        if (isEmpty()) {
            return null;
        }
        Patient patient = front.patient;
        front = front.next;
        if (front == null) {
            rear = null; // queue is now empty
        }
        size--;
        return patient;
    }

    /** Returns (without removing) the patient at the front of the queue. */
    public Patient peek() {
        return isEmpty() ? null : front.patient;
    }

    /** Prints every patient currently waiting, from front to rear. */
    public void display() {
        if (isEmpty()) {
            System.out.println("   Emergency queue is empty.");
            return;
        }
        QueueNode current = front;
        int position = 1;
        while (current != null) {
            System.out.println("   " + position + ". " + current.patient);
            current = current.next;
            position++;
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}
