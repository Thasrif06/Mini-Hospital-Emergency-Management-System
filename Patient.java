package hospital.model;

import hospital.structures.VisitHistory;

/**
 * Represents a single patient record stored in the Patient BST.
 * Every patient owns exactly one {@link VisitHistory} - a Singly Linked
 * List tracking that patient's previous hospital visits.
 */
public class Patient {

    private final int patientId;
    private String name;
    private int age;
    private String contactNumber;
    private String medicalCondition;
    private final VisitHistory visitHistory;

    public Patient(int patientId, String name, int age, String contactNumber, String medicalCondition) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.medicalCondition = medicalCondition;
        this.visitHistory = new VisitHistory();
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    /** Each patient owns exactly one visit-history linked list. */
    public VisitHistory getVisitHistory() {
        return visitHistory;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s | Age: %-3d | Contact: %-15s | Condition: %s",
                patientId, name, age, contactNumber, medicalCondition);
    }
}
