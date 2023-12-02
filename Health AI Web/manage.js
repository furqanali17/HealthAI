import { database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";


function fetchAndDisplayForms() {
    const usersRef = ref(database, 'Users');
    onValue(usersRef, (snapshot) => {
        if (snapshot.exists()) {
            const users = snapshot.val();
            displayForms(users);
        } else {
            console.error('No users found');
        }
    });
}

function displayForms(users) {
    const datasetDisplay = document.getElementById('dataset-display');
    datasetDisplay.innerHTML = '';

    Object.entries(users).forEach(([userId, userData]) => {
        if (userData.forms) {
            const userForms = userData.forms;
            Object.entries(userForms).forEach(([formId, formData]) => {
                const formDetails = document.createElement('div');
                formDetails.innerHTML = `<h3>${userData.name} - Form ${formId}</h3><pre>${JSON.stringify(formData, null, 2)}</pre>`;
                datasetDisplay.appendChild(formDetails);
            });
        }
    });
}

document.addEventListener('DOMContentLoaded', (event) => {
    fetchAndDisplayForms();

    const exportButton = document.getElementById('export-dataset');
    exportButton.addEventListener('click', exportDataset);
});

function exportDataset() {
    const datasetDisplay = document.getElementById('dataset-display');

    let exportedData = [];
    document.querySelectorAll('#dataset-display div').forEach(div => {
        // Assuming each div contains JSON string in <pre> tag
        const formJson = div.querySelector('pre').textContent;
        try {
            const formData = JSON.parse(formJson);
            exportedData.push(formData);
        } catch (error) {
            console.error('Error parsing form data:', error);
        }
    });

    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(exportedData, null, 2));
    const downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download", "exported_dataset.json");
    document.body.appendChild(downloadAnchorNode);
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
}

document.getElementById('create-dataset').addEventListener('click', function () {
    window.location.href = 'new_form.html';
});