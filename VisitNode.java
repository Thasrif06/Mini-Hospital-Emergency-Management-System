package hospital.structures;

import hospital.model.Visit;

/**
 * A single node in a patient's VisitHistory singly linked list.
 * Package-private - external code interacts with VisitHistory, not nodes.
 */
class VisitNode {
    Visit visit;
    VisitNode next;

    VisitNode(Visit visit) {
        this.visit = visit;
        this.next = null;
    }
}
