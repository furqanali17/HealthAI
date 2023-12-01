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
            document.getElementById('patient-name-header').innerText = 'No Patient Details';
            const detailsToClear = ['patient-name', 'patient-age', 'patient-sex', 'patient-email', 'patient-mobile', 'insurance-company', 'policy-number', 'group-number', 'insurance-phone', 'insurance-year', 'subscriber-id', 'type-of-insurance'];
            detailsToClear.forEach(id => {
                document.getElementById(id).innerText = 'No patient details recorded';
            });
        }
    });
}

function updatePatientDisplay(data) {
    // Updating patient details
    document.getElementById('patient-name-header').innerText = `Patient: ${data.name}`;
    updateContactDetail('patient-name', data.name);
    updateContactDetail('patient-age', data.age);
    updateContactDetail('patient-sex', data.sex);
    updateContactDetail('patient-email', data.email);
    updateContactDetail('patient-mobile', data.mobile);

    // Updating insurance details
    if (data.Insurance_Details) {
        document.getElementById('insurance-company').innerText = data.Insurance_Details.insuranceCompany;
        document.getElementById('policy-number').innerText = data.Insurance_Details.policyNumber;
        document.getElementById('group-number').innerText = data.Insurance_Details.groupNumber;
        document.getElementById('insurance-phone').innerText = data.Insurance_Details.insurancePhone;
        document.getElementById('insurance-year').innerText = data.Insurance_Details.insuranceYear;
        document.getElementById('subscriber-id').innerText = data.Insurance_Details.subscriberID;
        document.getElementById('type-of-insurance').innerText = data.Insurance_Details.typeOfInsurance;
    } else {
        console.error('Insurance details not found');
        const insuranceDetailElements = ['insurance-company', 'policy-number', 'group-number', 'insurance-phone', 'insurance-year', 'subscriber-id', 'type-of-insurance'];
        insuranceDetailElements.forEach(elementId => {
            document.getElementById(elementId).innerText = 'No insurance details recorded';
        });
    }
}

function updateContactDetail(id, value) {
    const detailElement = document.getElementById(id);
    if (detailElement) {
        detailElement.innerText = value || 'Not Provided';
    }
}

document.getElementById('chatbot-button').addEventListener('click', function () {
    window.location.href = 'support.html';
});

document.getElementById('view-form-button').addEventListener('click', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const patientId = urlParams.get('patientId');
    if (patientId) {
        window.location.href = `form.html?patientId=${patientId}`;
    } else {
        console.error('No patient ID available for form view');
    }
});