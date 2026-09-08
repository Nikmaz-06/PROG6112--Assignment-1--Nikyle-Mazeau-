package com.mycompany.hospitaladmissionsystem;

import java.util.ArrayList;

/**
 * Handles patient management for the hospital system.
 */
public class HospitalSystem {

    // Stores all registered patients while the program is running.
    private ArrayList<Patient> patients;

    // Constructor.
    public HospitalSystem() {
        patients = new ArrayList<>();
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
    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
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
     * Returns the ArrayList of patients.
     * This will be useful later for reports and sorting.
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }
}