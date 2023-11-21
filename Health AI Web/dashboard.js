import { auth, database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

onAuthStateChanged(auth, (user) => {
    if (user) {
        // User is signed in

        // Fetch and display the professional's name
        const professionalRef = ref(database, 'Professionals/' + user.uid);
        onValue(professionalRef, (snapshot) => {
            const professionalData = snapshot.val();
            if (professionalData && professionalData.fullname) {
                document.getElementById('professional-name').innerText = `Welcome Dr. ${professionalData.fullname}`;
            } else {
                console.error('Professional data not found');
                document.getElementById('professional-name').innerText = 'Welcome Dr. Name'; // Fallback text
            }
        });

        // Listen for changes to the user's patient list
        const patientsRef = ref(database, 'Users');
        onValue(patientsRef, (snapshot) => {
            const patientListElement = document.getElementById('patient-list');
            patientListElement.innerHTML = '';

            if (snapshot.exists()) {
                snapshot.forEach((childSnapshot) => {
                    const email = childSnapshot.key;
                    const patientDetails = childSnapshot.val();

                    const listItem = document.createElement('li');

                    const nameElement = document.createElement('div');
                    nameElement.innerText = `Name: ${patientDetails.name}`;

                    const ageElement = document.createElement('div');
                    ageElement.innerText = `Age: ${patientDetails.age}`;

                    const sexElement = document.createElement('div');
                    sexElement.innerText = `Sex: ${patientDetails.sex}`;

                    const mobileElement = document.createElement('div');
                    mobileElement.innerText = `Mobile: ${patientDetails.mobile}`;

                    listItem.appendChild(nameElement);
                    listItem.appendChild(ageElement);
                    listItem.appendChild(sexElement);
                    listItem.appendChild(mobileElement);

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