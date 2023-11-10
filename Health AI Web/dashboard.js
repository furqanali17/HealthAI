import { firestore, auth } from './database_connection.js';
import { doc, collection, onSnapshot } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

onAuthStateChanged(auth, async (user) => {
    if (user) {
        // User is signed in
        // Fetch and display the professional's name
        const professionalRef = doc(firestore, 'professionals', user.uid);
        onSnapshot(professionalRef, (doc) => {
            const professionalData = doc.data();
            if (professionalData && professionalData.fullname) {
                document.getElementById('professional-name').innerText = `Welcome Dr. ${professionalData.fullname}`;
            } else {
                console.error('Professional data not found');
                document.getElementById('professional-name').innerText = 'Welcome Dr. Name'; // Fallback text
            }
        });

        // Listen for changes to the user's patient list
        const patientsRef = collection(firestore, 'patients');
        onSnapshot(patientsRef, (querySnapshot) => {
            const patientListElement = document.getElementById('patient-list');
            patientListElement.innerHTML = '';

            if (!querySnapshot.empty) {
                querySnapshot.forEach((doc) => {
                    const patient = doc.data();
                    const listItem = document.createElement('li');

                    const nameElement = document.createElement('div');
                    nameElement.innerText = `Name: ${patient.Name}`;

                    const ageElement = document.createElement('div');
                    ageElement.innerText = `Age: ${patient.Age}`;

                    const heightElement = document.createElement('div');
                    heightElement.innerText = `Height: ${patient.Height}`;

                    listItem.appendChild(nameElement);
                    listItem.appendChild(ageElement);
                    listItem.appendChild(heightElement);

                    patientListElement.appendChild(listItem);
                });
            } else {
                patientListElement.innerText = 'No patients found';
            }
        });
    } else {
        // User is signed out
        window.location.href = 'index.html'; // Redirect to login page
    }
});