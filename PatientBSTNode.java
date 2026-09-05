package hospital.structures;

import hospital.model.Patient;

/**
 * A single node in the Patient BST. Package-private - external code
 * interacts with PatientBST, not nodes directly.
 */
class PatientBSTNode {
    Patient patient;
    PatientBSTNode left;
    PatientBSTNode right;

    PatientBSTNode(Patient patient) {
        this.patient = patient;
        this.left = null;
        this.right = null;
    }
}
