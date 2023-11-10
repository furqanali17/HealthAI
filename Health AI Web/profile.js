import { firestore, auth } from './database_connection.js';
import { doc, onSnapshot } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

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