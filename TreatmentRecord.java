package hospital.model;

/**
 * Represents one completed treatment. An instance is pushed onto the
 * TreatmentHistoryStack the moment a patient's emergency treatment
 * finishes.
 */
public class TreatmentRecord {

    private final int patientId;
    private final String patientName;
    private final String medicalCondition;
    private final String completedOn;

    public TreatmentRecord(int patientId, String patientName, String medicalCondition, String completedOn) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.medicalCondition = medicalCondition;
        this.completedOn = completedOn;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    @Override
    public String toString() {
        return String.format("Patient ID: %-5d | Name: %-20s | Condition treated: %-20s | Completed: %s",
                patientId, patientName, medicalCondition, completedOn);
    }
}
