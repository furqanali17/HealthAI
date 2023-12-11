package com.example.healthai.formActivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Form implements Parcelable {

    // Common
    private int age;
    private int gender;
    private int isSmoker;
    private int doesDrinkAlcohol;
    private int hasObesity;
    private int physicalActivity;

    // Dataset 1 ( // indicates has been added to form)
    //Commons:age, gender,
    // common derived: if smoking, ask smoking 1-8, if drinker ask for alcohol use
    private int airPollution; //
    private int alcoholUse; //
    private int dustAllergies; //
    private int occupationalHazards; //
    private int geneticRisk; // cancer?
    private int chronicLungDisease; //
    private int balancedDiet; //
    private int obesity; //
    private int  smoking; //
    private int passiveSmoker; //
    private int chestPain; //
    private int coughing; //
    private int fatigue; //
    private int weightLoss; //
    private int shortnessOfBreath; //
    private int wheezing; //
    private int swallowingDifficulty; //
    private int clubbingFingerNails; //
    private int frequentColds; //
    private int dryCough; //
    private int snoring; //

    // Dataset2
    // commons: smoker, drinker, obesity, age
    // common derived: age, activity
    private int hadPreviousCancer;
    private int hadPreviousColonCancer;
    private int hasFamilyHistory;
    private int isOldAge; // (derived from age input)
    private int hasIDB;
    private int exercisesRegularly; // derived from physical activity common
    private int hasHighFatDiet;

    private int hadRadiationTherapy;
    private int hasHadStroke;
    private int difficultyWalking;
    private int hasAsthma;

    private int hadKidneyDisease;
    private int hadPreviousSkinCancer;
    private String diabetic; //Y,N,Borderline,yes but pregnant
    private String generalHealth; // poor.fair,good,excellent,very good
    private String race; // white,black,asian,american indian,hispanic, other
    private int sleepTime; // 1-24 hours

    private float bmi;
    private int physicalHealth; // 1-30 scale
    private int mentalHealth;


    public Form(){

    }

    protected Form(Parcel in) {
        age = in.readInt();
        gender = in.readInt();
        isSmoker = in.readInt();
        doesDrinkAlcohol = in.readInt();
        hasObesity = in.readInt();
        physicalActivity = in.readInt();
        airPollution = in.readInt();
        alcoholUse = in.readInt();
        dustAllergies = in.readInt();
        occupationalHazards = in.readInt();
        geneticRisk = in.readInt();
        chronicLungDisease = in.readInt();
        balancedDiet = in.readInt();
        obesity = in.readInt();
        smoking = in.readInt();
        passiveSmoker = in.readInt();
        chestPain = in.readInt();
        coughing = in.readInt();
        fatigue = in.readInt();
        weightLoss = in.readInt();
        shortnessOfBreath = in.readInt();
        wheezing = in.readInt();
        swallowingDifficulty = in.readInt();
        clubbingFingerNails = in.readInt();
        frequentColds = in.readInt();
        dryCough = in.readInt();
        snoring = in.readInt();
        hadPreviousCancer = in.readInt();
        hadPreviousColonCancer = in.readInt();
        hasFamilyHistory = in.readInt();
        isOldAge = in.readInt();
        hasIDB = in.readInt();
        exercisesRegularly = in.readInt();
        hasHighFatDiet = in.readInt();
        hadRadiationTherapy = in.readInt();
        hasHadStroke = in.readInt();
        difficultyWalking = in.readInt();
        hasAsthma = in.readInt();
        hadKidneyDisease = in.readInt();
        hadPreviousSkinCancer = in.readInt();
        diabetic = in.readString();
        generalHealth = in.readString();
        race = in.readString();
        sleepTime = in.readInt();
        bmi = in.readFloat();
        physicalHealth = in.readInt();
        mentalHealth = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeInt(gender);
        dest.writeInt(isSmoker);
        dest.writeInt(doesDrinkAlcohol);
        dest.writeInt(hasObesity);
        dest.writeInt(physicalActivity);
        dest.writeInt(airPollution);
        dest.writeInt(alcoholUse);
        dest.writeInt(dustAllergies);
        dest.writeInt(occupationalHazards);
        dest.writeInt(geneticRisk);
        dest.writeInt(chronicLungDisease);
        dest.writeInt(balancedDiet);
        dest.writeInt(obesity);
        dest.writeInt(smoking);
        dest.writeInt(passiveSmoker);
        dest.writeInt(chestPain);
        dest.writeInt(coughing);
        dest.writeInt(fatigue);
        dest.writeInt(weightLoss);
        dest.writeInt(shortnessOfBreath);
        dest.writeInt(wheezing);
        dest.writeInt(swallowingDifficulty);
        dest.writeInt(clubbingFingerNails);
        dest.writeInt(frequentColds);
        dest.writeInt(dryCough);
        dest.writeInt(snoring);
        dest.writeInt(hadPreviousCancer);
        dest.writeInt(hadPreviousColonCancer);
        dest.writeInt(hasFamilyHistory);
        dest.writeInt(isOldAge);
        dest.writeInt(hasIDB);
        dest.writeInt(exercisesRegularly);
        dest.writeInt(hasHighFatDiet);
        dest.writeInt(hadRadiationTherapy);
        dest.writeInt(hasHadStroke);
        dest.writeInt(difficultyWalking);
        dest.writeInt(hasAsthma);
        dest.writeInt(hadKidneyDisease);
        dest.writeInt(hadPreviousSkinCancer);
        dest.writeString(diabetic);
        dest.writeString(generalHealth);
        dest.writeString(race);
        dest.writeInt(sleepTime);
        dest.writeFloat(bmi);
        dest.writeInt(physicalHealth);
        dest.writeInt(mentalHealth);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Form> CREATOR = new Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel in) {
            return new Form(in);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };

    public void page1Input(int age, boolean gender, boolean isSmoker, boolean doesDrinkAlcohol,
                           boolean hasObesity, boolean physicalActivity){
        this.age = age;
        this.gender = (gender) ? 0 : 1;
        this.isSmoker = (isSmoker) ? 1 :0;
        this.doesDrinkAlcohol = (doesDrinkAlcohol) ? 1 : 0;
        this.hasObesity= (hasObesity) ? 1 : 0;
        this.physicalActivity = (physicalActivity) ? 1 : 0;



        this.exercisesRegularly = (physicalActivity) ? 1 : 0;

        if (age >= 65)
            this.isOldAge = 1;
        else
            this.isOldAge = 0;
    }

    public void page2Input(int alcohol,int polution, int smoking,
                           int hazards, int diet, int obesity,
                           int passive_smoke, int dust_allergies,
                           int chronic_lung_disease){
        this.alcoholUse = alcohol;
        this.airPollution = polution;
        this.smoking = smoking;
        this.occupationalHazards = hazards;
        this.balancedDiet = diet;
        this.dustAllergies = dust_allergies;
        this.passiveSmoker = passive_smoke;
        this.obesity = obesity;
        this.chronicLungDisease = chronic_lung_disease;
    }

    public void page3Input(int snore, int frequentColds,int dryCough,
                           int chestPain,int swallowingDifficulty,int shortnessOfBreath,
                           int weezing, int weightLoss, int fatigue,
                           int clubbingFingerNails, int coughing){
        this.snoring = snore;
        this.frequentColds = frequentColds;
        this.dryCough = dryCough;
        this.wheezing = weezing;
        this.weightLoss = weightLoss;
        this.fatigue = fatigue;
        this.clubbingFingerNails = clubbingFingerNails;
        this.coughing = coughing;
        this.chestPain = chestPain;
        this.swallowingDifficulty = swallowingDifficulty;
        this.shortnessOfBreath = shortnessOfBreath;
    }

    public void page4Input(boolean hasHighFatDiet,boolean hasIDB,
                           boolean hasFamilyHistory,boolean hadPreviousColonCancer,
                           boolean hadPreviousCancer,boolean hadRadiationTherapy){
        this.hasHighFatDiet = (hasHighFatDiet) ? 1 : 0;
        this.hasIDB = (hasIDB) ? 1 : 0;
        this.hasFamilyHistory = (hasFamilyHistory) ? 1 : 0;
        this.hadPreviousColonCancer= (hadPreviousColonCancer) ? 1:0;
        this.hadPreviousCancer = (hadPreviousCancer) ?1:0;
        this.hadRadiationTherapy = (hadRadiationTherapy) ? 1 : 0;
    }

    public void page5Input(boolean stroke, boolean difficultyWalking,
                           boolean hasAsthma, boolean hadKidneyDisease,
                           boolean hadPreviousSkinCancer){
        this.hasHadStroke = (stroke) ? 1 : 0;
        this.difficultyWalking = (difficultyWalking) ? 1 : 0;
        this.hasAsthma = (hasAsthma) ? 1 : 0;
        this.hadKidneyDisease = (hadKidneyDisease) ? 1 : 0;
        this.hadPreviousSkinCancer = (hadPreviousSkinCancer) ? 1 : 0;
    }

    public void page6Input(String diabetic,String generalHealth,
                           String race, int avgSleepTime,
                           int bmi,int physicalHealthRating,
                           int mentalHealthRating){
        this.diabetic = diabetic;
        this.generalHealth = generalHealth;
        this.race = race;
        this.sleepTime = avgSleepTime;
        this.bmi = bmi;
        this.physicalHealth = physicalHealthRating;
        this.mentalHealth = mentalHealthRating;

    }


    public static boolean checkIntInput(int min, int max, int input){
        boolean res = false;
        if (input >= min && input <= max)
            res = true;
        return res;
    }

    public static boolean validateRGInput(RadioGroup rg, AtomicBoolean res, boolean formComplete){
        int rbID = rg.getCheckedRadioButtonId();
        if (rbID != -1 && formComplete){
            RadioButton rb = rg.findViewById(rbID);
            if (rb.getText().equals("Yes"))
                res.set(true);
            else
                res.set(false);

            return true;
        }
        else{
            return false;
        }
    }    public static boolean validateRGInput(RadioGroup rg, AtomicBoolean res, boolean formComplete, String titleTrue){
        int rbID = rg.getCheckedRadioButtonId();
        if (rbID != -1 && formComplete){
            RadioButton rb = rg.findViewById(rbID);
            if (rb.getText().equals(titleTrue))
                res.set(true);
            else
                res.set(false);

            return true;
        }
        else{
            return false;
        }
    }

    public static boolean validateRGInput(RadioGroup rg, AtomicReference<String> res, boolean formComplelte){
        int rbID = rg.getCheckedRadioButtonId();
        if(rbID != -1 && formComplelte){
            RadioButton rb = rg.findViewById(rbID);
            res.set(rb.getText().toString());
            return true;
        }
        else
            return false;
    }

    public void readFromParcel(Parcel source) {
        this.age = source.readInt();
        this.gender = source.readInt();
        this.isSmoker = source.readInt();
        this.doesDrinkAlcohol = source.readInt();
        this.hasObesity = source.readInt();
        this.physicalActivity = source.readInt();
        this.airPollution = source.readInt();
        this.alcoholUse = source.readInt();
        this.dustAllergies = source.readInt();
        this.occupationalHazards = source.readInt();
        this.geneticRisk = source.readInt();
        this.chronicLungDisease = source.readInt();
        this.balancedDiet = source.readInt();
        this.obesity = source.readInt();
        this.smoking = source.readInt();
        this.passiveSmoker = source.readInt();
        this.chestPain = source.readInt();
        this.coughing = source.readInt();
        this.fatigue = source.readInt();
        this.weightLoss = source.readInt();
        this.shortnessOfBreath = source.readInt();
        this.wheezing = source.readInt();
        this.swallowingDifficulty = source.readInt();
        this.clubbingFingerNails = source.readInt();
        this.frequentColds = source.readInt();
        this.dryCough = source.readInt();
        this.snoring = source.readInt();
        this.hadPreviousCancer = source.readInt();
        this.hadPreviousColonCancer = source.readInt();
        this.hasFamilyHistory = source.readInt();
        this.isOldAge = source.readInt();
        this.hasIDB = source.readInt();
        this.exercisesRegularly = source.readInt();
        this.hasHighFatDiet = source.readInt();
        this.hasHadStroke = source.readInt();
        this.difficultyWalking = source.readInt();
        this.hasAsthma = source.readInt();
        this.hadKidneyDisease = source.readInt();
        this.hadPreviousSkinCancer = source.readInt();
        this.diabetic = source.readString();
        this.generalHealth = source.readString();
        this.race = source.readString();
        this.sleepTime = source.readInt();
        this.bmi = source.readFloat();
        this.physicalHealth = source.readInt();
        this.mentalHealth = source.readInt();
    }

    public int getHadRadiationTherapy() {
        return hadRadiationTherapy;
    }

    public void setHadRadiationTherapy(int hadRadiationTherapy) {
        this.hadRadiationTherapy = hadRadiationTherapy;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(int isSmoker) {
        this.isSmoker = isSmoker;
    }

    public int getDoesDrinkAlcohol() {
        return doesDrinkAlcohol;
    }

    public void setDoesDrinkAlcohol(int doesDrinkAlcohol) {
        this.doesDrinkAlcohol = doesDrinkAlcohol;
    }

    public int getHasObesity() {
        return hasObesity;
    }

    public void setHasObesity(int hasObesity) {
        this.hasObesity = hasObesity;
    }

    public int getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(int physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public int getAirPollution() {
        return airPollution;
    }

    public void setAirPollution(int airPollution) {
        this.airPollution = airPollution;
    }

    public int getAlcoholUse() {
        return alcoholUse;
    }

    public void setAlcoholUse(int alcoholUse) {
        this.alcoholUse = alcoholUse;
    }

    public int getDustAllergies() {
        return dustAllergies;
    }

    public void setDustAllergies(int dustAllergies) {
        this.dustAllergies = dustAllergies;
    }

    public int getOccupationalHazards() {
        return occupationalHazards;
    }

    public void setOccupationalHazards(int occupationalHazards) {
        this.occupationalHazards = occupationalHazards;
    }

    public int getGeneticRisk() {
        return geneticRisk;
    }

    public void setGeneticRisk(int geneticRisk) {
        this.geneticRisk = geneticRisk;
    }

    public int getChronicLungDisease() {
        return chronicLungDisease;
    }

    public void setChronicLungDisease(int chronicLungDisease) {
        this.chronicLungDisease = chronicLungDisease;
    }

    public int getBalancedDiet() {
        return balancedDiet;
    }

    public void setBalancedDiet(int balancedDiet) {
        this.balancedDiet = balancedDiet;
    }

    public int getObesity() {
        return obesity;
    }

    public void setObesity(int obesity) {
        this.obesity = obesity;
    }

    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    public int getPassiveSmoker() {
        return passiveSmoker;
    }

    public void setPassiveSmoker(int passiveSmoker) {
        this.passiveSmoker = passiveSmoker;
    }

    public int getChestPain() {
        return chestPain;
    }

    public void setChestPain(int chestPain) {
        this.chestPain = chestPain;
    }

    public int getCoughing() {
        return coughing;
    }

    public void setCoughing(int coughing) {
        this.coughing = coughing;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(int weightLoss) {
        this.weightLoss = weightLoss;
    }

    public int getShortnessOfBreath() {
        return shortnessOfBreath;
    }

    public void setShortnessOfBreath(int shortnessOfBreath) {
        this.shortnessOfBreath = shortnessOfBreath;
    }

    public int getWheezing() {
        return wheezing;
    }

    public void setWheezing(int wheezing) {
        this.wheezing = wheezing;
    }

    public int getSwallowingDifficulty() {
        return swallowingDifficulty;
    }

    public void setSwallowingDifficulty(int swallowingDifficulty) {
        this.swallowingDifficulty = swallowingDifficulty;
    }

    public int getClubbingFingerNails() {
        return clubbingFingerNails;
    }

    public void setClubbingFingerNails(int clubbingFingerNails) {
        this.clubbingFingerNails = clubbingFingerNails;
    }

    public int getFrequentColds() {
        return frequentColds;
    }

    public void setFrequentColds(int frequentColds) {
        this.frequentColds = frequentColds;
    }

    public int getDryCough() {
        return dryCough;
    }

    public void setDryCough(int dryCough) {
        this.dryCough = dryCough;
    }

    public int getSnoring() {
        return snoring;
    }

    public void setSnoring(int snoring) {
        this.snoring = snoring;
    }

    public int getHadPreviousCancer() {
        return hadPreviousCancer;
    }

    public void setHadPreviousCancer(int hadPreviousCancer) {
        this.hadPreviousCancer = hadPreviousCancer;
    }

    public int getHadPreviousColonCancer() {
        return hadPreviousColonCancer;
    }

    public void setHadPreviousColonCancer(int hadPreviousColonCancer) {
        this.hadPreviousColonCancer = hadPreviousColonCancer;
    }

    public int getHasFamilyHistory() {
        return hasFamilyHistory;
    }

    public void setHasFamilyHistory(int hasFamilyHistory) {
        this.hasFamilyHistory = hasFamilyHistory;
    }

    public int getIsOldAge() {
        return isOldAge;
    }

    public void setIsOldAge(int isOldAge) {
        this.isOldAge = isOldAge;
    }

    public int getHasIDB() {
        return hasIDB;
    }

    public void setHasIDB(int hasIDB) {
        this.hasIDB = hasIDB;
    }

    public int getExercisesRegularly() {
        return exercisesRegularly;
    }

    public void setExercisesRegularly(int exercisesRegularly) {
        this.exercisesRegularly = exercisesRegularly;
    }

    public int getHasHighFatDiet() {
        return hasHighFatDiet;
    }

    public void setHasHighFatDiet(int hasHighFatDiet) {
        this.hasHighFatDiet = hasHighFatDiet;
    }

    public int getHasHadStroke() {
        return hasHadStroke;
    }

    public void setHasHadStroke(int hasHadStroke) {
        this.hasHadStroke = hasHadStroke;
    }

    public int getDifficultyWalking() {
        return difficultyWalking;
    }

    public void setDifficultyWalking(int difficultyWalking) {
        this.difficultyWalking = difficultyWalking;
    }

    public int getHasAsthma() {
        return hasAsthma;
    }

    public void setHasAsthma(int hasAsthma) {
        this.hasAsthma = hasAsthma;
    }

    public int getHadKidneyDisease() {
        return hadKidneyDisease;
    }

    public void setHadKidneyDisease(int hadKidneyDisease) {
        this.hadKidneyDisease = hadKidneyDisease;
    }

    public int getHadPreviousSkinCancer() {
        return hadPreviousSkinCancer;
    }

    public void setHadPreviousSkinCancer(int hadPreviousSkinCancer) {
        this.hadPreviousSkinCancer = hadPreviousSkinCancer;
    }

    public String getDiabetic() {
        return diabetic;
    }

    public void setDiabetic(String diabetic) {
        this.diabetic = diabetic;
    }

    public String getGeneralHealth() {
        return generalHealth;
    }

    public void setGeneralHealth(String generalHealth) {
        this.generalHealth = generalHealth;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public int getPhysicalHealth() {
        return physicalHealth;
    }

    public void setPhysicalHealth(int physicalHealth) {
        this.physicalHealth = physicalHealth;
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(int mentalHealth) {
        this.mentalHealth = mentalHealth;
    }

}