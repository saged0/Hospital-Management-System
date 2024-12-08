public class Patient {
    public int id;
    public String name;
    public String diagnosis;

    public Patient(int id, String name, String diagnosis) {
        this.id = id;
        this.name = name;
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + ", Diagnosis: " + diagnosis;
    }
}
