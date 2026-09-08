package com.mycompany.hospitaladmissionsystem;

import java.util.Scanner;

// Main patient class
public class HospitalAdmissionSystem {

    // Scanner used to receive input from the user.
    private static final Scanner scanner = new Scanner(System.in);

    // This object manages all patient records.
    private static final HospitalSystem hospital = new HospitalSystem();

   public static void main(String[] args) {

    int choice = 0;

    System.out.println("==========================================");
    System.out.println("   MEDICARE HOSPITAL ADMISSION SYSTEM");
    System.out.println("==========================================");

    while (choice != 3) {

        displayMainMenu();

        try {
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    patientManagementMenu();
                    break;

                case 2:
                    bedManagementMenu();
                    break;

                case 3:
                    System.out.println("\nExiting MediCare Hospital System...");
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println(
                            "\nInvalid option. Please select 1 to 3.");
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "\nInvalid input. Please enter a number.");
        }
    }
}

  /**
 * Displays the main application menu.*/
public static void displayMainMenu() {

    System.out.println("\n=============== MAIN MENU ===============");
    System.out.println("1. Patient Management");
    System.out.println("2. Bed Management");
    System.out.println("3. Exit");
    System.out.print("Select an option: ");
}

/**
 * Displays patient management menu
 */
public static void patientManagementMenu() {

    int choice = 0;

    while (choice != 6) {

        System.out.println(
                "\n========== PATIENT MANAGEMENT ==========");
        System.out.println("1. Register New Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Return to Main Menu");
        System.out.print("Select an option: ");

        try {

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    searchPatient();
                    break;

                case 3:
                    updatePatient();
                    break;

                case 4:
                    deletePatient();
                    break;

                case 5:
                    hospital.displayAllPatients();
                    break;

                case 6:
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select 1 to 6.");
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid input. Please enter a number.");
        }
    }
}

    /**
     * Captures patient information and registers a new patient.*/
    public static void registerPatient() {

        System.out.println("\n--- REGISTER NEW PATIENT ---");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        // Prevent duplicate patient IDs.
        if (hospital.searchPatient(patientId) != null) {
            System.out.println(
                    "A patient with this ID already exists.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        int age;

        // Validatation for age.
        try {
            System.out.print("Enter Age: ");
            age = Integer.parseInt(scanner.nextLine());

            if (age <= 0) {
                System.out.println(
                        "Age must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid age. Please enter a number.");
            return;
        }

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Medical Condition: ");
        String medicalCondition = scanner.nextLine();

        PatientCategory category = selectCategory();

        if (category == null) {
            return;
        }

        Patient patient;

        // Inpatients use the Inpatient subclass.
        if (category == PatientCategory.INPATIENT) {

           
            patient = new Inpatient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    medicalCondition,
                    category,
                    1,
                    null
            );

        } else {

            // Outpatients and Emergency patients .
            patient = new Patient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    medicalCondition,
                    category
            );
        }

        if (hospital.registerPatient(patient)) {
            System.out.println("\nPatient registered successfully.");
        } else {
            System.out.println("\nPatient could not be registered.");
        }
    }

    /**
     * Searches for and displays a patient using their ID.
     */
    public static void searchPatient() {

        System.out.println("\n--- SEARCH PATIENT ---");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = hospital.searchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
        } else {
            System.out.println("\nPatient found:");
            patient.displayDetails();
        }
    }

    /**
     * Updates the details of an existing patient.
     */
    public static void updatePatient() {

        System.out.println("\n--- UPDATE PATIENT ---");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        Patient patient = hospital.searchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter New First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter New Last Name: ");
        String lastName = scanner.nextLine();

        int age;

        try {
            System.out.print("Enter New Age: ");
            age = Integer.parseInt(scanner.nextLine());

            if (age <= 0) {
                System.out.println(
                        "Age must be greater than zero.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid age. Patient was not updated.");
            return;
        }

        System.out.print("Enter New Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter New Medical Condition: ");
        String medicalCondition = scanner.nextLine();

       
        PatientCategory category = patient.getCategory();

        boolean updated = hospital.updatePatient(
                patientId,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                category
        );

        if (updated) {
            System.out.println("\nPatient updated successfully.");
        } else {
            System.out.println("\nPatient could not be updated.");
        }
    }

    /**
     * Deletes a patient using their Patient ID.
     */
    public static void deletePatient() {

        System.out.println("\n--- DELETE PATIENT ---");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        if (hospital.deletePatient(patientId)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    /**
     * Allows the user to select one of the patient categories
     */
    public static PatientCategory selectCategory() {

        System.out.println("\nSelect Patient Category:");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        System.out.print("Select an option: ");

        try {

            int categoryChoice =
                    Integer.parseInt(scanner.nextLine());

            switch (categoryChoice) {
                case 1:
                    return PatientCategory.INPATIENT;

                case 2:
                    return PatientCategory.OUTPATIENT;

                case 3:
                    return PatientCategory.EMERGENCY;

                default:
                    System.out.println(
                            "Invalid patient category.");
                    return null;
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid input. Please enter a number.");

            return null;
        }
    }
    
    
    /**
 * Displays the bed management menu.
 */
public static void bedManagementMenu() {

    int choice = 0;

    while (choice != 6) {

        System.out.println(
                "\n============ BED MANAGEMENT ============");
        System.out.println("1. Allocate Bed");
        System.out.println("2. Release Bed");
        System.out.println("3. Display Ward Layout");
        System.out.println("4. Display Available Beds");
        System.out.println("5. Display Occupied Beds");
        System.out.println("6. Return to Main Menu");
        System.out.print("Select an option: ");

        try {

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    allocateBed();
                    break;

                case 2:
                    releaseBed();
                    break;

                case 3:
                    hospital.displayWardLayout();
                    break;

                case 4:
                    hospital.displayAvailableBeds();
                    break;

                case 5:
                    hospital.displayOccupiedBeds();
                    break;

                case 6:
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select 1 to 6.");
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid input. Please enter a number.");
        }
    }
}

/**
 * Allocates an available bed to an inpatient.
 */
public static void allocateBed() {

    System.out.println("\n--- ALLOCATE BED ---");

    System.out.print("Enter Patient ID: ");
    String patientId = scanner.nextLine();

    Patient patient = hospital.searchPatient(patientId);

    if (patient == null) {
        System.out.println("Patient not found.");
        return;
    }

    // Only inpatients may receive hospital beds.
    if (!(patient instanceof Inpatient)) {
        System.out.println(
                "Only inpatients may be allocated a bed.");
        return;
    }

    Inpatient inpatient = (Inpatient) patient;

    // Prevent the same patient from receiving two beds.
    if (inpatient.getBedNumber() != null) {
        System.out.println(
                "This patient already has bed "
                + inpatient.getBedNumber() + ".");
        return;
    }

    // Prevent allocation if the ward is full.
    if (!hospital.bedsAvailable()) {
        System.out.println(
                "No hospital beds are currently available.");
        return;
    }

    hospital.displayAvailableBeds();

    System.out.print("\nEnter Bed Number: ");
    String bedNumber = scanner.nextLine();

    if (hospital.allocateBed(patientId, bedNumber)) {

        System.out.println(
                "Bed " + bedNumber.toUpperCase()
                + " allocated successfully.");

    } else {

        System.out.println(
                "Bed could not be allocated. "
                + "The bed may be occupied or invalid.");
    }
}

/**
 * Releases the bed occupied by an inpatient.
 */
public static void releaseBed() {

    System.out.println("\n--- RELEASE BED ---");

    System.out.print("Enter Patient ID: ");
    String patientId = scanner.nextLine();

    Patient patient = hospital.searchPatient(patientId);

    if (patient == null) {
        System.out.println("Patient not found.");
        return;
    }

    if (!(patient instanceof Inpatient)) {
        System.out.println(
                "This patient does not occupy a hospital bed.");
        return;
    }

    Inpatient inpatient = (Inpatient) patient;

    String bedNumber = inpatient.getBedNumber();

    if (bedNumber == null) {
        System.out.println(
                "This inpatient does not currently have a bed.");
        return;
    }

    if (hospital.releaseBed(patientId)) {

        System.out.println(
                "Bed " + bedNumber + " released successfully.");

    } else {

        System.out.println(
                "The bed could not be released.");
    }
}

}