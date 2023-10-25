import pandas as pd
import numpy as np

np.set_printoptions(precision=3, suppress=True)

import tensorflow as tf
from tensorflow.keras import layers


def split_features(dataset, categorical_columns, numerical_columns):
    feature_columns = []
    vocabulary = []
    for feature in categorical_columns:
        vocabulary = dataset[feature].unique()
        feature_columns.append(tf.feature_column.categorical_column_with_vocabulary_list(feature, vocabulary))
    for feature in numerical_columns:
        feature_columns.append(tf.feature_column.numeric_column(feature, dtype=tf.float32))

    return vocabulary, feature_columns


def split_train_test(split_percentage, dataset, label):
    train = dataset.sample(frac=split_percentage, random_state=0)
    X_train = train.drop(columns=[label])
    y_train = train[label]
    test = dataset.drop(train.index)
    X_test = test.drop(columns=[label])
    y_test = test[label]

    return X_train, X_test, y_train, y_test


def make_input_function(data_df, label_df, num_epochs, shuffle=True, batch_size=32):
    def input_function():
        ds = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))
        if shuffle:
            ds = ds.shuffle(1000)
        ds = ds.batch(batch_size).repeat(num_epochs)
        return ds
    return input_function

cc_dataset = pd.read_csv("cc_dataset.csv")
CC_DATASET_CATCOL = []
CC_DATASET_NUMCOL = ['hasColonCancer', 'hadPreviousCancer', 'hadPreviousColonCancer', 'hasFamilyHistory',
                     'hadRadiationTherapy', 'isOldAge', 'hasIBD', 'hasObesity', 'isSmoker', 'isDrinker',
                     'exercisesRegularly', 'hasHighFatDiet']

hd_dataset = pd.read_csv("hd_dataset.csv")
HD_DATASET_CATCOL = ['HeartDisease', 'Smoking', 'AlcoholDrinking', 'Stroke', 'DiffWalking', 'Sex', 'AgeCategory',
                     'Race', 'Diabetic', 'PhysicalActivity', 'GenHealth', 'Asthma', 'KidneyDisease', 'SkinCancer']
HD_DATASET_NUMCOL = ['BMI', 'PhysicalHealth', 'MentalHealth', 'SleepTime']

lc_dataset = pd.read_csv("lc_dataset.csv")
LC_DATASET_CATCOL = ['Level']
LC_DATASET_NUMCOL = ['Age', 'Gender', 'Air Pollution', 'Alcohol use', 'Dust Allergy', 'OccuPational Hazards',
                     'Genetic Risk', 'chronic Lung Disease', 'Balanced Diet', 'Obesity', 'Smoking', 'Passive Smoker',
                     'Chest Pain', 'Coughing of Blood', 'Fatigue', 'Weight Loss', 'Shortness of Breath', 'Wheezing',
                     'Swallowing Difficulty', 'Clubbing of Finger Nails', 'Frequent Cold', 'Dry Cough', 'Snoring']


# Colon Cancer Dataset
cc_vocabulary, cc_features = split_features(cc_dataset, CC_DATASET_CATCOL, CC_DATASET_NUMCOL)
cc_labels = cc_dataset['hasColonCancer']

cc_X_train, cc_X_test, cc_y_train, cc_y_test = split_train_test(0.8, cc_dataset, 'hasColonCancer')
print(cc_X_train.head(), cc_y_train.head())


# Heart Disease Dataset
hd_vocabulary, hd_features = split_features(hd_dataset, HD_DATASET_CATCOL, HD_DATASET_NUMCOL)
hd_labels = hd_dataset['HeartDisease']

hd_X_train, hd_X_test, hd_y_train, hd_y_test = split_train_test(0.8, hd_dataset, 'HeartDisease')

# Liver Cancer Dataset
lc_dataset = lc_dataset.drop(columns=['Patient Id', 'index'])
lc_vocabulary, lc_features = split_features(lc_dataset, LC_DATASET_CATCOL, LC_DATASET_NUMCOL)
lc_labels = lc_dataset['Level']

lc_X_train, lc_X_test, lc_y_train, lc_y_test = split_train_test(0.8, lc_dataset, 'Level')