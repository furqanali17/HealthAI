import pandas as pd

YN_REPLACEMENT = [['No', 0], ['Yes', 1]]
HD_DATASET_YNCOLS = ['HeartDisease', 'Smoking', 'AlcoholDrinking', 'Stroke', 'DiffWalking',
                     'PhysicalActivity', 'Asthma', 'KidneyDisease', 'SkinCancer']

def replace_column(dataset, column, values):
    for i in range(len(values)):
        dataset[column] = dataset[column].replace(values[i][0], values[i][1])
    return dataset

if __name__ == '__main__':
    # Heart Disease
    hd_dataset = pd.read_csv("hd_dataset.csv")

    for col in HD_DATASET_YNCOLS:
        hd_dataset = replace_column(hd_dataset, col, YN_REPLACEMENT)
    hd_dataset = replace_column(hd_dataset, 'Sex', [['Male', 0], ['Female', 1]])
    hd_dataset = replace_column(hd_dataset, 'AgeCategory', [['18-24', 0], ['25-29', 1], ['30-34', 2],
                                                            ['35-39', 3], ['40-44', 4], ['45-49', 5], ['50-54', 6],
                                                            ['55-59', 7], ['60-64', 8], ['65-69', 9], ['70-74', 10],
                                                            ['75-79', 11], ['80 or older', 12]])
    hd_dataset = replace_column(hd_dataset, 'Race', [['White', 0], ['Black', 1], ['Asian', 2], ['American Indian', 3], ['Hispanic', 4], ['Other', 5]])
    hd_dataset = replace_column(hd_dataset, 'Diabetic', [['No', 0], ['Yes', 1],
                                                         ['No, borderline diabetes', 2], ['Yes (during pregnancy)', 3]])
    hd_dataset = replace_column(hd_dataset, 'GenHealth', [['Poor', 0], ['Fair', 1], ['Good', 2],
                                                          ['Very good', 3], ['Excellent', 4]])

    hd_dataset.to_csv("hd_dataset.csv")

    # Lung Cancer
    lc_dataset = pd.read_csv("lc_dataset.csv")
    try:
        lc_dataset = lc_dataset.drop(columns=['Index', 'Patient ID'])
    except KeyError:
        print("Columns already dropped... Continuing")

    lc_dataset = replace_column(lc_dataset, 'Level', [['Low', 0], ['Medium', 1], ['High', 2]])

    lc_dataset.to_csv("lc_dataset.csv")