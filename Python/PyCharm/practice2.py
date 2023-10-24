import tensorflow as tf
import pandas as pd
import numpy as np
from tensorflow import keras
tf.enable_eager_execution()


heart_disease_data = pd.read_csv()
(x_train, y_train), (x_test, y_test) = fashiondata.load_data()

x_train, x_test = x_train/255, x_test/255

model = tf.keras.models.Sequential([
    tf.keras.layers.Flatten(input_shape=(28, 28)),
    tf.keras.layers.Dense(128, activation='relu'),
    tf.keras.layers.Dropout(0.2),
    tf.keras.layers.Dense(10, activation='softmax')
])

model.compile(
    optimizer='adam',
    loss='sparse_categorical_crossentropy',
    metrics=['accuracy']
)

model.fit(x_train, y_train, epochs=200)