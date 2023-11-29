package com.example.healthai.formActivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Form implements Parcelable {

    // Common
    private int age;
    private boolean gender;
    private boolean isSmoker;
    private boolean doesDrinkAlcohol;
    private boolean hasObesity;
    private boolean physicalActivity;

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
    private boolean hadPreviousCancer;
    private boolean hadPreviousColonCancer;
    private boolean hasFamilyHistory;
    private boolean isOldAge; // (derived from age input)
    private boolean hasIDB;
    private boolean exercisesRegularly; // derived from physical activity common
    private boolean hasHighFatDiet;

    // Dataset3
    // commons: smoker, drinker, age,  activity
    // common derived: sex,
    private boolean hasHadStroke;
    private boolean difficultyWalking;
    private boolean hasAsthma;

    private boolean hadKidneyDisease;
    private boolean hadPreviousSkinCancer;
    private String diabetic; //Y,N,Borderline,yes but pregnant
    private String generalHealth; // poor.fair,good,excellent,very good
    private char sex; // derived from gender common
    private String race; // white,black,asian,american indian,hispanic, other
    private int sleepTime; // 1-24 hours

    private float bmi;

    private int physicalHealth; // 1-30 scale

    public Form(){

    }

    public void page1Input(int age, boolean gender, boolean isSmoker,boolean doesDrinkAlcohol,
                           boolean hasObesity,boolean physicalActivity){
        this.age = age;
        this.gender = gender;
        this.isSmoker = isSmoker;
        this.doesDrinkAlcohol = doesDrinkAlcohol;
        this.hasObesity= hasObesity;
        this.physicalActivity = physicalActivity;

        // derived
        if(gender)
            this.sex = 'M';
        else
            this.sex = 'F';

        this.exercisesRegularly = physicalActivity;

        if (age >= 65)
            this.isOldAge = true;
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
                           boolean hadPreviousCancer){
        this.hasHighFatDiet = hasHighFatDiet;
        this.hasIDB = hasIDB;
        this.hasFamilyHistory = hasFamilyHistory;
        this.hadPreviousColonCancer= hadPreviousColonCancer;
        this.hadPreviousCancer = hadPreviousCancer;
    }

    public void page5Input(boolean stroke, boolean difficultyWalking,
                           boolean hasAsthma, boolean hadKidneyDisease,
                           boolean hadPreviousSkinCancer){
        this.hasHadStroke = stroke;
        this.difficultyWalking = difficultyWalking;
        this.hasAsthma = hasAsthma;
        this.hadKidneyDisease = hadKidneyDisease;
        this.hadPreviousSkinCancer = hadPreviousSkinCancer;
    }

    public void page6Input(String diabetic,String generalHealth,
                           String race, int avgSleepTime,
                           int bmi,int physicalHealthRating){
        this.diabetic = diabetic;
        this.generalHealth = generalHealth;
        this.race = race;
        this.sleepTime = avgSleepTime;
        this.bmi = bmi;
        this.physicalHealth = physicalHealthRating;

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeByte(this.gender ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSmoker ? (byte) 1 : (byte) 0);
        dest.writeByte(this.doesDrinkAlcohol ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasObesity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.physicalActivity ? (byte) 1 : (byte) 0);
        dest.writeInt(this.airPollution);
        dest.writeInt(this.alcoholUse);
        dest.writeInt(this.dustAllergies);
        dest.writeInt(this.occupationalHazards);
        dest.writeInt(this.geneticRisk);
        dest.writeInt(this.chronicLungDisease);
        dest.writeInt(this.balancedDiet);
        dest.writeInt(this.obesity);
        dest.writeInt(this.smoking);
        dest.writeInt(this.passiveSmoker);
        dest.writeInt(this.chestPain);
        dest.writeInt(this.coughing);
        dest.writeInt(this.fatigue);
        dest.writeInt(this.weightLoss);
        dest.writeInt(this.shortnessOfBreath);
        dest.writeInt(this.wheezing);
        dest.writeInt(this.swallowingDifficulty);
        dest.writeInt(this.clubbingFingerNails);
        dest.writeInt(this.frequentColds);
        dest.writeInt(this.dryCough);
        dest.writeInt(this.snoring);
        dest.writeByte(this.hadPreviousCancer ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hadPreviousColonCancer ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasFamilyHistory ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isOldAge ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasIDB ? (byte) 1 : (byte) 0);
        dest.writeByte(this.exercisesRegularly ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasHighFatDiet ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasHadStroke ? (byte) 1 : (byte) 0);
        dest.writeByte(this.difficultyWalking ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasAsthma ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hadKidneyDisease ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hadPreviousSkinCancer ? (byte) 1 : (byte) 0);
        dest.writeString(this.diabetic);
        dest.writeString(this.generalHealth);
        dest.writeInt(this.sex);
        dest.writeString(this.race);
        dest.writeInt(this.sleepTime);
        dest.writeFloat(this.bmi);
        dest.writeInt(this.physicalHealth);
    }

    public void readFromParcel(Parcel source) {
        this.age = source.readInt();
        this.gender = source.readByte() != 0;
        this.isSmoker = source.readByte() != 0;
        this.doesDrinkAlcohol = source.readByte() != 0;
        this.hasObesity = source.readByte() != 0;
        this.physicalActivity = source.readByte() != 0;
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
        this.hadPreviousCancer = source.readByte() != 0;
        this.hadPreviousColonCancer = source.readByte() != 0;
        this.hasFamilyHistory = source.readByte() != 0;
        this.isOldAge = source.readByte() != 0;
        this.hasIDB = source.readByte() != 0;
        this.exercisesRegularly = source.readByte() != 0;
        this.hasHighFatDiet = source.readByte() != 0;
        this.hasHadStroke = source.readByte() != 0;
        this.difficultyWalking = source.readByte() != 0;
        this.hasAsthma = source.readByte() != 0;
        this.hadKidneyDisease = source.readByte() != 0;
        this.hadPreviousSkinCancer = source.readByte() != 0;
        this.diabetic = source.readString();
        this.generalHealth = source.readString();
        this.sex = (char) source.readInt();
        this.race = source.readString();
        this.sleepTime = source.readInt();
        this.bmi = source.readFloat();
        this.physicalHealth = source.readInt();
    }

    protected Form(Parcel in) {
        this.age = in.readInt();
        this.gender = in.readByte() != 0;
        this.isSmoker = in.readByte() != 0;
        this.doesDrinkAlcohol = in.readByte() != 0;
        this.hasObesity = in.readByte() != 0;
        this.physicalActivity = in.readByte() != 0;
        this.airPollution = in.readInt();
        this.alcoholUse = in.readInt();
        this.dustAllergies = in.readInt();
        this.occupationalHazards = in.readInt();
        this.geneticRisk = in.readInt();
        this.chronicLungDisease = in.readInt();
        this.balancedDiet = in.readInt();
        this.obesity = in.readInt();
        this.smoking = in.readInt();
        this.passiveSmoker = in.readInt();
        this.chestPain = in.readInt();
        this.coughing = in.readInt();
        this.fatigue = in.readInt();
        this.weightLoss = in.readInt();
        this.shortnessOfBreath = in.readInt();
        this.wheezing = in.readInt();
        this.swallowingDifficulty = in.readInt();
        this.clubbingFingerNails = in.readInt();
        this.frequentColds = in.readInt();
        this.dryCough = in.readInt();
        this.snoring = in.readInt();
        this.hadPreviousCancer = in.readByte() != 0;
        this.hadPreviousColonCancer = in.readByte() != 0;
        this.hasFamilyHistory = in.readByte() != 0;
        this.isOldAge = in.readByte() != 0;
        this.hasIDB = in.readByte() != 0;
        this.exercisesRegularly = in.readByte() != 0;
        this.hasHighFatDiet = in.readByte() != 0;
        this.hasHadStroke = in.readByte() != 0;
        this.difficultyWalking = in.readByte() != 0;
        this.hasAsthma = in.readByte() != 0;
        this.hadKidneyDisease = in.readByte() != 0;
        this.hadPreviousSkinCancer = in.readByte() != 0;
        this.diabetic = in.readString();
        this.generalHealth = in.readString();
        this.sex = (char) in.readInt();
        this.race = in.readString();
        this.sleepTime = in.readInt();
        this.bmi = in.readFloat();
        this.physicalHealth = in.readInt();
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
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isSmoker() {
        return isSmoker;
    }

    public void setSmoker(boolean smoker) {
        isSmoker = smoker;
    }

    public boolean isDoesDrinkAlcohol() {
        return doesDrinkAlcohol;
    }

    public void setDoesDrinkAlcohol(boolean doesDrinkAlcohol) {
        this.doesDrinkAlcohol = doesDrinkAlcohol;
    }

    public boolean isHasObesity() {
        return hasObesity;
    }

    public void setHasObesity(boolean hasObesity) {
        this.hasObesity = hasObesity;
    }

    public boolean isPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(boolean physicalActivity) {
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

    public boolean isHadPreviousCancer() {
        return hadPreviousCancer;
    }

    public void setHadPreviousCancer(boolean hadPreviousCancer) {
        this.hadPreviousCancer = hadPreviousCancer;
    }

    public boolean isHadPreviousColonCancer() {
        return hadPreviousColonCancer;
    }

    public void setHadPreviousColonCancer(boolean hadPreviousColonCancer) {
        this.hadPreviousColonCancer = hadPreviousColonCancer;
    }

    public boolean isHasFamilyHistory() {
        return hasFamilyHistory;
    }

    public void setHasFamilyHistory(boolean hasFamilyHistory) {
        this.hasFamilyHistory = hasFamilyHistory;
    }

    public boolean isOldAge() {
        return isOldAge;
    }

    public void setOldAge(boolean oldAge) {
        isOldAge = oldAge;
    }

    public boolean isHasIDB() {
        return hasIDB;
    }

    public void setHasIDB(boolean hasIDB) {
        this.hasIDB = hasIDB;
    }

    public boolean isExercisesRegularly() {
        return exercisesRegularly;
    }

    public void setExercisesRegularly(boolean exercisesRegularly) {
        this.exercisesRegularly = exercisesRegularly;
    }

    public boolean isHasHighFatDiet() {
        return hasHighFatDiet;
    }

    public void setHasHighFatDiet(boolean hasHighFatDiet) {
        this.hasHighFatDiet = hasHighFatDiet;
    }

    public boolean isHasHadStroke() {
        return hasHadStroke;
    }

    public void setHasHadStroke(boolean hasHadStroke) {
        this.hasHadStroke = hasHadStroke;
    }

    public boolean isDifficultyWalking() {
        return difficultyWalking;
    }

    public void setDifficultyWalking(boolean difficultyWalking) {
        this.difficultyWalking = difficultyWalking;
    }

    public boolean isHasAsthma() {
        return hasAsthma;
    }

    public void setHasAsthma(boolean hasAsthma) {
        this.hasAsthma = hasAsthma;
    }

    public boolean isHadKidneyDisease() {
        return hadKidneyDisease;
    }

    public void setHadKidneyDisease(boolean hadKidneyDisease) {
        this.hadKidneyDisease = hadKidneyDisease;
    }

    public boolean isHadPreviousSkinCancer() {
        return hadPreviousSkinCancer;
    }

    public void setHadPreviousSkinCancer(boolean hadPreviousSkinCancer) {
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

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
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

    public static final Parcelable.Creator<Form> CREATOR = new Parcelable.Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel source) {
            return new Form(source);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };
}
