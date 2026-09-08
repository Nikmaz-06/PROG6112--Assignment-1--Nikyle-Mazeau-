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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the HospitalSystem class
 */
public class HospitalSystemTest {

    private HospitalSystem hospital;

    @BeforeEach
    public void setUp() {
        hospital = new HospitalSystem();
    }

    @Test
    public void testRegisterPatient() {

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        assertTrue(hospital.registerPatient(patient));
        assertEquals(1, hospital.getTotalPatients());
    }

    @Test
    public void testSearchPatient() {

        Patient patient = new Patient(
                "P002",
                "Sarah",
                "Jones",
                28,
                "Female",
                "Migraine",
                PatientCategory.EMERGENCY
        );

        hospital.registerPatient(patient);

        Patient result = hospital.searchPatient("P002");

        assertNotNull(result);
        assertEquals("Sarah", result.getFirstName());
    }

    @Test
    public void testUpdatePatient() {

        Patient patient = new Patient(
                "P003",
                "Mike",
                "Brown",
                40,
                "Male",
                "Back Pain",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean updated = hospital.updatePatient(
                "P003",
                "Michael",
                "Brown",
                41,
                "Male",
                "Back Injury",
                PatientCategory.OUTPATIENT
        );

        assertTrue(updated);

        Patient result = hospital.searchPatient("P003");

        assertEquals("Michael", result.getFirstName());
        assertEquals(41, result.getAge());
        assertEquals("Back Injury", result.getMedicalCondition());
    }

    @Test
    public void testDeletePatient() {

        Patient patient = new Patient(
                "P004",
                "Emily",
                "Davis",
                25,
                "Female",
                "Cold",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.deletePatient("P004"));
        assertNull(hospital.searchPatient("P004"));
    }

    @Test
    public void testPreventDuplicatePatientId() {

        Patient patient1 = new Patient(
                "P005",
                "James",
                "Wilson",
                35,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P005",
                "Daniel",
                "Taylor",
                45,
                "Male",
                "Headache",
                PatientCategory.EMERGENCY
        );

        assertTrue(hospital.registerPatient(patient1));
        assertFalse(hospital.registerPatient(patient2));

        assertEquals(1, hospital.getTotalPatients());
    }

    @Test
    public void testAllocateBed() {

        Inpatient patient = new Inpatient(
                "P006",
                "Anna",
                "White",
                50,
                "Female",
                "Pneumonia",
                PatientCategory.INPATIENT,
                1,
                null
        );

        hospital.registerPatient(patient);

        assertTrue(hospital.allocateBed("P006", "B01"));
        assertEquals("B01", patient.getBedNumber());
    }

    @Test
    public void testReleaseBed() {

        Inpatient patient = new Inpatient(
                "P007",
                "David",
                "Green",
                60,
                "Male",
                "Heart Condition",
                PatientCategory.INPATIENT,
                1,
                null
        );

        hospital.registerPatient(patient);

        hospital.allocateBed("P007", "B02");

        assertTrue(hospital.releaseBed("P007"));
        assertNull(patient.getBedNumber());
    }

    @Test
    public void testPreventOccupiedBedAllocation() {

        Inpatient patient1 = new Inpatient(
                "P008",
                "Chris",
                "King",
                55,
                "Male",
                "Surgery",
                PatientCategory.INPATIENT,
                1,
                null
        );

        Inpatient patient2 = new Inpatient(
                "P009",
                "Lisa",
                "Hall",
                44,
                "Female",
                "Observation",
                PatientCategory.INPATIENT,
                1,
                null
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(hospital.allocateBed("P008", "B03"));
        assertFalse(hospital.allocateBed("P009", "B03"));
    }

    @Test
    public void testPreventAllocationWhenWardIsFull() {

        for (int i = 1; i <= 20; i++) {

            String patientId = "FULL" + i;

            Inpatient patient = new Inpatient(
                    patientId,
                    "Patient",
                    Integer.toString(i),
                    30,
                    "Unknown",
                    "Condition",
                    PatientCategory.INPATIENT,
                    1,
                    null
            );

            hospital.registerPatient(patient);

            String bedNumber = String.format("B%02d", i);

            assertTrue(
                    hospital.allocateBed(patientId, bedNumber)
            );
        }

        Inpatient extraPatient = new Inpatient(
                "FULL21",
                "Extra",
                "Patient",
                35,
                "Unknown",
                "Condition",
                PatientCategory.INPATIENT,
                1,
                null
        );

        hospital.registerPatient(extraPatient);

        assertFalse(hospital.bedsAvailable());
        assertFalse(hospital.allocateBed("FULL21", "B01"));
    }

    @Test
    public void testSortPatientsBySurname() {

        hospital.registerPatient(new Patient(
                "P012",
                "John",
                "Zulu",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        ));

        hospital.registerPatient(new Patient(
                "P010",
                "Sarah",
                "Adams",
                28,
                "Female",
                "Cold",
                PatientCategory.OUTPATIENT
        ));

        hospital.registerPatient(new Patient(
                "P011",
                "Mike",
                "Brown",
                32,
                "Male",
                "Headache",
                PatientCategory.OUTPATIENT
        ));

        hospital.sortPatientsBySurname();

        assertEquals(
                "Adams",
                hospital.getPatients().get(0).getLastName()
        );

        assertEquals(
                "Brown",
                hospital.getPatients().get(1).getLastName()
        );

        assertEquals(
                "Zulu",
                hospital.getPatients().get(2).getLastName()
        );
    }

    @Test
    public void testSortPatientsById() {

        hospital.registerPatient(new Patient(
                "P003",
                "John",
                "Smith",
                30,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        ));

        hospital.registerPatient(new Patient(
                "P001",
                "Sarah",
                "Jones",
                28,
                "Female",
                "Cold",
                PatientCategory.OUTPATIENT
        ));

        hospital.registerPatient(new Patient(
                "P002",
                "Mike",
                "Brown",
                35,
                "Male",
                "Headache",
                PatientCategory.OUTPATIENT
        ));

        hospital.sortPatientsById();

        assertEquals(
                "P001",
                hospital.getPatients().get(0).getPatientId()
        );

        assertEquals(
                "P002",
                hospital.getPatients().get(1).getPatientId()
        );

        assertEquals(
                "P003",
                hospital.getPatients().get(2).getPatientId()
        );
    }
}