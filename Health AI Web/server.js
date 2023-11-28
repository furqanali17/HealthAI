const express = require('express');
const cors = require('cors');
const admin = require('firebase-admin');
const app = express();
const serviceAccount = require('./dfos-healthai-firebase-adminsdk-aiz5j-7cfff48431.json');

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

const port = 3000;
app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});