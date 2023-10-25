import { initializeApp } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-app.js";
import { getFirestore, doc, updateDoc, getDoc } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";
import { getAuth, signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

const firebaseConfig = {
    apiKey: "AIzaSyCTMf-Pttj9U-KZ_kOFIBEWQgrWPkG4MJA",
    authDomain: "dfos-healthai.firebaseapp.com",
    projectId: "dfos-healthai",
    storageBucket: "dfos-healthai.appspot.com",
    messagingSenderId: "259705293143",
    appId: "1:259705293143:web:889a820ce52326e592d612"
};

const app = initializeApp(firebaseConfig);
const firestore = getFirestore(app);
const auth = getAuth();

function validateLoginForm(email, password) {
    if (!email || !password) {
        alert("Both fields are required!");
        return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Please enter a valid email address");
        return false;
    }

    if (password.length < 8) {
        alert("Password must be at least 8 characters long");
        return false;
    }

    return true;
}

document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    if (validateLoginForm(email, password)) {
        signInWithEmailAndPassword(auth, email, password)
            .then((professionalCredential) => {
                const professional = professionalCredential.user;

                // Create a reference to the specific document in the "professionals" collection
                const professionalDocRef = doc(firestore, 'professionals', professional.uid);

                return getDoc(professionalDocRef)
                    .then((docSnapshot) => {
                        if (docSnapshot.exists()) {
                            const lastLoginTime = new Date().toISOString();
                            // Update the last login time in the Firestore document
                            return updateDoc(professionalDocRef, { lastLogin: lastLoginTime });
                        } else {
                            alert('Professional not found in the database');
                        }
                    });
            })
            .then(() => {
                // Redirect to the dashboard page
                window.location.href = 'dashboard.html';
            })
            .catch((error) => {
                const errorMessage = error.message;
                alert(errorMessage);
            });
    }
});