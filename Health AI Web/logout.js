import { auth } from './database_connection.js'; 
import { signOut } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('logout').addEventListener('click', async (event) => {
        event.preventDefault();  // Prevent the default link behavior

        // Show a confirmation dialog
        const confirmLogout = confirm("Are you sure you want to log out?");
        if (confirmLogout) {
            try {
                // Sign out the user
                await signOut(auth);
                // Redirect to the login page
                window.location.href = 'login.html';
            } catch (error) {
                console.error('Error during logout:', error);
                alert('Logout failed. Please try again.');
            }
        }
    });
});