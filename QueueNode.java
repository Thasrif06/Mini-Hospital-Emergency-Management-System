package hospital.structures;

import hospital.model.Patient;

/**
 * A single node in the EmergencyQueue linked list. Package-private -
 * external code interacts with EmergencyQueue, not nodes directly.
 */
class QueueNode {
    Patient patient;
    QueueNode next;

    QueueNode(Patient patient) {
        this.patient = patient;
        this.next = null;
    }
}
