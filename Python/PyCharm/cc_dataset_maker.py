import csv
import random

DATASET_SIZE = 4000
COLON_CANCER = 0.5
PREVIOUS_CANCER = 0.3
PREVIOUS_COLON_CANCER = 0.5
FAMILY_HISTORY = 0.5
RADIATION_THERAPY = 0.4
OLD_AGE = 0.3
INFLAMMATORY_BOWEL_DISEASE = 0.5
OBESITY = 0.2
SMOKING = 0.2
DRINKING = 0.3
EXERCISE_REGULARLY = 0.
HIGH_FAT_DIET = 0.4


class User:
    def __init__(self, hasColonCancer, hadPreviousCancer, hadPreviousColonCancer, hasFamilyHistory, hadRadiationTherapy,
                 isOldAge, hasIBD, hasObesity, isSmoker, isDrinker, exercisesRegularly, hasHighFatDiet):
        self.hasColonCancer = hasColonCancer
        self.hasPreviousCancer = hadPreviousCancer
        self.hadPreviousColonCancer = hadPreviousColonCancer
        self.hasFamilyHistory = hasFamilyHistory
        self.hadRadiationTherapy = hadRadiationTherapy
        self.isOldAge = isOldAge
        self.hasIBD = hasIBD
        self.hasObesity = hasObesity
        self.isSmoker = isSmoker
        self.isDrinker = isDrinker
        self.exercisesRegularly = exercisesRegularly
        self.hasHighFatDiet = hasHighFatDiet


def create_entry():
        # Determine whether this entry does or doesn't have colon cancer
        hasColonCancer = True if random.random() >= COLON_CANCER else False

        if hasColonCancer:
            hadPreviousCancer = True if random.random() >= PREVIOUS_CANCER else False
            hadPreviousColonCancer = True if (random.random() >= PREVIOUS_COLON_CANCER) and not hadPreviousCancer else False
            hasFamilyHistory = True if random.random() >= FAMILY_HISTORY else False
            hadRadiationTherapy = True if random.random() >= RADIATION_THERAPY else False
            isOldAge = True if random.random() >= OLD_AGE else False
            hasIBD = True if random.random() >= INFLAMMATORY_BOWEL_DISEASE else False
            hasObesity = True if random.random() >= OBESITY else False
            isSmoker = True if random.random() >= SMOKING else False
            isDrinker = True if random.random() >= DRINKING else False
            exercisesRegularly = True if random.random() >= EXERCISE_REGULARLY else False
            hasHighFatDiet = True if random.random() >= HIGH_FAT_DIET else False

        else:
            hadPreviousCancer = True if random.random() >= PREVIOUS_CANCER - 0.1 else False
            hadPreviousColonCancer = True if (random.random() >= PREVIOUS_COLON_CANCER - 0.1) and not hadPreviousCancer else False
            hasFamilyHistory = True if random.random() >= FAMILY_HISTORY - 0.1 else False
            hadRadiationTherapy = True if random.random() >= RADIATION_THERAPY - 0.1 else False
            isOldAge = True if random.random() >= OLD_AGE - 0.1 else False
            hasIBD = True if random.random() >= INFLAMMATORY_BOWEL_DISEASE - 0.1 else False
            hasObesity = True if random.random() >= OBESITY - 0.1 else False
            isSmoker = True if random.random() >= SMOKING - 0.1 else False
            isDrinker = True if random.random() >= DRINKING - 0.1 else False
            exercisesRegularly = True if random.random() >= EXERCISE_REGULARLY + 0.2 else False
            hasHighFatDiet = True if random.random() >= HIGH_FAT_DIET - 0.1 else False

        # return {
        #     'Colon Cancer': hasColonCancer,
        #     'Previous Cancer': hadPreviousCancer,
        #     'Previous Colon Cancer': hadPreviousColonCancer,
        #     'Family History': hasFamilyHistory,
        #     'Radiation Therapy': hadRadiationTherapy,
        #     'Old Age': isOldAge,
        #     'IBD': hasIBD,
        #     'Obesity': hasObesity,
        #     'Smoker': isSmoker,
        #     'Drinker': isDrinker,
        #     'Exercises Regularly': exercisesRegularly,
        #     'High-Fat Diet': hasHighFatDiet
        # }

        return [hasColonCancer, hadPreviousCancer, hadPreviousColonCancer, hasFamilyHistory, hadRadiationTherapy, isOldAge, hasIBD, hasObesity, isSmoker, isDrinker, exercisesRegularly, hasHighFatDiet]

def create_dataset(size):
    with open('cc_dataset.csv', 'w', newline='') as csvfile:
        filewriter = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)

        dataset = [create_entry() for i in range(size)]

        filewriter.writerows(dataset)


def main():
    create_dataset(DATASET_SIZE)

main()