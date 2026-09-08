package com.mycompany.hospitaladmissionsystem;

import java.util.ArrayList;

/**
 * Handles patient management for the hospital system.
 */
public class HospitalSystem {

    // Stores all registered patients while the program is running.
    private ArrayList<Patient> patients;
    
    // Stores the beds in the 4x5 layout 
private String[][] beds;

// Stores the Patient ID occupying each bed
// A null value means that the bed is available and ready to be used
private String[][] bedOccupants;

  public HospitalSystem() {

    patients = new ArrayList<>();

    // Create the 4 x 5 hospital ward.
    beds = new String[4][5];
    bedOccupants = new String[4][5];

    initialiseBeds();
}
    /**
     * Registers a new patient.
     * Returns false if the Patient ID already exists.
     */
    public boolean registerPatient(Patient patient) {

        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    /**
     * Searches for a patient using the Patient ID.
     */
    public Patient searchPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }

        return null;
    }

    /**
     * Updates an existing patient's details.
     */
    /**
 * Updates an existing patient's information.
 * The patient object is replaced when changing
 * between inpatient and non-inpatient categories.
 */
public boolean updatePatient(String patientId,
                             String firstName,
                             String lastName,
                             int age,
                             String gender,
                             String medicalCondition,
                             PatientCategory category) {

    Patient patient = searchPatient(patientId);

    if (patient == null) {
        return false;
    }

    int index = patients.indexOf(patient);

    /*
     * If the new category is INPATIENT but the existing
     * object is not an Inpatient, replace it with one.
     */
    if (category == PatientCategory.INPATIENT
            && !(patient instanceof Inpatient)) {

        Inpatient inpatient = new Inpatient(
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

        patients.set(index, inpatient);

        return true;
    }

    /*
     * If a patient changes from inpatient to out the  object is then replaced*/
    if (patient instanceof Inpatient
            && category != PatientCategory.INPATIENT) {

        Inpatient inpatient = (Inpatient) patient;

        if (inpatient.getBedNumber() != null) {
            releaseBed(patientId);
        }

        Patient updatedPatient = new Patient(
                patientId,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                category
        );

        patients.set(index, updatedPatient);

        return true;
    }

    // No object type change is required.
    patient.setFirstName(firstName);
    patient.setLastName(lastName);
    patient.setAge(age);
    patient.setGender(gender);
    patient.setMedicalCondition(medicalCondition);
    patient.setCategory(category);

    return true;
}

    /**
     * Deletes a patient using the Patient ID.
     */
 /**
 * Deletes a patient using their Patient ID*/
public boolean deletePatient(String patientId) {

    Patient patient = searchPatient(patientId);

    if (patient == null) {
        return false;
    }

    // Release the bed before deleting an inpatient.
    if (patient instanceof Inpatient) {

        Inpatient inpatient = (Inpatient) patient;

        if (inpatient.getBedNumber() != null) {
            releaseBed(patientId);
        }
    }

    patients.remove(patient);

    return true;
}

    /**
     * Displays all registered patients.
     */
    public void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("No patients are currently registered.");
            return;
        }

        System.out.println("\n--- Registered Patients ---");

        for (Patient patient : patients) {

            patient.displayDetails();

            System.out.println("---------------------------");
        }
    }

    /**
     * Returns the total number of registered patients.
     */
    public int getTotalPatients() {
        return patients.size();
    }

    /**
     * Returns the ArrayList of patients
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }
    
    /**
 * Creates bed numbers B01 to B20 using 4x5 layout
 */
private void initialiseBeds() {

    int bedNumber = 1;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            beds[row][column] =
                    String.format("B%02d", bedNumber);

            bedNumber++;
        }
    }
}

/**
 * Allocates a bed to an inpatient.
 * Returns false if criteria is not met
 */
public boolean allocateBed(String patientId, String bedNumber) {

    Patient patient = searchPatient(patientId);

    // Check that the patient exists and is an inpatient.
    if (!(patient instanceof Inpatient)) {
        return false;
    }

    Inpatient inpatient = (Inpatient) patient;

    // Prevent one inpatient from receiving multiple beds.
    if (inpatient.getBedNumber() != null) {
        return false;
    }

    // Search the 2D array for the requested bed.
    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column].equalsIgnoreCase(bedNumber)) {

                // Bed is already occupied.
                if (bedOccupants[row][column] != null) {
                    return false;
                }

                // Allocate the bed.
                bedOccupants[row][column] = patientId;
                inpatient.setBedNumber(beds[row][column]);

                return true;
            }
        }
    }

    // Bed number was not found.
    return false;
}

/**
 * Releases the bed that belongs the an inpatient.
 */
public boolean releaseBed(String patientId) {

    Patient patient = searchPatient(patientId);

    if (!(patient instanceof Inpatient)) {
        return false;
    }

    Inpatient inpatient = (Inpatient) patient;

    // Patient does not have a bed.
    if (inpatient.getBedNumber() == null) {
        return false;
    }

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (patientId.equalsIgnoreCase(
                    bedOccupants[row][column])) {

                bedOccupants[row][column] = null;
                inpatient.setBedNumber(null);

                return true;
            }
        }
    }

    return false;
}

/**
 * Displays the 4x5 hospital ward.
 */
public void displayWardLayout() {

    System.out.println("\n========== WARD LAYOUT ==========");

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (bedOccupants[row][column] == null) {

                System.out.printf(
                        "%-15s",
                        beds[row][column] + " [Available]");

            } else {

                System.out.printf(
                        "%-15s",
                        beds[row][column] + " [Occupied]");
            }
        }

        System.out.println();
    }
}

/**
 * Displays all currently available beds.
 */
public void displayAvailableBeds() {

    System.out.println("\n--- AVAILABLE BEDS ---");

    boolean found = false;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (bedOccupants[row][column] == null) {

                System.out.print(beds[row][column] + " ");
                found = true;
            }
        }
    }

    if (!found) {
        System.out.print("No beds available.");
    }

    System.out.println();
}

/**
 * Displays all occupied beds and their Patient IDs.
 */
public void displayOccupiedBeds() {

    System.out.println("\n--- OCCUPIED BEDS ---");

    boolean found = false;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (bedOccupants[row][column] != null) {

                System.out.println(
                        beds[row][column]
                        + " - Patient ID: "
                        + bedOccupants[row][column]);

                found = true;
            }
        }
    }

    if (!found) {
        System.out.println("No beds are currently occupied.");
    }
}

/**
 * Returns the number of occupied hospital beds.
 */
public int getOccupiedBedCount() {

    int count = 0;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (bedOccupants[row][column] != null) {
                count++;
            }
        }
    }

    return count;
}

/**
 * Checks whether any hospital beds are available.
 */
public boolean bedsAvailable() {

    return getOccupiedBedCount() < 20;
}

/**
 * Calculates the ward occupancy percentage.*/
public double getOccupancyPercentage() {

    return (getOccupiedBedCount() / 20.0) * 100;
}

/**
 * Displays summary report*/
public void displayWardReport() {

    System.out.println("\n========== WARD REPORT ==========");

    System.out.println("Total Registered Patients: "
            + getTotalPatients());

    System.out.println("Total Occupied Beds: "
            + getOccupiedBedCount());

    System.out.println("Total Available Beds: "
            + (20 - getOccupiedBedCount()));

    System.out.printf(
            "Ward Occupancy Percentage: %.2f%%%n",
            getOccupancyPercentage());

    System.out.println("=================================");
}

/**
 * Sorts patients alphabetically*/
public void sortPatientsBySurname() {

    for (int i = 0; i < patients.size() - 1; i++) {

        for (int j = 0; j < patients.size() - 1 - i; j++) {

            Patient first = patients.get(j);
            Patient second = patients.get(j + 1);

            if (first.getLastName().compareToIgnoreCase(
                    second.getLastName()) > 0) {

                patients.set(j, second);
                patients.set(j + 1, first);
            }
        }
    }
}

/**
 * Sorts patients by Patient ID.*/
public void sortPatientsById() {

    for (int i = 0; i < patients.size() - 1; i++) {

        for (int j = 0; j < patients.size() - 1 - i; j++) {

            Patient first = patients.get(j);
            Patient second = patients.get(j + 1);

            if (first.getPatientId().compareToIgnoreCase(
                    second.getPatientId()) > 0) {

                patients.set(j, second);
                patients.set(j + 1, first);
            }
        }
    }
}
}