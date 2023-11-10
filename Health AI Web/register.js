import { firestore, auth } from './database_connection.js';
import { createUserWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";
import { doc, setDoc } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";

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

async function registerUser(fullname, email, password) {
    try {
        const professionalCredential = await createUserWithEmailAndPassword(auth, email, password);
        const professional = professionalCredential.user;

        const professionalDocRef = doc(firestore, 'professionals', professional.uid);
        await setDoc(professionalDocRef, {
            fullname: fullname,
            email: email
        });

        alert('User created and data saved!');
        window.location.href = 'dashboard.html';
    } catch (error) {
        alert(error.message);
    }
}

const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const fullname = document.getElementById('fullName').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;

        if (validateForm(fullname, email, password)) {
            registerUser(fullname, email, password);
        }
    });
} else {
    console.error('Register form not found.');
}