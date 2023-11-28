import pandas as pd
import numpy as np

np.set_printoptions(precision=3, suppress=True)

import tensorflow as tf
from keras import layers
from sklearn.model_selection import train_test_split

if __name__ == '__main__':
    cc_dataset = pd.read_csv("cc_dataset.csv")
    CC_DATASET_COLS = ['hasColonCancer', 'hadPreviousCancer', 'hadPreviousColonCancer', 'hasFamilyHistory',
                       'hadRadiationTherapy', 'isOldAge', 'hasIBD', 'hasObesity', 'isSmoker', 'isDrinker',
                       'exercisesRegularly', 'hasHighFatDiet']
    CC_LABEL_COL = 'hasColonCancer'
    CC_FEATURES = list(CC_DATASET_COLS)
    CC_FEATURES.remove(CC_LABEL_COL)

    hd_dataset = pd.read_csv("hd_dataset.csv")
    HD_DATASET_COLS = ['HeartDisease', 'BMI', 'PhysicalHealth', 'MentalHealth', 'SleepTime', 'Smoking',
                       'AlcoholDrinking', 'Stroke', 'DiffWalking', 'Sex', 'AgeCategory',
                       'Race', 'Diabetic', 'PhysicalActivity', 'GenHealth', 'Asthma', 'KidneyDisease', 'SkinCancer']
    HD_FEATURES = list(HD_DATASET_COLS)
    HD_FEATURES.remove('HeartDisease')

    lc_dataset = pd.read_csv("lc_dataset.csv")
    LC_DATASET_COLS = ['Level', 'Age', 'Gender', 'AirPollution', 'AlcoholUse', 'DustAllergy', 'OccupationalHazards',
                       'GeneticRisk', 'ChronicLungDisease', 'BalancedDiet', 'Obesity', 'Smoking', 'PassiveSmoker',
                       'ChestPain', 'CoughingofBlood', 'Fatigue', 'WeightLoss', 'ShortnessofBreath', 'Wheezing',
                       'SwallowingDifficulty', 'ClubbingofFingerNails', 'FrequentCold', 'DryCough', 'Snoring']
    LC_FEATURES = list(LC_DATASET_COLS)
    LC_FEATURES.remove('Level')

    x = cc_dataset
    y = cc_dataset[CC_LABEL_COL]
    X_train, X_test, y_train, y_test = train_test_split(x, y, train_size=0.8)

    cc_model = tf.keras.Sequential([layers.Dense(64), layers.Dense(1)])
    cc_model.compile(loss = tf.keras.losses.MeanSquaredError(),
                      optimizer = tf.keras.optimizers.Adam())

    cc_model.fit(X_train, y_train, epochs=10)
