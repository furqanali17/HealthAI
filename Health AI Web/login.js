import { auth, firestore } from './database_connection.js'; // Import auth and firestore
import { signInWithEmailAndPassword, getAuth, onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";
import { doc, getDoc, updateDoc } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";

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