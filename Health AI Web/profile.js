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

onAuthStateChanged(auth, (user) => {
    if (user) {
        const professionalRef = doc(firestore, 'professionals', user.uid);
        onSnapshot(professionalRef, (doc) => {
            if (doc.exists) {
                const professionalData = doc.data();
                document.getElementById('professional-name').innerText = `Welcome Dr. ${professionalData.fullname}`;
                document.getElementById('doctor-name').innerText = professionalData.fullname;
                document.getElementById('doctor-contact').innerHTML = `Email: ${professionalData.email}`;
            } else {
                console.error('Professional data not found');
                document.getElementById('professional-name').innerText = 'Welcome Dr. Name';
            }
        });
    } else {
        window.location.href = 'index.html';
    }
});