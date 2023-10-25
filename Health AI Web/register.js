import { initializeApp } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-app.js";
import { getFirestore, doc, setDoc } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";
import { getAuth, createUserWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

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

function validateForm(fullname, email, password) {
    if (!fullname || !email || !password) {
        alert("All fields are required!");
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

document.getElementById('registerForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const fullname = document.getElementById('fullName').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (validateForm(fullname, email, password)) {
        createUserWithEmailAndPassword(auth, email, password)
            .then((professionalCredential) => {
                const professional = professionalCredential.user;

                // Create a reference to a specific document in the "professionals" collection
                const professionalDocRef = doc(firestore, 'professionals', professional.uid);

                // Set the data for the document
                return setDoc(professionalDocRef, {
                    fullname: fullname,
                    email: email
                });
            })
            .then(() => {
                alert('User created and data saved!');
                // Redirect to the dashboard page
                window.location.href = 'dashboard.html';
            })
            .catch((error) => {
                const errorMessage = error.message;
                alert(errorMessage);
            });
    }
});