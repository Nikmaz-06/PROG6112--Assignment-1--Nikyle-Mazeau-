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
 * Stores the information for a patient registered
 * in the hospital admission system.
 */
public class Patient {

    // Patient information is set to private for encapsulation.
    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    // Constructor for a new patient
    public Patient(String patientId, String firstName, String lastName,
                   int age, String gender, String medicalCondition,
                   PatientCategory category) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // Getters and setters
    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public PatientCategory getCategory() {
        return category;
    }

    // Setters allow patient information to be updated.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }
    // setter for gender information
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public void setCategory(PatientCategory category) {
        this.category = category;
    }

    // Displays all basic patient information
    public void displayDetails() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Patient Category: " + category);
    }
}
