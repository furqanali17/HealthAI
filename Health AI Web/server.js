const express = require('express');
const cors = require('cors');
const admin = require('firebase-admin');
const fs = require('fs');
const path = require('path');
const app = express();
const serviceAccount = require('./dfos-healthai-firebase-adminsdk-aiz5j-eb586fff85.json');

app.use(cors());
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://dfos-healthai-default-rtdb.firebaseio.com",
});

app.get('/', (req, res) => {
    res.send('Server is running.');
});

app.delete('/deleteUser/:userId', (req, res) => {
    const userId = req.params.userId;

    admin.auth().deleteUser(userId)
        .then(() => {
            return admin.database().ref(`Professionals/${userId}`).remove();
        })
        .then(() => {
            res.send({ message: `Successfully deleted user ${userId}` });
        })
        .catch((error) => {
            console.error("Error deleting user:", error);
            res.status(500).send({ error: 'Internal Server Error' });
        });
});

app.get('/createDataset', (req, res) => {
    admin.database().ref('Users').once('value')
        .then((snapshot) => {
            if (snapshot.exists()) {
                const users = snapshot.val();
                Object.entries(users).forEach(([userId, userData]) => {
                    if (userData.forms && Object.keys(userData.forms).length > 0) {
                        const fileName = `dataset${userId % 3 + 1}.csv`;
                        appendToCSVFile(userId, userData.forms, fileName);
                    }
                });
                res.send({ message: "New aggregate data set created" });
            } else {
                res.status(404).send({ error: 'No users found' });
            }
        })
        .catch((error) => {
            console.error("Error fetching users:", error);
            res.status(500).send({ error: 'Internal Server Error' });
        });
});

function appendToCSVFile(userId, formData, fileName) {
    const csvContent = Object.entries(formData).map(([formId, formData]) => {
        return Object.values(formData).join(',');
    }).join('\n');

    const filePath = path.join(__dirname, 'Datasets', fileName);

    fs.appendFile(filePath, csvContent + '\n', (err) => {
        if (err) throw err;
        console.log(`Data from user ${userId} appended to ${fileName}`);
    });
}

const port = 3000;
app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});