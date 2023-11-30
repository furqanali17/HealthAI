import { database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const patientId = urlParams.get('patientId');

    if (patientId) {
        loadFormData(patientId);
    } else {
        console.error('No patient ID provided in URL');
        document.getElementById('form-title-header').innerText = 'No Form Details Available';
    }
});

function loadFormData(patientId) {
    const formsRef = ref(database, `Users/${patientId}/forms`);
    onValue(formsRef, (snapshot) => {
        if (snapshot.exists()) {
            const formsData = snapshot.val();
            Object.keys(formsData).forEach(formId => {
                updateFormDisplay(formsData[formId], formId);
            });
        } else {
            console.error('Form data not found');
            document.getElementById('form-title-header').innerText = 'No Form Details Available';
        }
    });
}
function updateFormDisplay(formData, formId) {
    const formDetailsSection = document.querySelector('.form-details');
    const formContainer = document.createElement('div');
    formContainer.classList.add('form-container');
    formContainer.innerHTML = `<h3>Form ID: ${formId}</h3>`;

    Object.entries(formData).forEach(([key, value]) => {
        const detailDiv = document.createElement('div');
        detailDiv.classList.add('form-detail-entry');

        const detailHeader = document.createElement('h4');
        detailHeader.textContent = formatQuestionKey(key);
        const detailParagraph = document.createElement('p');
        detailParagraph.textContent = formatValue(value);

        detailDiv.appendChild(detailHeader);
        detailDiv.appendChild(detailParagraph);
        formContainer.appendChild(detailDiv);
    });

    formDetailsSection.appendChild(formContainer);
}

function formatValue(value) {
    if (value === true || value === 1) {
        return 'Yes';
    } else if (value === false || value === 0) {
        return 'No';
    }
    return value; // Return the value as is if it's not true/false or 1/0
}

function formatQuestionKey(key) {
    return key.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}