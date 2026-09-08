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
 *shows different categories of patients
 * that can be registered at the hospital, inherit
 */
public enum PatientCategory {
    INPATIENT,
    OUTPATIENT,
    EMERGENCY
}