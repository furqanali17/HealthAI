import { auth, database } from './database_connection.js';
import { ref, onValue, set } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";
import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

onAuthStateChanged(auth, (user) => {
    if (user) {
        loadProfileData(user);
    } else {
        window.location.href = 'index.html';
    }
});

function loadProfileData(user) {
    const professionalRef = ref(database, 'Professionals/' + user.uid);
    onValue(professionalRef, (snapshot) => {
        if (snapshot.exists()) {
            const professionalData = snapshot.val();
            updateProfileDisplay(professionalData); // Update fields with data
            checkProfileCompletion(); // Then check for completeness
        } else {
            console.error('Professional data not found');
            document.getElementById('professional-name').innerText = 'Welcome Dr. Name';
        }
    });
}

var profileCompletionPromptShown = false;

function checkProfileCompletion() {
    // Function to update individual field warning
    function updateFieldWarning(fieldId, isEmpty) {
        const warningElement = document.getElementById(fieldId + '-warning');
        if (isEmpty) {
            warningElement.innerText = 'Please fill this information';
            warningElement.style.display = 'inline'; // Show the warning
        } else {
            warningElement.innerText = '';
            warningElement.style.display = 'none'; // Hide the warning
        }
    }

    // Check each text field and update warning accordingly
    updateFieldWarning('doctor-title', !document.getElementById('doctor-title').value);
    updateFieldWarning('doctor-specialties', !document.getElementById('doctor-specialties').value);
    updateFieldWarning('doctor-mobile', !document.getElementById('doctor-mobile').value);

    // Check and update gender field warning
    const sexDisplayElement = document.getElementById('doctor-sex-display');
    const isGenderEmpty = sexDisplayElement.style.display === 'none'; // Check if sex field is hidden
    updateFieldWarning('sex', isGenderEmpty);
}

// Call this function every time a field is updated
function recheckProfileCompletion() {
    profileCompletionPromptShown = false;  // Reset the flag when fields are updated
    checkProfileCompletion();
}

function updateProfileDisplay(data) {
    document.getElementById('professional-name').innerText = `Welcome Dr. ${data.fullname}`;
    document.getElementById('doctor-name').innerText = data.fullname;
    document.getElementById('doctor-email').innerText = data.email;

    updateField('doctor-title', data.academicTitle, 'Enter Academic Title');
    updateField('doctor-mobile', data.mobile, 'Enter Mobile Number');
    updateField('doctor-specialties', data.specialties, 'Enter Specialties');
    updateSexField(data.sex);
    checkProfileCompletion(); // Check for empty fields after updating UI
}

function updateField(fieldId, value, placeholder) {
    const element = document.getElementById(fieldId);
    // Check if value is not null or undefined, but allow empty string
    element.value = (value !== null && value !== undefined) ? value : '';
    element.placeholder = (value !== null && value !== undefined) ? '' : placeholder;
}

function updateSexField(sexValue) {
    const sexDisplayElement = document.getElementById('doctor-sex-display');
    const sexSelectionElement = document.getElementById('sex-selection');

    if (sexValue) {
        sexDisplayElement.innerText = sexValue;
        sexDisplayElement.style.display = 'block';
        sexSelectionElement.style.display = 'none';
    } else {
        sexDisplayElement.style.display = 'none';
        sexSelectionElement.style.display = 'block';
    }
}
function updateSex(selectedSex) {
    updateProfileField(auth.currentUser.uid, 'sex', selectedSex);
}

function toggleEditSave(elementId) {
    var element = document.getElementById(elementId);
    var button = document.querySelector(`button[onclick="toggleEditSave('${elementId}')"]`);

    if (element.disabled) {
        element.disabled = false;
        element.focus();
        button.innerText = 'Save';
    } else {
        element.disabled = true;
        button.innerText = 'Edit';
        updateProfileField(auth.currentUser.uid, elementId, element.value);
    }
}

function updateProfileField(uid, field, value) {
    const fieldMap = {
        'doctor-mobile': 'mobile',
        'doctor-specialties': 'specialties',
        'doctor-title': 'academicTitle',
        'sex': 'sex'
    };

    if (field === 'sex') {
        const sexDisplayElement = document.getElementById('doctor-sex-display');
        sexDisplayElement.innerText = value;
        sexDisplayElement.style.display = 'block';
        document.getElementById('sex-selection').style.display = 'none';
    }

    const professionalRef = ref(database, `Professionals/${uid}/${fieldMap[field]}`);
    set(professionalRef, value).then(() => {
        console.log(`${fieldMap[field]} updated successfully`);
        recheckProfileCompletion();  // Call after successful update
    }).catch((error) => {
        console.error('Error updating profile:', error);
    });
}

window.toggleEditSave = toggleEditSave;
window.updateSex = updateSex;