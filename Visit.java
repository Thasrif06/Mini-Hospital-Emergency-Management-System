package hospital.model;

/**
 * Represents a single past hospital visit belonging to a patient.
 * Stored as the payload inside a node of that patient's VisitHistory
 * (Singly Linked List).
 */
public class Visit {

    private final int visitId;
    private final String visitDate;
    private final String doctorName;
    private final String diagnosis;
    private final String treatment;

    public Visit(int visitId, String visitDate, String doctorName, String diagnosis, String treatment) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public int getVisitId() {
        return visitId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    @Override
    public String toString() {
        return String.format("Visit ID: %-5d | Date: %-12s | Doctor: %-20s | Diagnosis: %-20s | Treatment: %s",
                visitId, visitDate, doctorName, diagnosis, treatment);
    }
}
