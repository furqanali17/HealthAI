from flask import Flask, request
from main import run_prediction

app = Flask(__name__)


@app.get("/predict")
def predict_cc():
    form_data = str(request.args.get("form"))
    hd, lc, cc = run_prediction(form_data)
    output = f"{hd[0]},{lc[0]},{cc[0]}"
    print(output)
    message = {"predictions": f'{output}'}
    return message


if __name__ == '__main__':
    app.run(debug=True, port=65530)
