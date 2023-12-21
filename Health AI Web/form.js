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
                updateFormDisplay(formsData[formId], formId, patientId);
            });
        } else {
            console.error('Form data not found');
            document.getElementById('form-title-header').innerText = 'No Form Details Available';
        }
    });
}

async function updateFormDisplay(formData, formId, patientId) {
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

    try {
        const predictions = await loadPredictions(patientId, formId);

        const predictionsDiv = document.createElement('div');
        predictionsDiv.classList.add('predictions-container');
        predictionsDiv.innerHTML = '<h3>Predictions</h3>';

        Object.entries(predictions).forEach(([key, value]) => {
            const predictionDetail = document.createElement('p');
            predictionDetail.textContent = `${formatPredictionKey(key)}: ${value}`;
            predictionsDiv.appendChild(predictionDetail);
        });

        formContainer.appendChild(predictionsDiv);
    } catch (error) {
        console.error(error);
    }

    formDetailsSection.appendChild(formContainer);
}

function formatPredictionKey(key) {
    const formattedKey = key.replace(/Prediction$/, ''); // Remove "Prediction" at the end
    return formattedKey.replace(/([a-z])([A-Z])/g, '$1 $2').toUpperCase();
}

function loadPredictions(patientId, formId) {
    const predictionsRef = ref(database, `Users/${patientId}/predictions/`);
    return new Promise((resolve, reject) => {
        onValue(predictionsRef, (snapshot) => {
            if (snapshot.exists()) {
                resolve(snapshot.val());
            } else {
                reject('Predictions not found');
            }
        });
    });
}

function formatValue(value) {
    if (value === true || value === 1) {
        return 'Yes';
    } else if (value === false || value === 0) {
        return 'No';
    }
    return value;
}

function formatQuestionKey(key) {
    return key.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}

document.getElementById('chatbot-button').addEventListener('click', function () {
    window.location.href = 'support.html';
});