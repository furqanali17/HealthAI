import pandas as pd
import tensorflow as tf
tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.ERROR)

def split_features(dataset, columns):
    feature_columns = []

    for feature in columns:
        feature_columns.append(tf.feature_column.numeric_column(feature, dtype=tf.float32))

    return feature_columns


def split_train_test(split_percentage, dataset, label):
    X_train = dataset.sample(frac=split_percentage, random_state=0)
    y_train = X_train[label]
    X_test = dataset.drop(X_train.index)
    y_test = X_test[label]

    return X_train, X_test, y_train, y_test


def make_input_function(data_df, label_df, training=True, batch_size=256):
    ds = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))

    if training:
        ds = ds.shuffle(1000).repeat()

    return ds.batch(batch_size)

cc_dataset = pd.read_csv("cc_dataset.csv")
CC_DATASET_COLS = ['hasColonCancer', 'hadPreviousCancer', 'hadPreviousColonCancer', 'hasFamilyHistory',
                     'hadRadiationTherapy', 'isOldAge', 'hasIBD', 'hasObesity', 'isSmoker', 'isDrinker',
                     'exercisesRegularly', 'hasHighFatDiet']

hd_dataset = pd.read_csv("hd_dataset.csv")
HD_DATASET_COLS = ['HeartDisease', 'BMI', 'PhysicalHealth', 'MentalHealth', 'SleepTime', 'Smoking', 'AlcoholDrinking', 'Stroke', 'DiffWalking', 'Sex', 'AgeCategory',
                     'Race', 'Diabetic', 'PhysicalActivity', 'GenHealth', 'Asthma', 'KidneyDisease', 'SkinCancer']

lc_dataset = pd.read_csv("lc_dataset.csv")
LC_DATASET_COLS = ['Level', 'Age', 'Gender', 'AirPollution', 'AlcoholUse', 'DustAllergy', 'OccupationalHazards',
                     'GeneticRisk', 'ChronicLungDisease', 'BalancedDiet', 'Obesity', 'Smoking', 'PassiveSmoker',
                     'ChestPain', 'CoughingofBlood', 'Fatigue', 'WeightLoss', 'ShortnessofBreath', 'Wheezing',
                     'SwallowingDifficulty', 'ClubbingofFingerNails', 'FrequentCold', 'DryCough', 'Snoring']


# Colon Cancer Dataset
cc_features = split_features(cc_dataset, CC_DATASET_COLS)
cc_labels = cc_dataset['hasColonCancer']

cc_X_train, cc_X_test, cc_y_train, cc_y_test = split_train_test(0.5, cc_dataset, 'hasColonCancer')

cc_classifier = tf.estimator.DNNClassifier(
    feature_columns=cc_features,
    hidden_units=[30,10],
    n_classes=2
)

cc_classifier.train(input_fn=lambda: make_input_function(cc_X_train, cc_y_train), steps=10000)

cc_eval_result = cc_classifier.evaluate(input_fn=lambda: make_input_function(cc_X_train, cc_y_train, training=False))

print('\nCC Test set accuracy: {accuracy:0.3f}\n'.format(**cc_eval_result))


# Heart Disease Dataset
hd_features = split_features(hd_dataset, HD_DATASET_COLS)
hd_labels = hd_dataset['HeartDisease']

hd_X_train, hd_X_test, hd_y_train, hd_y_test = split_train_test(0.8, hd_dataset, 'HeartDisease')

hd_classifier = tf.estimator.DNNClassifier(
    feature_columns=hd_features,
    hidden_units=[30,10],
    n_classes=2
)

hd_classifier.train(input_fn=lambda: make_input_function(hd_X_train, hd_y_train), steps=10000)

hd_eval_result = hd_classifier.evaluate(input_fn=lambda: make_input_function(hd_X_train, hd_y_train, training=False))

print('\nHD Test set accuracy: {accuracy:0.3f}\n'.format(**hd_eval_result))


# Liver Cancer Dataset

lc_features = split_features(lc_dataset, LC_DATASET_COLS)
lc_labels = lc_dataset['Level']

lc_X_train, lc_X_test, lc_y_train, lc_y_test = split_train_test(0.8, lc_dataset, 'Level')

lc_classifier = tf.estimator.DNNClassifier(
    feature_columns=lc_features,
    hidden_units=[30,10],
    n_classes=3
)

lc_classifier.train(input_fn=lambda: make_input_function(lc_X_train, lc_y_train), steps=10000)

lc_eval_result = lc_classifier.evaluate(input_fn=lambda: make_input_function(lc_X_train, lc_y_train, training=False))

print('\nLC Test set accuracy: {accuracy:0.3f}\n'.format(**lc_eval_result))