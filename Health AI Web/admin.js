import { auth, database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

onAuthStateChanged(auth, (user) => {
    if (user) {
        // User is signed in

        // Fetch and display the admin's name
        const adminRef = ref(database, 'Admins/' + user.uid);
        onValue(adminRef, (snapshot) => {
            const adminData = snapshot.val();
            if (adminData && adminData.fullname) {
                document.getElementById('admin-name').innerText = `Welcome Admin ${adminData.fullname}`;
            } else {
                console.error('Admin data not found');
                document.getElementById('admin-name').innerText = 'Welcome Admin';
            }
        });

    } else {
        // User is signed out
        window.location.href = 'login.html'; // Redirect to login page
    }
});