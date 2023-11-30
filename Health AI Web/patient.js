import { database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const patientId = urlParams.get('patientId');
    
    if (patientId) {
        loadPatientData(patientId);
    } else {
        // Handle the case where no patient ID is provided
        console.error('No patient ID provided in URL');
    }
});

function loadPatientData(patientId) {
    const patientRef = ref(database, 'Users/' + patientId);
    onValue(patientRef, (snapshot) => {
        if (snapshot.exists()) {
            const patientData = snapshot.val();
            updatePatientDisplay(patientData);
        } else {
            console.error('Patient data not found');
            // Handle case where patient data is not found
        }
    });
}

function updatePatientDisplay(data) {
    // Updating patient details
    document.getElementById('patient-name-header').innerText = `Welcome, ${data.name}`;
    document.getElementById('patient-name').innerText = data.name;
    document.getElementById('patient-age').innerText = data.age;
    document.getElementById('patient-sex').innerText = data.sex;
    document.getElementById('patient-email').innerText = data.email;
    document.getElementById('patient-mobile').innerText = data.mobile;

    // Updating insurance details
    if (data.Insurance_Details) {
        document.getElementById('insurance-company').innerText = data.Insurance_Details.insuranceCompany;
        document.getElementById('policy-number').innerText = data.Insurance_Details.policyNumber;
        document.getElementById('group-number').innerText = data.Insurance_Details.groupNumber;
        document.getElementById('insurance-phone').innerText = data.Insurance_Details.insurancePhone;
        document.getElementById('insurance-year').innerText = data.Insurance_Details.insuranceYear;
        document.getElementById('subscriber-id').innerText = data.Insurance_Details.subscriberID;
        document.getElementById('type-of-insurance').innerText = data.Insurance_Details.typeOfInsurance;
        // Add more fields as per your data structure
    } else {
        console.error('Insurance details not found');
        // Handle case where insurance details are not available
    }
}