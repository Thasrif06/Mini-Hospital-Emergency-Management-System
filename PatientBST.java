package hospital.structures;

import hospital.model.Patient;

/**
 * Binary Search Tree storing Patient records, keyed by Patient ID.
 * Supports insert, search, delete, and an in-order traversal that
 * yields patients in ascending order of Patient ID.
 */
public class PatientBST {

    private PatientBSTNode root;
    private int count;

    public PatientBST() {
        root = null;
        count = 0;
    }

    /** Inserts a new patient. Returns false (and inserts nothing) if the ID already exists. */
    public boolean insert(Patient patient) {
        if (search(patient.getPatientId()) != null) {
            return false;
        }
        root = insertRec(root, patient);
        count++;
        return true;
    }

    private PatientBSTNode insertRec(PatientBSTNode node, Patient patient) {
        if (node == null) {
            return new PatientBSTNode(patient);
        }
        if (patient.getPatientId() < node.patient.getPatientId()) {
            node.left = insertRec(node.left, patient);
        } else {
            node.right = insertRec(node.right, patient);
        }
        return node;
    }

    /** Searches for a patient by ID. Returns null if not found. */
    public Patient search(int patientId) {
        PatientBSTNode result = searchRec(root, patientId);
        return result == null ? null : result.patient;
    }

    private PatientBSTNode searchRec(PatientBSTNode node, int patientId) {
        if (node == null || node.patient.getPatientId() == patientId) {
            return node;
        }
        if (patientId < node.patient.getPatientId()) {
            return searchRec(node.left, patientId);
        }
        return searchRec(node.right, patientId);
    }

    /** Deletes the patient with the given ID. Returns true if a patient was removed. */
    public boolean delete(int patientId) {
        if (search(patientId) == null) {
            return false;
        }
        root = deleteRec(root, patientId);
        count--;
        return true;
    }

    private PatientBSTNode deleteRec(PatientBSTNode node, int patientId) {
        if (node == null) {
            return null;
        }
        if (patientId < node.patient.getPatientId()) {
            node.left = deleteRec(node.left, patientId);
        } else if (patientId > node.patient.getPatientId()) {
            node.right = deleteRec(node.right, patientId);
        } else {
            // Node found.
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Two children: replace this node's data with its in-order
            // successor (smallest value in the right subtree), then
            // delete that successor node from the right subtree.
            PatientBSTNode successor = findMin(node.right);
            node.patient = successor.patient;
            node.right = deleteRec(node.right, successor.patient.getPatientId());
        }
        return node;
    }

    private PatientBSTNode findMin(PatientBSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /** Prints every patient in ascending order of Patient ID. */
    public void inorderTraversal() {
        if (root == null) {
            System.out.println("   No patient records available.");
            return;
        }
        inorderRec(root);
    }

    private void inorderRec(PatientBSTNode node) {
        if (node == null) {
            return;
        }
        inorderRec(node.left);
        System.out.println("   " + node.patient);
        inorderRec(node.right);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }
}
