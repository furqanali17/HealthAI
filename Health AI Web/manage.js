import { database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";

// Global variable to store the fetched users data
let globalUsersData = {};

function fetchAndDisplayForms() {
    const usersRef = ref(database, 'Users');
    onValue(usersRef, (snapshot) => {
        if (snapshot.exists()) {
            const users = snapshot.val();
            globalUsersData = users;
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
        if (userData.forms && Object.keys(userData.forms).length > 0) {
            const userDetailsDiv = document.createElement('div');
            userDetailsDiv.className = 'dataset-block';

            userDetailsDiv.innerHTML += `<h3>User: ${userData.name}</h3>`;

            const formsDiv = document.createElement('div');
            formsDiv.className = 'json-nested';
            formsDiv.innerHTML += '<h4>Forms:</h4>';
            Object.entries(userData.forms).forEach(([formId, formData]) => {
                const formDetailsDiv = document.createElement('div');
                formDetailsDiv.className = 'json-pair';
                formDetailsDiv.innerHTML += `<div class="json-key">Form ${formId}:</div><div class="json-value">${formatJSON(formData)}</div>`;
                formsDiv.appendChild(formDetailsDiv);
            });
            userDetailsDiv.appendChild(formsDiv);

            if (userData.predictions) {
                const predictionsDiv = document.createElement('div');
                predictionsDiv.className = 'json-nested';
                predictionsDiv.innerHTML = '<h4>Predictions:</h4>';
                const predictionsList = document.createElement('ul');
                Object.entries(userData.predictions).forEach(([key, value]) => {
                    const listItem = document.createElement('li');
                    listItem.textContent = `${key}: ${value}`;
                    predictionsList.appendChild(listItem);
                });
                predictionsDiv.appendChild(predictionsList);
                userDetailsDiv.appendChild(predictionsDiv);
            }

            datasetDisplay.appendChild(userDetailsDiv);
        }
    });
}

function formatJSON(jsonData) {
    return JSON.stringify(jsonData, null, 2)
        .replace(/,/g, '')
        .replace(/"/g, '')
        .replace(/{|}/g, '')
        .replace(/\n/g, '<br>');
}

document.addEventListener('DOMContentLoaded', (event) => {
    fetchAndDisplayForms();

    const exportButton = document.getElementById('export-dataset');
    exportButton.addEventListener('click', exportDataset);

    const createDatasetButton = document.getElementById('create-dataset');
    createDatasetButton.addEventListener('click', createDataset);
});

function exportDataset() {
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(globalUsersData, null, 2));

    const downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download", "exported_dataset.json");
    document.body.appendChild(downloadAnchorNode);
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
}

function createDataset() {
    alert('New Aggregate Dataset Created');
    fetch('http://localhost:3000/createDataset', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(globalUsersData),
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}
