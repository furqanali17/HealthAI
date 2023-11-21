import { auth, database } from './database_connection.js'; // Import auth and database
import { signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";
import { ref, get, update } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";

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

                // Create a reference to the specific data path in the Realtime Database
                const professionalRef = ref(database, 'Professionals/' + professional.uid);

                return get(professionalRef)
                    .then((snapshot) => {
                        if (snapshot.exists()) {
                            const lastLoginTime = new Date().toISOString();
                            // Update the last login time in the Realtime Database
                            return update(professionalRef, { lastLogin: lastLoginTime });
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