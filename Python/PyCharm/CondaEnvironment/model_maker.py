import os
import shutil
import pandas as pd
import tensorflow as tf
tf.compat.v1.logging.set_verbosity(tf.compat.v1.logging.ERROR)

def split_features(columns):
    feature_columns = []

    for feature in columns:
        feature_columns.append(tf.feature_column.numeric_column(feature, dtype=tf.float32))

    return feature_columns


def split_train_test(split_percentage, dataset, label):
    X_train = dataset.sample(frac=split_percentage, random_state=0)
    y_train = X_train.pop(label)
    X_test = dataset.drop(X_train.index)
    y_test = X_test.pop(label)

    return X_train, X_test, y_train, y_test


def make_input_function(data_df, label_df, training=True, batch_size=256):
    dataset = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))

    if training:
        dataset = dataset.shuffle(1000).repeat()

    return dataset.batch(batch_size)


def predict(features, classifier):
    def predict_input_function(features, batch_size=256):
        return tf.data.Dataset.from_tensor_slices(dict(features)).batch(batch_size)

    patient_to_predict = {}
    for feature in features:
        value = input("Introduce value for " + feature + ": ")
        patient_to_predict[feature] = [float(value)]

    predictions = classifier.predict(input_fn=lambda: predict_input_function(patient_to_predict))

    for prediction in predictions:
        outcome = prediction['class_ids'][0]
        probability = prediction['probabilities'][outcome]

        return outcome, probability


def make_model(dataset, features, label, split_percentage=0.8, n_classes=2):


    X_train, X_test, y_train, y_test = split_train_test(split_percentage, dataset, label)

    classifier = tf.estimator.DNNClassifier(
        feature_columns=features,
        hidden_units=[30, 10],
        n_classes=n_classes
    )

    classifier.train(input_fn=lambda: make_input_function(X_train, y_train), steps=10000)
    evaluation_result = classifier.evaluate(input_fn=lambda: make_input_function(X_train, y_train, training=False))

    return classifier, evaluation_result


if __name__ == '__main__':
    cc_dataset = pd.read_csv("cc_dataset.csv")
    CC_DATASET_COLS = ['hasColonCancer', 'hadPreviousCancer', 'hadPreviousColonCancer', 'hasFamilyHistory',
                         'hadRadiationTherapy', 'isOldAge', 'hasIBD', 'hasObesity', 'isSmoker', 'isDrinker',
                         'exercisesRegularly', 'hasHighFatDiet']
    CC_FEATURES = list(CC_DATASET_COLS)
    CC_FEATURES.remove('hasColonCancer')

    hd_dataset = pd.read_csv("hd_dataset.csv")
    HD_DATASET_COLS = ['HeartDisease', 'BMI', 'PhysicalHealth', 'MentalHealth', 'SleepTime', 'Smoking', 'AlcoholDrinking', 'Stroke', 'DiffWalking', 'Sex', 'AgeCategory',
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

    # Colon Cancer Dataset
    cc_ftr_cols = split_features(CC_FEATURES)
    cc_classifier, cc_evaluation_result= make_model(cc_dataset, cc_ftr_cols, 'hasColonCancer')
    print('\nCC test set accuracy: {accuracy:0.3f}\n'.format(**cc_evaluation_result))

    # Heart Disease Dataset
    hd_ftr_cols = split_features(HD_FEATURES)
    hd_classifier, hd_evaluation_result = make_model(hd_dataset, hd_ftr_cols, 'HeartDisease')
    print('\nHD test set accuracy: {accuracy:0.3f}\n'.format(**hd_evaluation_result))

    # Liver Cancer Dataset
    lc_ftr_cols = split_features(LC_FEATURES)
    lc_classifier, lc_evaluation_result = make_model(lc_dataset, lc_ftr_cols, 'Level', n_classes=3)
    print('\nLC test set accuracy: {accuracy:0.3f}\n'.format(**lc_evaluation_result))

    # Predict CC
    cc_outcome, cc_probability = predict(CC_FEATURES, cc_classifier)
    print(f'Model predicts: "{cc_outcome}" ({cc_probability * 100:.1f}%)')

    # Predict HD
    hd_outcome, hd_probability = predict(HD_FEATURES, hd_classifier)
    print(f'Model predicts: "{hd_outcome}" ({hd_probability * 100:.1f}%)')

    # Predict LC
    lc_outcome, lc_probability = predict(LC_FEATURES, lc_classifier)
    print(f'Model predicts: "{lc_outcome}" ({lc_probability * 100:.1f}%)')

    # Save Models
    MODELS_DIRECTORY = 'models'
    shutil.rmtree(MODELS_DIRECTORY, ignore_errors=True)
    cc_save_input_fn = tf.estimator.export.build_parsing_serving_input_receiver_fn(tf.feature_column.make_parse_example_spec(cc_ftr_cols))
    hd_save_input_fn = tf.estimator.export.build_parsing_serving_input_receiver_fn(tf.feature_column.make_parse_example_spec(hd_ftr_cols))
    lc_save_input_fn = tf.estimator.export.build_parsing_serving_input_receiver_fn(tf.feature_column.make_parse_example_spec(lc_ftr_cols))

    cc_path = cc_classifier.export_saved_model(os.path.join(MODELS_DIRECTORY, "cc_model"), cc_save_input_fn)
    hd_path = hd_classifier.export_saved_model(os.path.join(MODELS_DIRECTORY, "hd_model"), hd_save_input_fn)
    lc_path = lc_classifier.export_saved_model(os.path.join(MODELS_DIRECTORY, "lc_model"), lc_save_input_fn)

    # Load Models
    cc_classifier = tf.saved_model.load(cc_path)
    hd_classifier = tf.saved_model.load(hd_path)
    lc_classifier = tf.saved_model.load(lc_path)