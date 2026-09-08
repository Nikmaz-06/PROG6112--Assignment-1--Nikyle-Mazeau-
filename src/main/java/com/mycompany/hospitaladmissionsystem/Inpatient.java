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
     * Overrides the Patient details display method 
     */
    @Override
    public void displayDetails() {
        super.displayDetails();

        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: "
                + (bedNumber == null ? "Not Allocated" : bedNumber));
    }
}
