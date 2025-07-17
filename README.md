🧠💻 Health AI Diagnostic System – Group Project
We developed a full-stack health diagnostic system aimed at providing early risk assessments for brain cancer and lung cancer. Our system included a mobile app, a responsive web application, a powerful AI chatbot, and a custom-built machine learning algorithm trained to analyze user-provided health data.

The system was designed to be user-friendly, fast, and accessible from both mobile and web platforms. It offered real-time predictions through a smart AI bot powered by a predictive model. The goal was to combine usability and AI-driven insight into a seamless diagnostic experience.

🔧 Project Components
📱 1. User Interface (Mobile App & Website)
We designed two front-end platforms:

Mobile App:
A clean and intuitive interface allowing users to fill out a short diagnostic form. Designed for Android.

Web Application (Javascript / HTML-CSS):
Fully responsive website with the same diagnostic form as the mobile app, accessible via desktop or mobile browser.

Features:

Modern, responsive design

Form validation

Integration with API for live predictions

User-friendly interface for all age groups

📝 2. Smart Form & Data Collection
The core of both the app and the website was a health questionnaire a simple, structured form containing questions related to:

Symptoms (e.g., persistent headaches, coughing, etc.)

Personal and family medical history

Lifestyle factors (e.g., smoking, exposure to pollutants)

Implementation Details:

Inputs were designed using dropdowns, checkboxes, and text fields to reduce input error

Data was cleaned and formatted on the front end before submission

Once submitted, form data was sent as a JSON payload to our RESTful API

🤖 3. AI Chatbot and Backend Communication
The AI bot acted as an interactive assistant embedded within both platforms. It provided real-time communication, explanations, and predictions.

How it worked:

The chatbot asked questions conversationally (optional mode)

Once the form was submitted, the chatbot handled the response display

The bot communicated with our backend via a Python Flask API (or Node.js/Express in some versions)

Features:

Custom scripted responses

JSON-based data exchange

Used REST API calls to fetch predictions

Human-like interaction for improved user experience

🧠 4. Machine Learning Model
At the heart of the system was a trained ML model that could estimate the likelihood of brain or lung cancer from user responses.

Technical Details:

Language: Python (using Scikit-learn and Pandas)

Dataset: Sourced from anonymized open-source medical records (Kaggle/UCI)

Training Process:

Cleaned and preprocessed data

Balanced classes using SMOTE

Tuned hyperparameters for better accuracy

Trained and tested on a split dataset (80/20)

Achieved over 90% accuracy during validation

The final trained model was deployed using Flask (or FastAPI), which exposed an endpoint that the API could call to make predictions.

⚙️ 5. Technical Flow
Here’s a step-by-step overview of how the system works:

User submits form (via mobile app or website)

Data is sent as a POST request to our API endpoint

API passes data to the ML model

Model processes inputs and returns a prediction result

AI chatbot receives the result and formats a response

User sees the prediction, including explanation and suggestion for follow-up action

✅ Results & Achievements
Fully functional on both mobile and web platforms

Delivered real-time predictions within seconds

Achieved high model accuracy during testing and deployment

Received 100% marks for the project as it demonstrated:

Excellent integration of machine learning and full-stack development

User-centric design

Strong collaboration and technical execution
