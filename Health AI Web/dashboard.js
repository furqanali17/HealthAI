import { initializeApp } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-app.js";
import { getFirestore, doc, collection, onSnapshot } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";
import { getAuth, onAuthStateChanged, signOut } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

const firebaseConfig = {
    apiKey: "AIzaSyCTMf-Pttj9U-KZ_kOFIBEWQgrWPkG4MJA",
    authDomain: "dfos-healthai.firebaseapp.com",
    databaseURL: "https://dfos-healthai-default-rtdb.firebaseio.com",
    projectId: "dfos-healthai",
    storageBucket: "dfos-healthai.appspot.com",
    messagingSenderId: "259705293143",
    appId: "1:259705293143:web:889a820ce52326e592d612"
};

const app = initializeApp(firebaseConfig);
const firestore = getFirestore(app);
const auth = getAuth();

// Set up a listener for authentication state changes
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