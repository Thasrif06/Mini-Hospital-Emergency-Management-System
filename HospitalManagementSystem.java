package hospital.system;

import hospital.model.Patient;
import hospital.model.TreatmentRecord;
import hospital.model.Visit;
import hospital.structures.EmergencyQueue;
import hospital.structures.PatientBST;
import hospital.structures.TreatmentHistoryStack;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Console-driven controller that ties the four required data structures
 * together into one Mini Hospital Emergency Management System:
 *
 * <ul>
 *   <li>{@link PatientBST}           - master patient record store, keyed by Patient ID</li>
 *   <li>{@link EmergencyQueue}       - FIFO line of patients waiting for treatment</li>
 *   <li>{@link TreatmentHistoryStack}- LIFO log of completed treatments</li>
 *   <li>each Patient's VisitHistory  - per-patient Singly Linked List</li>
 * </ul>
 *
 * Flow: a patient is registered into the BST, then enqueued for treatment.
 * "Treating" the next patient dequeues them, pushes a TreatmentRecord onto
 * the stack, and (optionally) appends a Visit to that patient's own
 * VisitHistory linked list.
 */
public class HospitalManagementSystem {

    private final PatientBST patientRecords = new PatientBST();
    private final EmergencyQueue emergencyQueue = new EmergencyQueue();
    private final TreatmentHistoryStack treatmentHistory = new TreatmentHistoryStack();
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int nextVisitId = 1;

    public void run() {
        try (scanner) {
            System.out.println("=================================================");
            System.out.println(" MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM");
            System.out.println("=================================================");
            
            boolean running = true;
            while (running) {
                printMainMenu();
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 -> patientRecordsMenu();
                    case 2 -> emergencyQueueMenu();
                    case 3 -> treatmentHistoryMenu();
                    case 4 -> visitHistoryMenu();
                    case 5 -> loadSampleData();
                    case 0 -> {
                        running = false;
                        System.out.println("\nExiting system. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    // ---------------------------------------------------------------
    // Main menu
    // ---------------------------------------------------------------

    private void printMainMenu() {
        System.out.println("\n------------------- MAIN MENU -------------------");
        System.out.println("1. Patient Records          (Binary Search Tree)");
        System.out.println("2. Emergency Patient Queue   (Queue)");
        System.out.println("3. Treatment History         (Stack)");
        System.out.println("4. Patient Visit History     (Singly Linked List)");
        System.out.println("5. Load sample data (for quick testing)");
        System.out.println("0. Exit");
        System.out.println("--------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // 1. Patient Records (BST)
    // ---------------------------------------------------------------

    private void patientRecordsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----------- PATIENT RECORDS (BST) -----------");
            System.out.println("1. Insert new patient");
            System.out.println("2. Search patient by ID");
            System.out.println("3. Delete patient");
            System.out.println("4. Display all patients (in-order, by ID)");
            System.out.println("0. Back to main menu");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> insertPatient();
                case 2 -> searchPatient();
                case 3 -> deletePatient();
                case 4 -> {
                    System.out.println("\nAll registered patients (ascending Patient ID):");
                    patientRecords.inorderTraversal();
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void insertPatient() {
        System.out.println("\n-- Insert New Patient --");
        int id = readInt("Patient ID: ");
        if (patientRecords.search(id) != null) {
            System.out.println("A patient with ID " + id + " already exists.");
            return;
        }
        String name = readString("Name: ");
        int age = readInt("Age: ");
        String contact = readString("Contact number: ");
        String condition = readString("Medical condition: ");

        Patient patient = new Patient(id, name, age, contact, condition);
        patientRecords.insert(patient);
        System.out.println("Patient registered successfully:\n   " + patient);
    }

    private void searchPatient() {
        int id = readInt("\nEnter Patient ID to search: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ".");
        } else {
            System.out.println("Patient found:\n   " + patient);
        }
    }

    private void deletePatient() {
        int id = readInt("\nEnter Patient ID to delete: ");
        boolean removed = patientRecords.delete(id);
        System.out.println(removed
                ? "Patient ID " + id + " deleted successfully."
                : "No patient found with ID " + id + ".");
    }

    // ---------------------------------------------------------------
    // 2. Emergency Patient Queue
    // ---------------------------------------------------------------

    private void emergencyQueueMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------- EMERGENCY PATIENT QUEUE (QUEUE) -------");
            System.out.println("1. Add registered patient to queue (Enqueue)");
            System.out.println("2. Display patients currently waiting");
            System.out.println("0. Back to main menu");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> enqueuePatient();
                case 2 -> {
                    System.out.println("\nPatients currently waiting:");
                    emergencyQueue.display();
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void enqueuePatient() {
        int id = readInt("\nEnter Patient ID to add to the emergency queue: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ". Register the patient first (Patient Records menu).");
            return;
        }
        emergencyQueue.enqueue(patient);
        System.out.println("Patient " + patient.getName() + " (ID: " + id + ") added to the emergency queue.");
    }

    // ---------------------------------------------------------------
    // 3. Treatment History (Stack)
    // ---------------------------------------------------------------

    private void treatmentHistoryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-------- TREATMENT HISTORY (STACK) --------");
            System.out.println("1. Treat next patient in queue (Dequeue + Push)");
            System.out.println("2. Display treatment records (most recent first)");
            System.out.println("3. Remove most recent treatment record (Pop)");
            System.out.println("0. Back to main menu");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> treatNextPatient();
                case 2 -> {
                    System.out.println("\nTreatment history:");
                    treatmentHistory.display();
                }
                case 3 -> removeLastTreatmentRecord();
                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Dequeues the next waiting patient, records the completed treatment
     * on the stack, and offers to log the visit in the patient's own
     * VisitHistory linked list. This is where the Queue and the Stack
     * meet: a completed wait (dequeue) becomes a completed treatment (push).
     */
    private void treatNextPatient() {
        if (emergencyQueue.isEmpty()) {
            System.out.println("\nThe emergency queue is empty. No patients to treat.");
            return;
        }
        Patient patient = emergencyQueue.dequeue();
        String today = LocalDate.now().format(dateFormat);

        TreatmentRecord record = new TreatmentRecord(
                patient.getPatientId(), patient.getName(), patient.getMedicalCondition(), today);
        treatmentHistory.push(record);

        System.out.println("\nTreatment completed for: " + patient);
        System.out.println("Treatment record pushed onto the treatment history stack.");

        String addVisit = readString("Log this as a visit in the patient's history? (y/n): ");
        if (addVisit.equalsIgnoreCase("y")) {
            String doctor = readString("Doctor name: ");
            String diagnosis = readString("Diagnosis: ");
            String treatment = readString("Treatment given: ");
            Visit visit = new Visit(nextVisitId++, today, doctor, diagnosis, treatment);
            patient.getVisitHistory().addVisit(visit);
            System.out.println("Visit added to patient's visit history:\n   " + visit);
        }
    }

    private void removeLastTreatmentRecord() {
        TreatmentRecord removed = treatmentHistory.pop();
        if (removed == null) {
            System.out.println("\nTreatment history is empty. Nothing to remove.");
        } else {
            System.out.println("\nRemoved most recent treatment record:\n   " + removed);
        }
    }

    // ---------------------------------------------------------------
    // 4. Patient Visit History (Singly Linked List)
    // ---------------------------------------------------------------

    private void visitHistoryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- PATIENT VISIT HISTORY (LINKED LIST) -----");
            System.out.println("1. Add visit for a patient");
            System.out.println("2. Remove visit from a patient's history");
            System.out.println("3. Search for a visit");
            System.out.println("4. Display a patient's full visit history");
            System.out.println("0. Back to main menu");
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addVisitDirectly();
                case 2 -> removeVisit();
                case 3 -> searchVisit();
                case 4 -> displayVisitHistory();
                case 0 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private Patient findPatientOrPrompt() {
        int id = readInt("Enter Patient ID: ");
        Patient patient = patientRecords.search(id);
        if (patient == null) {
            System.out.println("No patient found with ID " + id + ".");
        }
        return patient;
    }

    private void addVisitDirectly() {
        System.out.println();
        Patient patient = findPatientOrPrompt();
        if (patient == null) return;

        String date = readString("Visit date (YYYY-MM-DD): ");
        String doctor = readString("Doctor name: ");
        String diagnosis = readString("Diagnosis: ");
        String treatment = readString("Treatment: ");

        Visit visit = new Visit(nextVisitId++, date, doctor, diagnosis, treatment);
        patient.getVisitHistory().addVisit(visit);
        System.out.println("Visit added:\n   " + visit);
    }

    private void removeVisit() {
        System.out.println();
        Patient patient = findPatientOrPrompt();
        if (patient == null) return;

        int visitId = readInt("Enter Visit ID to remove: ");
        boolean removed = patient.getVisitHistory().removeVisit(visitId);
        System.out.println(removed
                ? "Visit ID " + visitId + " removed from " + patient.getName() + "'s history."
                : "No visit found with ID " + visitId + ".");
    }

    private void searchVisit() {
        System.out.println();
        Patient patient = findPatientOrPrompt();
        if (patient == null) return;

        int visitId = readInt("Enter Visit ID to search: ");
        Visit visit = patient.getVisitHistory().searchVisit(visitId);
        System.out.println(visit == null
                ? "No visit found with ID " + visitId + "."
                : "Visit found:\n   " + visit);
    }

    private void displayVisitHistory() {
        System.out.println();
        Patient patient = findPatientOrPrompt();
        if (patient == null) return;

        System.out.println("Visit history for " + patient.getName() + " (ID: " + patient.getPatientId() + "):");
        patient.getVisitHistory().display();
    }

    // ---------------------------------------------------------------
    // Sample data (optional, for quick manual testing / demos)
    // ---------------------------------------------------------------

    private void loadSampleData() {
        String[][] samples = {
                {"101", "Nimal Perera", "34", "0771234567", "Chest pain"},
                {"105", "Kasun Silva", "28", "0719876543", "Fractured arm"},
                {"98", "Amaya Fernando", "45", "0765554433", "High fever"},
                {"110", "Ishara Bandara", "19", "0701112233", "Allergic reaction"}
        };
        for (String[] s : samples) {
            int id = Integer.parseInt(s[0]);
            if (patientRecords.search(id) == null) {
                Patient patient = new Patient(id, s[1], Integer.parseInt(s[2]), s[3], s[4]);
                patientRecords.insert(patient);
                emergencyQueue.enqueue(patient);
            }
        }
        System.out.println("\nSample patients registered and added to the emergency queue.");
        System.out.println("Try: Patient Records > Display all patients, or Emergency Queue > Display.");
    }

    // ---------------------------------------------------------------
    // Input helpers (read whole lines throughout to avoid Scanner's
    // classic nextInt()/nextLine() buffering bugs)
    // ---------------------------------------------------------------

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
