public class Appointment implements Comparable<Appointment> {
    public int patientId;
    public int doctorId;
    public int emergencyLevel;

    public Appointment(int patientId, int doctorId, int emergencyLevel) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.emergencyLevel = emergencyLevel;
    }

    @Override
    public int compareTo(Appointment other) {
        return Integer.compare(other.emergencyLevel, this.emergencyLevel); // Higher emergency level = higher priority
    }

    @Override
    public String toString() {
        return "Appointment - Patient ID: " + patientId + ", Doctor ID: " + doctorId + ", Emergency Level: " + emergencyLevel;
    }
}
