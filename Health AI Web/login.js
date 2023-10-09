// Get references to the email, password, and login button elements
const loginEmail = document.getElementById('loginEmail');
const loginPassword = document.getElementById('loginPassword');
const btnLogin = document.getElementById('btn-Login');

// Function to handle the login
function handleLogin(event) {
    event.preventDefault(); // Prevent the form from submitting

    // Get the values entered by the user
    const enteredEmail = loginEmail.value;
    const enteredPassword = loginPassword.value;

    // Check if the entered email and password match the admin credentials
    if (enteredEmail === 'admin' && enteredPassword === 'admin') {
        // Redirect to admin.html
        window.location.href = 'admin.html';
    } else {
        // Redirect to dashboard.html for all other cases
        window.location.href = 'dashboard.html';
    }
}

// Attach the handleLogin function to the login button's click event
btnLogin.addEventListener('click', handleLogin);