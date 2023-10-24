import csv

with open('heart_2020_cleaned.csv') as csv_file:
    csv_reader = csv.DictReader(csv_file)
    i = 0
    for line in csv_reader:
        if line['HeartDisease'] == 'No':
            HeartDisease = 0
        else:
            HeartDisease = 1
        # BMI =
        BMI = line['BMI']

