import java.io.*;
import java.util.*;

public class HospitalManagementSystem {
    private HashMap<Integer, Patient> patientRecords = new HashMap<>();
    private HashMap<Integer, Doctor> doctors = new HashMap<>();
    private PriorityQueue<Appointment> appointmentQueue = new PriorityQueue<>();
    private Stack<String> systemLog = new Stack<>();

    private final String PATIENT_FILE = "src/patients.csv";
    private final String DOCTOR_FILE = "src/doctors.csv";

    public void run() {
        loadPatientData();
        loadDoctorData();

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. View all Patients");
            System.out.println("2. View all Doctors");
            System.out.println("3. View Appointments");
            System.out.println("4. Add Patient");
            System.out.println("5. Add Doctor");
            System.out.println("6. Schedule Regular Appointment");
            System.out.println("7. Add Emergency Patient");
            System.out.println("8. Search Patient");
            System.out.println("9. Search Doctor");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = new Scanner(System.in).nextInt();
                switch (choice) {
                    case 1 -> viewPatients();
                    case 2 -> viewDoctors();
                    case 3 -> viewAppointments();
                    case 4 -> addPatient();
                    case 5 -> addDoctor();
                    case 6 -> scheduleAppointment(false, 0);
                    case 7 -> addEmergencyPatient();
                    case 8 -> searchPatient();
                    case 9 -> searchDoctor();
                    case 10 -> {
                        System.out.println("Exiting system...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void loadPatientData() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String diagnosis = data[2].trim();
                Patient patient = new Patient(id, name, diagnosis);
                patientRecords.put(id, patient);
            }
            System.out.println("Loaded " + patientRecords.size() + " patients.");
        } catch (IOException e) {
            System.out.println("Error loading patient data: " + e.getMessage());
        }
    }

    private void loadDoctorData() {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String specialization = data[2].trim();
                Doctor doctor = new Doctor(id, name, specialization);
                doctors.put(id, doctor);
            }
            System.out.println("Loaded " + doctors.size() + " doctors.");
        } catch (IOException e) {
            System.out.println("Error loading doctor data: " + e.getMessage());
        }
    }

    private void viewPatients() {
        for (Patient patient : patientRecords.values()) {
            System.out.println(patient);
        }
    }

    private void viewDoctors() {
        for (Doctor doctor : doctors.values()) {
            System.out.println(doctor);
        }
    }

    private void viewAppointments() {
        if (appointmentQueue.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Appointments (Highest Priority First):");
            PriorityQueue<Appointment> tempQueue = new PriorityQueue<>(appointmentQueue);
            while (!tempQueue.isEmpty()) {
                System.out.println(tempQueue.poll());
            }
        }
    }

    private void addPatient() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Patient Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Diagnosis: ");
            String diagnosis = scanner.nextLine();
            Patient patient = new Patient(id, name, diagnosis);
            patientRecords.put(id, patient);
            System.out.println("Patient added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct values.");
        }
    }

    private void addDoctor() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Doctor ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Doctor Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Specialization: ");
            String specialization = scanner.nextLine();
            Doctor doctor = new Doctor(id, name, specialization);
            doctors.put(id, doctor);
            System.out.println("Doctor added successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct values.");
        }
    }

    private void addEmergencyPatient() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Patient Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Diagnosis: ");
            String diagnosis = scanner.nextLine();
            System.out.print("Enter Emergency Level (1-5, where 5 is most severe): ");
            int emergencyLevel = scanner.nextInt();

            Patient patient = new Patient(id, name, diagnosis);
            patientRecords.put(id, patient);
            scheduleAppointment(true, emergencyLevel);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct values.");
        }
    }

    private void scheduleAppointment(boolean isEmergency, int emergencyLevel) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doctorId = scanner.nextInt();

            Patient patient = patientRecords.get(patientId);
            Doctor doctor = doctors.get(doctorId);

            if (patient == null || doctor == null) {
                System.out.println("Invalid patient or doctor ID.");
                return;
            }

            int level = isEmergency ? emergencyLevel : 0;
            Appointment appointment = new Appointment(patientId, doctorId, level);
            appointmentQueue.add(appointment);
            System.out.println("Appointment scheduled successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter correct values.");
        }
    }

    private void searchPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        int id = scanner.nextInt();
        Patient patient = patientRecords.get(id);
        if (patient != null) {
            System.out.println("Patient found: " + patient);
        } else {
            System.out.println("No patient found with ID: " + id);
        }
    }

    private void searchDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Doctor ID: ");
        int id = scanner.nextInt();
        Doctor doctor = doctors.get(id);
        if (doctor != null) {
            System.out.println("Doctor found: " + doctor);
        } else {
            System.out.println("No doctor found with ID: " + id);
        }
    }

    public static void main(String[] args) {
        new HospitalManagementSystem().run();
    }
}
