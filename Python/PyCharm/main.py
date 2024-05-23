import numpy as np
import pandas as pd
from sklearn.model_selection import KFold
from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import OneHotEncoder
from sklearn.linear_model import Perceptron
from sklearn.metrics import accuracy_score
import pickle
import firebase_admin as fb_a
from firebase_admin import credentials, db


def read_datasets():
    hd_df = pd.read_csv("Datasets/hd_dataset.csv")
    hd_X, hd_y = hd_df.drop(columns='HeartDisease'), hd_df['HeartDisease']

    lc_df = pd.read_csv("Datasets/lc_dataset.csv")
    lc_X, lc_y = lc_df.drop(columns=['index', 'Patient Id', 'Level']), lc_df['Level']

    cc_df = pd.read_csv("Datasets/cc_dataset.csv")
    cc_X, cc_y = cc_df.drop(columns='hasColonCancer'), cc_df['hasColonCancer']

    yn_map = {'Yes': 1, 'No': 0}
    yn_cols = ['Smoking', 'AlcoholDrinking', 'Stroke', 'DiffWalking', 'PhysicalActivity', 'Asthma', 'KidneyDisease',
               'SkinCancer']

    hd_X[yn_cols] = hd_X[yn_cols].replace(yn_map)
    hd_X['Sex'] = hd_X['Sex'].replace({'Male': 0, 'Female': 1})

    return hd_X, hd_y, lc_X, lc_y, cc_X, cc_y


def split_categories(features):
    cat_features = features.select_dtypes(include=['object', 'bool'])
    num_features = features.select_dtypes(include=['int64', 'float64'])
    return cat_features, num_features


def create_scalers(hd_num, lc_num, cc_num):
    hd_mms = MinMaxScaler()

    hd_mms.fit(hd_num)
    num_portion = hd_mms.transform(hd_num)
    num_feature_names = hd_mms.get_feature_names_out(hd_num.columns)

    lc_mms = MinMaxScaler()

    lc_mms.fit(lc_num)
    num_portion = lc_mms.transform(lc_num)
    num_feature_names = lc_mms.get_feature_names_out(lc_num.columns)

    cc_mms = MinMaxScaler()

    cc_mms.fit(cc_num)
    num_portion = cc_mms.transform(cc_num)
    num_feature_names = cc_mms.get_feature_names_out(cc_num.columns)

    return hd_mms, lc_mms, cc_mms


def transform_df(cat_cols, num_cols):
    ohe = OneHotEncoder()
    mms = MinMaxScaler()

    ohe.fit(cat_cols)
    cat_portion = ohe.transform(cat_cols).toarray()
    cat_feature_names = ohe.get_feature_names_out(cat_cols.columns)

    mms.fit(num_cols)
    num_portion = mms.transform(num_cols)
    num_feature_names = mms.get_feature_names_out(num_cols.columns)

    result = pd.concat([pd.DataFrame(cat_portion, columns=cat_feature_names).astype(int),
                        pd.DataFrame(num_portion, columns=num_feature_names).astype(float)], axis=1)

    return result


def train_perceptron(X, y, n_splits=10, shuffle=True):
    kf = KFold(n_splits=n_splits, shuffle=shuffle)
    model = Perceptron()
    accuracy_scores = []

    for train_index, test_index in kf.split(X):
        X_train, X_test = X.iloc[train_index], X.iloc[test_index]
        y_train, y_test = y.iloc[train_index], y.iloc[test_index]

        model.fit(X_train, y_train)

        y_pred = model.predict(X_test)

        accuracy = accuracy_score(y_test, y_pred)
        accuracy_scores.append(accuracy)

    mean_scores = np.mean(accuracy_scores)

    return model, mean_scores


def find_best_n_splits(X, y):
    best_accuracy = 0
    best_n_splits = None
    best_model = None
    for i in range(2, 10):
        current_model, current_accuracy = train_perceptron(X, y, n_splits=i)
        if current_accuracy > best_accuracy:
            best_n_splits = i
            best_accuracy = current_accuracy
            best_model = current_model

    return best_accuracy, best_model

    print(f'Best number of splits for current model: {best_n_splits}')


def save_models(hd_model, lc_model, cc_model):
    hd_filename = 'hd_model.sav'
    lc_filename = 'lc_model.sav'
    cc_filename = 'cc_model.sav'

    pickle.dump(hd_model, open(hd_filename, 'wb'))
    pickle.dump(lc_model, open(lc_filename, 'wb'))
    pickle.dump(cc_model, open(cc_filename, 'wb'))


def save_scalers(hd_scaler, lc_scaler, cc_scaler):
    hd_filename = 'hd_scaler.sav'
    lc_filename = 'lc_scaler.sav'
    cc_filename = 'cc_scaler.sav'

    pickle.dump(hd_scaler, open(hd_filename, 'wb'))
    pickle.dump(lc_scaler, open(lc_filename, 'wb'))
    pickle.dump(cc_scaler, open(cc_filename, 'wb'))


def load_models():
    hd_filename = 'hd_model.sav'
    lc_filename = 'lc_model.sav'
    cc_filename = 'cc_model.sav'

    hd_model = pickle.load(open(hd_filename, 'rb'))
    lc_model = pickle.load(open(lc_filename, 'rb'))
    cc_model = pickle.load(open(cc_filename, 'rb'))

    return hd_model, lc_model, cc_model


def load_scalers():
    hd_filename = 'hd_scaler.sav'
    lc_filename = 'lc_scaler.sav'
    cc_filename = 'cc_scaler.sav'

    hd_scaler = pickle.load(open(hd_filename, 'rb'))
    lc_scaler = pickle.load(open(lc_filename, 'rb'))
    cc_scaler = pickle.load(open(cc_filename, 'rb'))

    return hd_scaler, lc_scaler, cc_scaler


def load_input(reference):
    cred = credentials.Certificate("dfos-healthai-firebase-adminsdk-aiz5j-a013f16cbc.json")
    fb_a.initialize_app(cred, {'databaseURL': 'https://dfos-healthai-default-rtdb.firebaseio.com/'})

    ref = db.reference(reference)
    data = ref.get()

    fb_a.delete_app(fb_a.get_app())

    return data


def make_predictables(form):
    hd_scaler, lc_scaler, cc_scaler = load_scalers()

    # Heart Disease
    ## HD Categoricals
    hd_cat_predictables = [0] * 28

    ### AgeCategory
    age = form['age']
    if age < 25:
        hd_cat_predictables[0] = 1
    elif age < 30:
        hd_cat_predictables[1] = 1
    elif age < 35:
        hd_cat_predictables[2] = 1
    elif age < 40:
        hd_cat_predictables[3] = 1
    elif age < 45:
        hd_cat_predictables[4] = 1
    elif age < 50:
        hd_cat_predictables[5] = 1
    elif age < 55:
        hd_cat_predictables[6] = 1
    elif age < 60:
        hd_cat_predictables[7] = 1
    elif age < 65:
        hd_cat_predictables[8] = 1
    elif age < 70:
        hd_cat_predictables[9] = 1
    elif age < 75:
        hd_cat_predictables[10] = 1
    elif age < 80:
        hd_cat_predictables[11] = 1
    else:  # 80+
        hd_cat_predictables[12] = 1

    ### Race
    race = form['race']
    if race == 'American Indian/Alaskan Native':
        hd_cat_predictables[13] = 1
    elif race == 'Asian':
        hd_cat_predictables[14] = 1
    elif race == 'Black':
        hd_cat_predictables[15] = 1
    elif race == 'Hispanic':
        hd_cat_predictables[16] = 1
    elif race == 'Other':
        hd_cat_predictables[17] = 1
    else:  # race == 'White'
        hd_cat_predictables[18] = 1

    ### Diabetic
    diabetic = form['diabetic']
    if diabetic == 'No':
        hd_cat_predictables[19] = 1
    elif diabetic == 'No, borderline diabetes':
        hd_cat_predictables[20] = 1
    elif diabetic == 'Yes':
        hd_cat_predictables[21] = 1
    else:  # diabetic == 'Yes (during pregnancy)'
        hd_cat_predictables[22] = 1

    ### Gen Health
    generalHealth = form['generalHealth']
    if generalHealth == 'Excellent':
        hd_cat_predictables[23] = 1
    elif generalHealth == 'Fair':
        hd_cat_predictables[24] = 1
    elif generalHealth == 'Good':
        hd_cat_predictables[25] = 1
    elif generalHealth == 'Poor':
        hd_cat_predictables[26] = 1
    else:  # generalHealth == 'Very Good'
        hd_cat_predictables[27] = 1

    hd_cat_cols = ['AgeCategory_18-24', 'AgeCategory_25-29', 'AgeCategory_30-34',
                   'AgeCategory_35-39', 'AgeCategory_40-44', 'AgeCategory_45-49',
                   'AgeCategory_50-54', 'AgeCategory_55-59', 'AgeCategory_60-64',
                   'AgeCategory_65-69', 'AgeCategory_70-74', 'AgeCategory_75-79',
                   'AgeCategory_80 or older', 'Race_American Indian/Alaskan Native',
                   'Race_Asian', 'Race_Black', 'Race_Hispanic', 'Race_Other', 'Race_White',
                   'Diabetic_No', 'Diabetic_No, borderline diabetes', 'Diabetic_Yes',
                   'Diabetic_Yes (during pregnancy)', 'GenHealth_Excellent',
                   'GenHealth_Fair', 'GenHealth_Good', 'GenHealth_Poor',
                   'GenHealth_Very good']

    hd_pred_cat = pd.DataFrame([dict(zip(hd_cat_cols, hd_cat_predictables))])

    ## HD Numericals
    hd_num_predictables = [0] * 13

    ### BMI
    hd_num_predictables[0] = form['bmi']
    ### Smoking
    hd_num_predictables[1] = form['isSmoker']
    ### AlcoholDrinking
    hd_num_predictables[2] = form['doesDrinkAlcohol']
    ### Stroke
    hd_num_predictables[3] = form['hasHadStroke']
    ### Physical Health
    hd_num_predictables[4] = form['physicalHealth']
    ### Mental Health
    hd_num_predictables[5] = form['mentalHealth']
    ### Difficulty Walking
    hd_num_predictables[6] = form['difficultyWalking']
    ### Sex
    hd_num_predictables[7] = form['gender']
    ### Physical Activity
    hd_num_predictables[8] = form['physicalActivity']
    ### Sleep Time
    hd_num_predictables[9] = form['sleepTime']
    ### Asthma
    hd_num_predictables[10] = form['hasAsthma']
    ### Kidney Disease
    hd_num_predictables[11] = form['hadKidneyDisease']
    ### Skin Cancer
    hd_num_predictables[12] = form['hadPreviousSkinCancer']

    hd_num_cols = ['BMI', 'Smoking', 'AlcoholDrinking', 'Stroke',
                   'PhysicalHealth', 'MentalHealth', 'DiffWalking', 'Sex',
                   'PhysicalActivity', 'SleepTime', 'Asthma', 'KidneyDisease',
                   'SkinCancer']

    hd_pred_num = pd.DataFrame([dict(zip(hd_num_cols, hd_num_predictables))])
    hd_pred_num = hd_scaler.transform(hd_pred_num)

    hd_predictable = pd.concat([hd_pred_cat, pd.DataFrame(hd_pred_num, columns=hd_num_cols)], axis=1)

    # Lung Cancer
    ## LC Numericals
    lc_num_predictables = [0] * 23

    lc_num_predictables[0] = form['age']
    lc_num_predictables[1] = form['gender']
    lc_num_predictables[2] = form['airPollution']
    lc_num_predictables[3] = form['alcoholUse']
    lc_num_predictables[4] = form['dustAllergies']
    lc_num_predictables[5] = form['occupationalHazards']
    lc_num_predictables[6] = form['geneticRisk']
    lc_num_predictables[7] = form['chronicLungDisease']
    lc_num_predictables[8] = form['balancedDiet']
    lc_num_predictables[9] = form['obesity']
    lc_num_predictables[10] = form['smoking']
    lc_num_predictables[11] = form['passiveSmoker']
    lc_num_predictables[12] = form['chestPain']
    lc_num_predictables[13] = form['coughing']
    lc_num_predictables[14] = form['fatigue']
    lc_num_predictables[15] = form['weightLoss']
    lc_num_predictables[16] = form['shortnessOfBreath']
    lc_num_predictables[17] = form['wheezing']
    lc_num_predictables[18] = form['swallowingDifficulty']
    lc_num_predictables[19] = form['clubbingFingerNails']
    lc_num_predictables[20] = form['frequentColds']
    lc_num_predictables[21] = form['dryCough']
    lc_num_predictables[22] = form['snoring']

    lc_num_cols = ['Age', 'Gender', 'Air Pollution', 'Alcohol use', 'Dust Allergy',
                   'OccuPational Hazards', 'Genetic Risk', 'chronic Lung Disease',
                   'Balanced Diet', 'Obesity', 'Smoking', 'Passive Smoker', 'Chest Pain',
                   'Coughing of Blood', 'Fatigue', 'Weight Loss', 'Shortness of Breath',
                   'Wheezing', 'Swallowing Difficulty', 'Clubbing of Finger Nails',
                   'Frequent Cold', 'Dry Cough', 'Snoring']

    lc_pred_num = pd.DataFrame([dict(zip(lc_num_cols, lc_num_predictables))])
    lc_pred_num = lc_scaler.transform(lc_pred_num)

    lc_predictable = pd.DataFrame(lc_pred_num, columns=lc_num_cols)

    # Colon Cancer
    ## CC Numericals
    cc_num_predictables = [0] * 11

    cc_num_predictables[0] = form['hadPreviousCancer']
    cc_num_predictables[1] = form['hadPreviousColonCancer']
    cc_num_predictables[2] = form['hasFamilyHistory']
    cc_num_predictables[3] = form['hadRadiationTherapy']  # Needs to be added to the form
    cc_num_predictables[4] = form['isOldAge']
    cc_num_predictables[5] = form['hasIDB']  # RENAME TO hasIBD
    cc_num_predictables[6] = form['isSmoker']
    cc_num_predictables[7] = form['doesDrinkAlcohol']
    cc_num_predictables[8] = form['hasObesity']
    cc_num_predictables[9] = form['exercisesRegularly']
    cc_num_predictables[10] = form['hasHighFatDiet']

    cc_num_cols = ['hadPreviousCancer', 'hadPreviousColonCancer', 'hasFamilyHistory',
                   'hadRadiationTherapy', 'isOldAge', 'hasIBD', 'hasObesity', 'isSmoker',
                   'isDrinker', 'exercisesRegularly', 'hasHighFatDiet']

    cc_pred_num = pd.DataFrame([dict(zip(cc_num_cols, cc_num_predictables))])
    cc_pred_num = cc_scaler.transform(cc_pred_num)

    cc_predictable = pd.DataFrame(cc_pred_num, columns=cc_num_cols)

    return hd_predictable, lc_predictable, cc_predictable


def make_predictions(hd_predictable, lc_predictable, cc_predictable):
    hd_model, lc_model, cc_model = load_models()

    hd_result = hd_model.predict(hd_predictable)
    lc_result = lc_model.predict(lc_predictable)
    cc_result = cc_model.predict(cc_predictable)

    return hd_result, lc_result, cc_result


def run_prediction(form_address):
    user_form = load_input(form_address)
    print(user_form)

    hd_predictable, lc_predictable, cc_predictable = make_predictables(user_form)
    hd_result, lc_result, cc_result = make_predictions(hd_predictable, lc_predictable, cc_predictable)
    return hd_result, lc_result, cc_result


# print(run_prediction("/Users/HEyazWxomRdRaFF1SCayINeB1Rt2/forms/-NlKFnhikil3BudirSyB"))


