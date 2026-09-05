package hospital.structures;

import hospital.model.TreatmentRecord;

/**
 * A single node in the TreatmentHistoryStack linked list. Package-private
 * - external code interacts with TreatmentHistoryStack, not nodes directly.
 */
class StackNode {
    TreatmentRecord record;
    StackNode next;

    StackNode(TreatmentRecord record) {
        this.record = record;
        this.next = null;
    }
}
