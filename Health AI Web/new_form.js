import { database } from './database_connection.js';
import { ref, push } from "https://www.gstatic.com/firebasejs/10.5.0/firebase-database.js";

document.getElementById('new-dataset-form').addEventListener('submit', function (e) {
    e.preventDefault();

    // Create a FormData object directly from the form element
    const formData = new FormData(this);

    // Call saveNewDataset and pass the FormData object to it
    saveNewDataset(formData);
});

function saveNewDataset(formData) {
    // Convert form data to the specified structure
    const formObject = {
        age: Number(formData.get('age')),
        airPollution: formData.get('air_pollution_exposure'),
        alcoholUse: formData.get('alcohol_consumption_frequency'),
        balancedDiet: formData.get('balanced_diet'),
        bmi: Number(formData.get('bmi')),
        chestPain: formData.get('chest_pain_frequency'),
        chronicLungDisease: formData.get('inflammatory_bowel_disease') === 'Yes' ? 1 : 0,
        clubbingFingerNails: formData.get('clubbing_finger_nails'),
        coughing: formData.get('frequent_cough'),
        diabetic: formData.get('diabetic'),
        difficultyWalking: formData.get('difficulty_walking') === 'Yes' ? true : false,
        doesDrinkAlcohol: formData.get('alcohol_consumption') === 'Yes' ? true : false,
        dryCough: formData.get('dry_cough_frequency'),
        dustAllergies: formData.get('dust_allergies'),
        exercisesRegularly: formData.get('exercises_regularly') === 'Yes' ? true : false, // issue
        fatigue: formData.get('fatigue_frequency'),
        frequentColds: formData.get('frequent_colds'),
        gender: formData.get('gender') === 'Male' ? true : false,
        generalHealth: formData.get('general_health'),
        geneticRisk: formData.get('genetic_risk') === 'Yes' ? 1 : 0, // issue
        hadKidneyDisease: formData.get('kidney_disease_history') === 'Yes' ? true : false,
        hadPreviousCancer: formData.get('cancer_history') === 'Yes' ? true : false, // issue
        hadPreviousColonCancer: formData.get('colon_cancer_history') === 'Yes' ? true : false,
        hadPreviousSkinCancer: formData.get('skin_cancer_history') === 'Yes' ? true : false,
        hasAsthma: formData.get('asthma') === 'Yes' ? true : false,
        hasFamilyHistory: formData.get('family_cancer_history') === 'Yes' ? true : false,
        hasHadStroke: formData.get('stroke_history') === 'Yes' ? true : false,
        hasHighFatDiet: formData.get('high_fat_diet') === 'Yes' ? true : false,
        hasIDB: formData.get('has_idb') === 'Yes' ? true : false, // issue
        hasObesity: formData.get('has_obesity') === 'Yes' ? true : false,
        obesity: Number(formData.get('obesity')),
        occupationalHazards: formData.get('occupational_hazards_risk'),
        oldAge: Number(formData.get('age')) >= 60,
        passiveSmoker: formData.get('passive_smoking'),
        physicalActivity: formData.get('physical_activity') === 'Yes' ? true : false,
        physicalHealth: Number(formData.get('general_health')),
        race: formData.get('race'),
        sex: formData.get('gender') === 'Male' ? 'M' : 'F',
        shortnessOfBreath: formData.get('shortness_of_breath') === 'Yes' ? 1 : 0,
        sleepTime: Number(formData.get('average_sleep_time')),
        smoker: formData.get('smoker') === 'Yes' ? true : false, // issue 
        smoking: formData.get('smoking_frequency'),
        snoring: formData.get('snoring_frequency'),
        stability: formData.get('stability') === 'Yes' ? 1 : 0, // issue
        swallowingDifficulty: formData.get('difficulty_swallowing'),
        weightLoss: formData.get('weight_loss'),
        wheezing: formData.get('wheezing_frequency')
    };

    // Push to the database
    const newFormRef = ref(database, 'Forms');
    push(newFormRef, formObject).then(() => {
        console.log('New dataset saved successfully.');
        // Redirect or display a success message
    }).catch((error) => {
        console.error('Failed to save new dataset:', error);
        // Handle the error, display a message to the user
    });
}