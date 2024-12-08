import java.util.ArrayList;
import java.util.List;

public class Doctor {
    public int id;
    public String name;
    public String specialization;
    public String availableHours;
    public List<Integer> assignedPatients;

    public Doctor(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.availableHours = "Not Set";
        this.assignedPatients = new ArrayList<>();
    }

    public void assignPatient(int patientId) {
        assignedPatients.add(patientId);
    }

    public void removePatient(int patientId) {
        assignedPatients.removeIf(id -> id == patientId);
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id
                + ", Name: " + name
                + ", Specialization: " + specialization
                + ", Available Hours: " + availableHours
                + ", Assigned Patients: " + assignedPatients;
    }
}
