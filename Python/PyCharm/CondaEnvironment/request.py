import requests

url = 'http://localhost:8080/predict_cc'

data = {
    "hadPreviousCancer": 1,
    "hadPreviousColonCancer": 0,
    "hasFamilyHistory": 1,
    "hadRadiationTherapy": 0,
    "isOldAge": 1,
    "hasIBD": 0,
    "hasObesity": 1,
    "isSmoker": 0,
    "isDrinker": 1,
    "exercisesRegularly": 0,
    "hasHighFatDiet": 1
}

response = requests.post(url, json=data)

print(response.text)