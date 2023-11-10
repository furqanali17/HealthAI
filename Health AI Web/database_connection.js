import { initializeApp } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-app.js";
import { getFirestore } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-firestore.js";
import { getAuth } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-auth.js";

const firebaseConfig = {
  apiKey: "AIzaSyCTMf-Pttj9U-KZ_kOFIBEWQgrWPkG4MJA",
  authDomain: "dfos-healthai.firebaseapp.com",
  databaseURL: "https://dfos-healthai-default-rtdb.firebaseio.com",
  projectId: "dfos-healthai",
  storageBucket: "dfos-healthai.appspot.com",
  messagingSenderId: "259705293143",
  appId: "1:259705293143:web:889a820ce52326e592d612"
};

const app = initializeApp(firebaseConfig);
export const firestore = getFirestore(app);
export const auth = getAuth();
