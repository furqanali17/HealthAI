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

    // Dataset3
    // commons: smoker, drinker, age,  activity
    // common derived: sex

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
                           boolean hadPreviousCancer){
        this.hasHighFatDiet = (hasHighFatDiet) ? 1 : 0;
        this.hasIDB = (hasIDB) ? 1 : 0;
        this.hasFamilyHistory = (hasFamilyHistory) ? 1 : 0;
        this.hadPreviousColonCancer= (hadPreviousColonCancer) ? 1:0;
        this.hadPreviousCancer = (hadPreviousCancer) ?1:0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeInt(this.gender);
        dest.writeInt(this.isSmoker);
        dest.writeInt(this.doesDrinkAlcohol);
        dest.writeInt(this.hasObesity);
        dest.writeInt(this.physicalActivity);
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
        dest.writeInt(this.hadPreviousCancer);
        dest.writeInt(this.hadPreviousColonCancer);
        dest.writeInt(this.hasFamilyHistory);
        dest.writeInt(this.isOldAge);
        dest.writeInt(this.hasIDB);
        dest.writeInt(this.exercisesRegularly);
        dest.writeInt(this.hasHighFatDiet);
        dest.writeInt(this.hasHadStroke);
        dest.writeInt(this.difficultyWalking);
        dest.writeInt(this.hasAsthma);
        dest.writeInt(this.hadKidneyDisease);
        dest.writeInt(this.hadPreviousSkinCancer);
        dest.writeString(this.diabetic);
        dest.writeString(this.generalHealth);
        dest.writeString(this.race);
        dest.writeInt(this.sleepTime);
        dest.writeFloat(this.bmi);
        dest.writeInt(this.physicalHealth);
        dest.writeInt(this.mentalHealth);
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

    protected Form(Parcel in) {
        this.age = in.readInt();
        this.gender = in.readInt();
        this.isSmoker = in.readInt();
        this.doesDrinkAlcohol = in.readInt();
        this.hasObesity = in.readInt();
        this.physicalActivity = in.readInt();
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
        this.hadPreviousCancer = in.readInt();
        this.hadPreviousColonCancer = in.readInt();
        this.hasFamilyHistory = in.readInt();
        this.isOldAge = in.readInt();
        this.hasIDB = in.readInt();
        this.exercisesRegularly = in.readInt();
        this.hasHighFatDiet = in.readInt();
        this.hasHadStroke = in.readInt();
        this.difficultyWalking = in.readInt();
        this.hasAsthma = in.readInt();
        this.hadKidneyDisease = in.readInt();
        this.hadPreviousSkinCancer = in.readInt();
        this.diabetic = in.readString();
        this.generalHealth = in.readString();
        this.race = in.readString();
        this.sleepTime = in.readInt();
        this.bmi = in.readFloat();
        this.physicalHealth = in.readInt();
        this.mentalHealth = in.readInt();
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
