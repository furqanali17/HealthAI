import csv
import random

DATASET_SIZE = 4000
COLON_CANCER = 0.2
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
        hasColonCancer = 1 if random.random() >= COLON_CANCER else 0

        if hasColonCancer:
            hadPreviousCancer = 1 if random.random() >= PREVIOUS_CANCER else 0
            hadPreviousColonCancer = 1 if (random.random() >= PREVIOUS_COLON_CANCER) and not hadPreviousCancer else 0
            hasFamilyHistory = 1 if random.random() >= FAMILY_HISTORY else 0
            hadRadiationTherapy = 1 if random.random() >= RADIATION_THERAPY else 0
            isOldAge = 1 if random.random() >= OLD_AGE else 0
            hasIBD = 1 if random.random() >= INFLAMMATORY_BOWEL_DISEASE else 0
            hasObesity = 1 if random.random() >= OBESITY else 0
            isSmoker = 1 if random.random() >= SMOKING else 0
            isDrinker = 1 if random.random() >= DRINKING else 0
            exercisesRegularly = 1 if random.random() >= EXERCISE_REGULARLY else 0
            hasHighFatDiet = 1 if random.random() >= HIGH_FAT_DIET else 0

        else:
            hadPreviousCancer = 1 if random.random() >= PREVIOUS_CANCER - 0.1 else 0
            hadPreviousColonCancer = 1 if (random.random() >= PREVIOUS_COLON_CANCER - 0.1) and not hadPreviousCancer else 0
            hasFamilyHistory = 1 if random.random() >= FAMILY_HISTORY - 0.1 else 0
            hadRadiationTherapy = 1 if random.random() >= RADIATION_THERAPY - 0.1 else 0
            isOldAge = 1 if random.random() >= OLD_AGE - 0.1 else 0
            hasIBD = 1 if random.random() >= INFLAMMATORY_BOWEL_DISEASE - 0.1 else 0
            hasObesity = 1 if random.random() >= OBESITY - 0.1 else 0
            isSmoker = 1 if random.random() >= SMOKING - 0.1 else 0
            isDrinker = 1 if random.random() >= DRINKING - 0.1 else 0
            exercisesRegularly = 1 if random.random() >= EXERCISE_REGULARLY + 0.2 else 0
            hasHighFatDiet = 1 if random.random() >= HIGH_FAT_DIET - 0.1 else 0

        return [hasColonCancer, hadPreviousCancer, hadPreviousColonCancer, hasFamilyHistory, hadRadiationTherapy, isOldAge, hasIBD, hasObesity, isSmoker, isDrinker, exercisesRegularly, hasHighFatDiet]

def create_dataset(size):
    with open('cc_dataset.csv', 'w', newline='') as csvfile:
        filewriter = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        header_row = ["hasColonCancer", "hadPreviousCancer", "hadPreviousColonCancer", "hasFamilyHistory",
                      "hadRadiationTherapy", "isOldAge", "hasIBD", "hasObesity", "isSmoker", "isDrinker",
                      "exercisesRegularly", "hasHighFatDiet"]

        filewriter.writerow(header_row)

        dataset = [create_entry() for i in range(size)]

        filewriter.writerows(dataset)


def main():
    create_dataset(DATASET_SIZE)

main()