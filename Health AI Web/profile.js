import { auth, database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

onAuthStateChanged(auth, (user) => {
    if (user) {
        const professionalRef = ref(database, 'Professionals/' + user.uid);
        onValue(professionalRef, (snapshot) => {
            if (snapshot.exists()) {
                const professionalData = snapshot.val();
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