// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth, onAuthStateChanged } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';


// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCTMf-Pttj9U-KZ_kOFIBEWQgrWPkG4MJA",
  authDomain: "dfos-healthai.firebaseapp.com",
  projectId: "dfos-healthai",
  storageBucket: "dfos-healthai.appspot.com",
  messagingSenderId: "259705293143",
  appId: "1:259705293143:web:889a820ce52326e592d612"
};

const auth = getAuth();
const db = getFirestore();

// Initialize Firebase
const app = initializeApp(firebaseConfig);
