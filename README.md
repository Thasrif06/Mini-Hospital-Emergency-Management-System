# Mini Hospital Emergency Management System

A console-based Java application (CIT300 - Data Structures and Algorithms) that
simulates patient registration, emergency triage, treatment history, and per-patient
visit history using four data structures implemented from first principles (no
`java.util` collections used for the core structures themselves).

| Requirement              | Data Structure            | Where it lives                                        |
|---------------------------|----------------------------|--------------------------------------------------------|
| Patient Records            | Binary Search Tree (BST)   | `hospital.structures.PatientBST`                        |
| Emergency Patient Queue     | Queue (linked list based)  | `hospital.structures.EmergencyQueue`                     |
| Treatment History           | Stack (linked list based)  | `hospital.structures.TreatmentHistoryStack`              |
| Patient Visit History       | Singly Linked List         | `hospital.structures.VisitHistory` (one per patient)      |

## How the pieces connect

1. A patient is **registered** into the `PatientBST`, keyed by Patient ID.
2. A registered patient is **enqueued** into the `EmergencyQueue` to wait for treatment.
3. "Treat next patient" **dequeues** them from the queue, **pushes** a `TreatmentRecord`
   onto the `TreatmentHistoryStack`, and optionally appends a `Visit` to that patient's
   own `VisitHistory` linked list.
4. Patient records, the waiting queue, the treatment stack, and any patient's visit
   history can each be searched, displayed, added to, or removed from independently
   through the menu.

## Project structure

```
src/
  hospital/
    Main.java                          - entry point
    model/
      Patient.java                     - patient record (owns a VisitHistory)
      Visit.java                       - one hospital visit
      TreatmentRecord.java             - one completed treatment
    structures/
      PatientBSTNode.java / PatientBST.java
      QueueNode.java      / EmergencyQueue.java
      StackNode.java      / TreatmentHistoryStack.java
      VisitNode.java      / VisitHistory.java
    system/
      HospitalManagementSystem.java    - console menu + wiring
    test/
      DataStructureTests.java          - dependency-free sanity checks
```

## Compiling and running

From the project root (requires JDK 8+):

```bash
# Compile everything into out/
javac -d out $(find src -name "*.java")

# Run the interactive system
java -cp out hospital.Main

# Run the sanity checks
java -cp out hospital.test.DataStructureTests
```

Or, in IntelliJ / Eclipse: mark `src` as a source root and run `hospital.Main`.

## Using the program

The main menu maps directly onto the four required data structures:

```
1. Patient Records          (Binary Search Tree)
2. Emergency Patient Queue   (Queue)
3. Treatment History         (Stack)
4. Patient Visit History     (Singly Linked List)
5. Load sample data (for quick testing)
0. Exit
```

Option 5 registers a handful of sample patients and enqueues them - handy for
quickly exercising search / delete / dequeue / display without typing everything
by hand while testing or rehearsing a demo.

## Design notes

- BST deletion of a node with two children replaces it with its **in-order
  successor** (smallest value in the right subtree) - the standard textbook approach.
- The Queue and Stack are each backed by their own linked-list nodes (`QueueNode`,
  `StackNode`) rather than `java.util.LinkedList` or `java.util.Stack`, so enqueue/
  dequeue and push/pop are genuinely built from first principles.
- Each `Patient` owns exactly one `VisitHistory`, satisfying "each patient should
  have a Singly Linked List containing their previous hospital visits."
- Treating a patient (Queue → Stack) is the one place two structures interact
  directly, which mirrors the real workflow: a completed wait becomes a completed
  treatment.

## Suggested commit sequence

The brief asks for progressive commits, not one final upload. A natural order
that matches how this project was actually built:

1. Initial project structure + README
2. Patient model + PatientBST (insert, search)
3. BST delete + in-order traversal
4. EmergencyQueue (enqueue, dequeue, display)
5. TreatmentRecord + TreatmentHistoryStack (push, pop, display)
6. Visit model + VisitHistory linked list (add, remove, search, display)
7. HospitalManagementSystem console menu wiring everything together
8. DataStructureTests + fixes found while testing
9. README polish / screenshots

Don't just replay this list against pre-written code in one sitting - build (or
at least rebuild in your own words) each step for real, commit as you go, so the
history actually reflects your process.
