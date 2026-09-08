/**
* --------------------------------------------
* CODE ATTRIBUTION
* --------------------------------------------
*Student Name: Nikyle Mazeau
*Student Number: ST10469340 
*Project: PROG6112 Assignment 1 - Hospital Admission system for medicare 
*Date: 28 August 2026
* Sources used: 
*1. Farrell, J. (2023). Java Programming. 10th Edition. Cengage Learning.
*- Used for OOP coding principles and structures, and
*the best way in which to implement good, clean code.
*2. Apache
*NetBeans (2026):  
* "Writing JUnit Tests in NetBeans IDE"
*URL : https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/
*3. Apache NetBeans (2026):
* "NetBeans Java language infrastructure"
*URL : https://netbeans.apache.org/tutorial/main/tutorials/nbm-copyfqn/
*4. Oracle. (2026): 
* "Java Platform SE Documentation"
* URL : https://docs.oracle.com/javase/8/docs/api/
*/

package com.mycompany.hospitaladmissionsystem;

import java.util.Scanner;

// Main patient class
public class HospitalAdmissionSystem {

    // Scanner used to receive input from the user.
    private static final Scanner scanner = new Scanner(System.in);

    // This object manages all patient records.
    private static final HospitalSystem hospital = new HospitalSystem();

   public static void main(String[] args) {
       //Stores the users selected menu option
    int choice = 0;
    //Diplay console heading when opening
    System.out.println("==========================================");
    System.out.println("   MEDICARE HOSPITAL ADMISSION SYSTEM");
    System.out.println("==========================================");
    //Continue running app until user selects option
    while (choice != 4) {

        displayMainMenu();
        //convert input into integer
        try {
            choice = Integer.parseInt(scanner.nextLine());
               //dorect user to choice menu
           switch (choice) {

    case 1:
        patientManagementMenu();
        break;

    case 2:
        bedManagementMenu();
        break;

    case 3:
        reportsMenu();
        break;

    case 4:
        System.out.println("\nExiting MediCare Hospital System...");
        System.out.println("Thank you for using MediCare services, have a good day"
                + "");
        break;

    default:
        System.out.println(
                "\nInvalid option. Please select 1 to 4.");
}
           //prevents crahsing of the program when selecting options
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
    System.out.println("3. Reports");
    System.out.println("4. Exit");
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
        System.out.println("1. Register New Patient");//register new patient
        System.out.println("2. Search Patient");//search a patient id
        System.out.println("3. Update Patient");//update a patients current detials
        System.out.println("4. Delete Patient");//delete a created patient and id
        System.out.println("5. Display All Patients");//display all created patients in a list
        System.out.println("6. Return to Main Menu");//return option to main menu
        System.out.print("Select an option: ");//select option prompt to remind user

        try {
            //convert input to a integer again
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
                            "Invalid option. Please select 1 to 6.");//error validation message
            }

        } catch (NumberFormatException e) {
            System.out.println(
                    "Invalid input. Please enter a number.");//error validation on inut
        }
    }
}

    /**
     * Captures patient information and registers a new patient.*/
    public static void registerPatient() {

        System.out.println("\n--- REGISTER NEW PATIENT ---");//register a new patient menu display

        System.out.print("Enter Patient ID: ");//prompt for input
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

       
        System.out.println("\nCurrent Category: "
        + patient.getCategory());

System.out.println("Select New Patient Category:");

PatientCategory category = selectCategory();

if (category == null) {
    System.out.println("Patient was not updated.");
    return;
}

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
        System.out.println("1. Allocate Bed");//allocate a bed to a patient
        System.out.println("2. Release Bed");//release the patient from the bed
        System.out.println("3. Display Ward Layout");//display the ward in a layout
        System.out.println("4. Display Available Beds");//show non used beds
        System.out.println("5. Display Occupied Beds");//show used beds
        System.out.println("6. Return to Main Menu");//return option
        System.out.print("Select an option: ");//prompt user for input

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
 * Releases the bed occupied by an inpatient method.
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

/**
 * Displays the reports menu method.*/
public static void reportsMenu() {

    int choice = 0;

    while (choice != 7) {

        System.out.println("\n============== REPORTS ==============");
        System.out.println("1. Display All Patients");
        System.out.println("2. Display Available Beds");
        System.out.println("3. Display Occupied Beds");
        System.out.println("4. Display Ward Summary");
        System.out.println("5. Sort Patients by Surname");
        System.out.println("6. Sort Patients by Patient ID");
        System.out.println("7. Return to Main Menu");
        System.out.print("Select an option: ");

        try {

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    hospital.displayAllPatients();
                    break;

                case 2:
                    hospital.displayAvailableBeds();
                    break;

                case 3:
                    hospital.displayOccupiedBeds();
                    break;

                case 4:
                    hospital.displayWardReport();
                    break;

                case 5:
                    hospital.sortPatientsBySurname();
                    System.out.println(
                            "\nPatients sorted by surname.");
                    hospital.displayAllPatients();
                    break;

                case 6:
                    hospital.sortPatientsById();
                    System.out.println(
                            "\nPatients sorted by Patient ID.");
                    hospital.displayAllPatients();
                    break;

                case 7:
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please select 1 to 7.");
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid input. Please enter a number.");
        }
    }
}

}