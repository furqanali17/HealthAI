import os
import pandas as pd
import tensorflow as tf
from flask import Flask, jsonify, request
import model_maker

app = Flask(__name__)

CC_FEATURES, HD_FEATURES, LC_FEATURES = model_maker.get_columns()
# cc_ftr_cols = model_maker.split_features(CC_FEATURES)
# cc_path = make_save_input_fn(cc_ftr_cols)

cc_loaded_model = tf.saved_model.load('models/cc_model/1701368367')

@app.route('/predict_cc', methods=['POST'])
def predict_cc():
    # Get JSON data from the request
    print(cc_loaded_model)

    test_data = request.get_json()

    prediction, probability = model_maker.predict(CC_FEATURES, cc_loaded_model)

    return jsonify({'prediction': prediction, 'probability': probability})


# @app.route('/predict_hd', methods=['POST'])
# def predict_hd():
#     data = request.json
#     prediction, probability = make_prediction(hd_classifier, data)
#     return jsonify({'prediction': prediction, 'probability': probability})
#
#
# @app.route('/predict_lc', methods=['POST'])
# def predict_lc():
#     data = request.json
#     prediction, probability = make_prediction(lc_classifier, data)
#     return jsonify({'prediction': prediction, 'probability': probability})


if __name__ == '__main__':
    app.run(debug=True, port=8080)