// JavaScript for handling the logout prompt
const logoutLink = document.getElementById('logout-link');

logoutLink.addEventListener('click', function (event) {
    event.preventDefault(); // Prevent the default link behavior
    const confirmLogout = confirm('Are you sure you want to log out?');
    if (confirmLogout) {
        // Redirect to the logout page or perform logout action
        window.location.href = 'index.html';
    }
});