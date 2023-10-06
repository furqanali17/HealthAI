// JavaScript code for handling the patient list (replace with actual data source)
document.addEventListener('DOMContentLoaded', function() {
    const patientList = document.getElementById('patient-list');

    // Simulated patient data (replace with actual data retrieval)
    const patients = [
        { id: 1, name: 'Patient 1', detailsPage: 'patient1.html' },
        { id: 2, name: 'Patient 2', detailsPage: 'patient2.html' },
        { id: 3, name: 'Patient 3', detailsPage: 'patient3.html' },
        // Add more patient data as needed
    ];

    // Populate the patient list
    patients.forEach(function(patient) {
        const listItem = document.createElement('li');
        const patientLink = document.createElement('a');
        patientLink.textContent = patient.name;
        patientLink.href = patient.detailsPage;
        listItem.appendChild(patientLink);
        patientList.appendChild(listItem);
    });
});
