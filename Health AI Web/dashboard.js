import { auth, database } from './database_connection.js';
import { ref, onValue } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

onAuthStateChanged(auth, (user) => {
    if (user) {
        displayUserName('Professionals', user.uid, 'professional-name', 'Dr.');
        displayList('Users', 'patient-list');
    } else {
        window.location.href = 'index.html';
    }
});

function displayUserName(userType, userId, elementId, defaultTitle) {
    const userRef = ref(database, `${userType}/${userId}`);
    onValue(userRef, (snapshot) => {
        const userData = snapshot.val();
        if (userData && userData.fullname) {
            document.getElementById(elementId).innerText = `Welcome ${defaultTitle} ${userData.fullname}`;
        } else {
            console.error(`${defaultTitle} data not found`);
            document.getElementById(elementId).innerText = `Welcome ${defaultTitle}`;
        }
    });
}

function displayList(node, listElementId) {
    const refNode = ref(database, node);
    onValue(refNode, (snapshot) => {
        const listElement = document.getElementById(listElementId);
        listElement.innerHTML = '';

        if (snapshot.exists()) {
            snapshot.forEach((childSnapshot) => {
                const userId = childSnapshot.key;
                const details = childSnapshot.val();
                const listItem = createListItem(details, userId);
                listElement.appendChild(listItem);
            });
        } else {
            listElement.innerText = `No ${node} found`;
        }
    });
}

function createListItem(details, userId) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-list-item');

    listItem.innerHTML = `
        <div><strong>Name: </strong> ${details.name}</div>
        <div><strong>Age: </strong> ${details.age}</div>
        <div><strong>Sex: </strong> ${details.sex}</div>
        <div><strong>Mobile :</strong> ${details.mobile}</div>
        <div><strong>Email: </strong> ${details.email}</div>
    `;
    return listItem;
}