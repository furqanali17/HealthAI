import pandas as pd
import tensorflow as tf
tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.ERROR)

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
    X_train = dataset.sample(frac=split_percentage, random_state=0)
    y_train = X_train[label]
    X_test = dataset.drop(X_train.index)
    y_test = X_test[label]

    return X_train, X_test, y_train, y_test


# Linear Classifier Input Function
def make_input_function(data_df, label_df, num_epochs=10, shuffle=True, batch_size=32):
    def input_function():
        ds = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))
        if shuffle:
            ds = ds.shuffle(1000)
        ds = ds.batch(batch_size).repeat(num_epochs)
        return ds
    return input_function

def make_input_function_2(data_df, label_df, training=True, batch_size=256):
    ds = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))

    if training:
        ds = ds.shuffle(1000).repeat()

    return ds.batch(batch_size)

cc_dataset = pd.read_csv("cc_dataset.csv")
CC_DATASET_CATCOL = []
CC_DATASET_NUMCOL = ['hasColonCancer', 'hadPreviousCancer', 'hadPreviousColonCancer', 'hasFamilyHistory',
                     'hadRadiationTherapy', 'isOldAge', 'hasIBD', 'hasObesity', 'isSmoker', 'isDrinker',
                     'exercisesRegularly', 'hasHighFatDiet']

hd_dataset = pd.read_csv("hd_dataset.csv")
hd_dataset['HeartDisease'] = hd_dataset['HeartDisease'].replace('No', 0).replace('Yes', 1)
HD_DATASET_CATCOL = ['Smoking', 'AlcoholDrinking', 'Stroke', 'DiffWalking', 'Sex', 'AgeCategory',
                     'Race', 'Diabetic', 'PhysicalActivity', 'GenHealth', 'Asthma', 'KidneyDisease', 'SkinCancer']
HD_DATASET_NUMCOL = ['HeartDisease', 'BMI', 'PhysicalHealth', 'MentalHealth', 'SleepTime']

lc_dataset = pd.read_csv("lc_dataset.csv")
lc_dataset = lc_dataset.drop(columns=['Index', 'Patient ID'])
lc_dataset['Level'] = lc_dataset['Level'].replace('Low', 0).replace('Medium', 0.5).replace('High', 1)
LC_DATASET_CATCOL = []
LC_DATASET_NUMCOL = ['Level', 'Age', 'Gender', 'Air Pollution', 'Alcohol Use', 'Dust Allergy', 'Occupational Hazards',
                     'Genetic Risk', 'Chronic Lung Disease', 'Balanced Diet', 'Obesity', 'Smoking', 'Passive Smoker',
                     'Chest Pain', 'Coughing of Blood', 'Fatigue', 'Weight Loss', 'Shortness of Breath', 'Wheezing',
                     'Swallowing Difficulty', 'Clubbing of Finger Nails', 'Frequent Cold', 'Dry Cough', 'Snoring']


# Colon Cancer Dataset
cc_vocabulary, cc_features = split_features(cc_dataset, CC_DATASET_CATCOL, CC_DATASET_NUMCOL)
cc_labels = cc_dataset['hasColonCancer']

cc_X_train, cc_X_test, cc_y_train, cc_y_test = split_train_test(0.5, cc_dataset, 'hasColonCancer')

cc_classifier = tf.estimator.DNNClassifier(
    feature_columns=cc_features,
    hidden_units=[30,10],
    n_classes=2
)

cc_classifier.train(input_fn=lambda: make_input_function_2(cc_X_train, cc_y_train), steps=10000)

cc_eval_result = cc_classifier.evaluate(input_fn=lambda: make_input_function_2(cc_X_train, cc_y_train, training=False))

print('\nTest set accuracy: {accuracy:0.3f}\n'.format(**cc_eval_result))

#
# cc_train_input_fn = make_input_function(cc_X_train, cc_y_train)
# cc_eval_input_fn = make_input_function(cc_X_test, cc_y_test, num_epochs=1, shuffle=False)
#
# cc_linear_estimate = tf.estimator.LinearClassifier(feature_columns=cc_features)
#
# cc_linear_estimate.train(cc_train_input_fn)
# cc_result = cc_linear_estimate.evaluate(cc_eval_input_fn)
#
# print('Colon Cancer Accuracy:', cc_result['accuracy'])
# print(list(cc_linear_estimate.predict(cc_eval_input_fn))[0]['probabilities'])
#
# Heart Disease Dataset
hd_vocabulary, hd_features = split_features(hd_dataset, HD_DATASET_CATCOL, HD_DATASET_NUMCOL)
hd_labels = hd_dataset['HeartDisease']

hd_X_train, hd_X_test, hd_y_train, hd_y_test = split_train_test(0.8, hd_dataset, 'HeartDisease')

hd_classifier = tf.estimator.DNNClassifier(
    feature_columns=hd_features,
    hidden_units=[30,10],
    n_classes=2
)

hd_classifier.train(input_fn=lambda: make_input_function_2(hd_X_train, hd_y_train), steps=10000)

hd_eval_result = hd_classifier.evaluate(input_fn=lambda: make_input_function_2(hd_X_train, hd_y_train, training=False))

print('\nTest set accuracy: {accuracy:0.3f}\n'.format(**hd_eval_result))

# hd_train_input_fn = make_input_function(hd_X_train, hd_y_train)
# hd_eval_input_fn = make_input_function(hd_X_test, hd_y_test, num_epochs=1, shuffle=False)
#
# hd_linear_estimate = tf.estimator.LinearClassifier(feature_columns=hd_features)
#
# hd_linear_estimate.train(hd_train_input_fn)
# hd_result = hd_linear_estimate.evaluate(hd_eval_input_fn)
#
# print('Heart Disease Accuracy:', hd_result['accuracy'])
#
#
# # Liver Cancer Dataset
# lc_vocabulary, lc_features = split_features(lc_dataset, LC_DATASET_CATCOL, LC_DATASET_NUMCOL)
# lc_labels = lc_dataset['Level']
#
# lc_X_train, lc_X_test, lc_y_train, lc_y_test = split_train_test(0.8, lc_dataset, 'Level')
#
# lc_train_input_fn = make_input_function(lc_X_train, lc_y_train)
# lc_eval_input_fn = make_input_function(lc_X_test, lc_y_test, num_epochs=1, shuffle=False)
#
# lc_linear_estimate = tf.estimator.LinearClassifier(feature_columns=lc_features)
#
# lc_linear_estimate.train(lc_train_input_fn)
# lc_result = lc_linear_estimate.evaluate(lc_eval_input_fn)
#
# print('Lung Cancer Accuracy:', lc_result['accuracy'])