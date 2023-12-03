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
            updateProfileDisplay(professionalData);
            checkProfileCompletion();
        } else {
            console.error('Professional data not found');
            document.getElementById('professional-name').innerText = 'Welcome Dr. Name';
        }
    });
}

var profileCompletionPromptShown = false;

function checkProfileCompletion() {
    function updateFieldWarning(fieldId, isEmpty) {
        const warningElement = document.getElementById(fieldId + '-warning');
        if (isEmpty) {
            warningElement.innerText = 'Please fill this information';
            warningElement.style.display = 'inline';
        } else {
            warningElement.innerText = '';
            warningElement.style.display = 'none';
        }
    }

    updateFieldWarning('doctor-title', !document.getElementById('doctor-title').value);
    updateFieldWarning('doctor-specialties', !document.getElementById('doctor-specialties').value);
    updateFieldWarning('doctor-mobile', !document.getElementById('doctor-mobile').value);

    const sexDisplayElement = document.getElementById('doctor-sex-display');
    const isGenderEmpty = sexDisplayElement.style.display === 'none';
    updateFieldWarning('sex', isGenderEmpty);
}

function recheckProfileCompletion() {
    profileCompletionPromptShown = false;
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
    checkProfileCompletion();
}

function updateField(fieldId, value, placeholder) {
    const element = document.getElementById(fieldId);
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
        recheckProfileCompletion();
    }).catch((error) => {
        console.error('Error updating profile:', error);
    });
}

document.getElementById('chatbot-button').addEventListener('click', function () {
    window.location.href = 'support.html';
});
window.toggleEditSave = toggleEditSave;
window.updateSex = updateSex;