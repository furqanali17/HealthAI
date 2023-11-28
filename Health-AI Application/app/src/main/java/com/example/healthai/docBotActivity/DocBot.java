package com.example.healthai.docBotActivity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class DocBot  {
    private String apiKey;
    private String systemMessage;

    public DocBot(String apiKey){
        this.apiKey = apiKey;
        this.systemMessage = "Your name is Doc-Bot. " +
                "Doc-Bot is a medical support assistant who provides professional medical advice to patients. " +
                "You must not mention anything outside of directly relevant medical information. " +
                "This includes facts about the history of medicine, real doctors, or anything related to fiction and media. " +
                "You must make an exception to this when asked questions about yourself. " +
                "When asked about a topic outside of your domain, you must exclusively respond with: " +
                "As a medical assistant I am unable to provide input on non-medical topics. If you have any medical questions, " +
                "I would be happy to help." + "Here is a response template for you to follow: :" +
                "1-3 Sentence long paragraph: Explain how to deal with the symptoms. Keep it brief." +
                "1 - 5 bullet points: list of symptoms which you should seek medical attention / examination if you are experiencing.(if the patient has described this, recommend they seek medical attention)"   ;
    }

    // Setters
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }
    public void setSystemMessage(String systemMessage){
        this.systemMessage = systemMessage;
    }




    public String sendPrompt(String prompt){
        try{
            // Define API Endpoint
            URL apiUrl = new URL("https://api.openai.com/v1/chat/completions");

            // Open a connection to endpoint
            HttpURLConnection apiConnection = (HttpURLConnection) apiUrl.openConnection();

            // Set the request method to POST
            apiConnection.setRequestMethod("POST");

            // Set the request headers
            apiConnection.setRequestProperty("Authorization","Bearer " + apiKey);
            apiConnection.setRequestProperty("Content-Type","application/json");

            // Enable input / output streams
            apiConnection.setDoOutput(true);

            // Set the request body with your chat conversation
            String request = "{\"model\":\"gpt-3.5-turbo\",\n\"messages\":[{\"role\":\"system\",\"content\":\""
                    + systemMessage + "\"},{\"role\":\"user\",\"content\":\"" + prompt + "\"}],\n\"temperature\":0.6}\n";
            try (DataOutputStream streamOut = new DataOutputStream(apiConnection.getOutputStream())){
                streamOut.writeBytes(request);
            }

            // Get response code
            int responseCode = apiConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                // Process the response from the API
                try(BufferedReader br = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()))){
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    // Create a JSON object from the response string to index
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    return jsonResponse.getJSONArray("choices").getJSONObject(0).
                            getJSONObject("message").getString("content");
                }
            }
            else {
                Log.e("DocBot","API Request Failed with Response Code: " + responseCode);
            }
            Log.d("DocBot",Integer.toString(responseCode));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
