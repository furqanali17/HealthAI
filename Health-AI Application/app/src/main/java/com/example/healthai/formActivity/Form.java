package com.example.healthai.formActivity;

public class Form {

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
    }

}
