package com.mycompany.hospitaladmissionsystem;

/**
 * Represents an inpatient who requires a hospital bed.
 */
public class Inpatient extends Patient {

    // Additional information required only for inpatients.
    private int wardNumber;
    private String bedNumber;

    /**
     * Constructor for creating an inpatient.
     */
    public Inpatient(String patientId, String firstName, String lastName,
                     int age, String gender, String medicalCondition,
                     PatientCategory category, int wardNumber,
                     String bedNumber) {

        super(patientId, firstName, lastName, age, gender,
              medicalCondition, category);

        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    // Getter for the ward number.
    public int getWardNumber() {
        return wardNumber;
    }

    // Setter for the ward number.
    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    // Getter for the allocated bed number.
    public String getBedNumber() {
        return bedNumber;
    }

    // Setter for the allocated bed number.
    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    /**
     * Overrides the Patient displayDetails() method.
     * It first displays the basic patient details,
     * then displays the inpatient-specific details.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();

        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: "
                + (bedNumber == null ? "Not Allocated" : bedNumber));
    }
}
