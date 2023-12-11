package com.example.healthai.formActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class predictActivity extends AppCompatActivity {
    String url, formString, formKey, userId, predictUrl;
    TextView heartDisease_textView, lungCancer_textView, colonCancer_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        url = "https://cf3c-83-174-182-162.ngrok-free.app/";

        heartDisease_textView = findViewById(R.id.heartDisease_textView);
        lungCancer_textView = findViewById(R.id.lungCancer_textView);
        colonCancer_textView = findViewById(R.id.colonCancer_textView);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        assert user != null;
        userId = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("forms");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    formKey = dataSnapshot.getChildren().iterator().next().getKey();

                    formString = "predict?form=/Users/" + userId + "/forms/" + formKey;
                    predictUrl = url + formString;
                    Log.d("predict", predictUrl);

                    makeJsonObjectRequest();
                } else {
                    Log.d("FormKey", "No forms found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error reading forms", databaseError.toException());
            }
        });
    }

    private void makeJsonObjectRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, predictUrl, null, response -> {
            try {
                String predictionsString = response.getString("predictions");
                String[] predictionsArray = predictionsString.split(",");

                if (predictionsArray.length >= 3) {
                    String heartDiseasePrediction = convertHeartDisease(predictionsArray[0]);
                    String lungCancerPrediction = predictionsArray[1];
                    String colonCancerPrediction = convertColonCancer(predictionsArray[2]);

                    changeBackgroundColor(heartDiseasePrediction, R.id.heartDisease_LinearLayout);
                    changeBackgroundColor(lungCancerPrediction, R.id.lungCancer_LinearLayout);
                    changeBackgroundColor(colonCancerPrediction, R.id.colonCancer_LinearLayout);

                    heartDisease_textView.setText(heartDiseasePrediction);
                    lungCancer_textView.setText(lungCancerPrediction);
                    colonCancer_textView.setText(colonCancerPrediction);

                    Map<String, Object> predictionsMap = new HashMap<>();
                    predictionsMap.put("heartDiseasePrediction", heartDiseasePrediction);
                    predictionsMap.put("lungCancerPrediction", lungCancerPrediction);
                    predictionsMap.put("colonCancerPrediction", colonCancerPrediction);

                    // Reference to the predictions node for the specific user
                    DatabaseReference predictionsReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("predictions");

                    predictionsReference.setValue(predictionsMap).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Predictions successfully added to the database", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.d("Predictions", "Heart Disease: " + heartDiseasePrediction + ", Lung Cancer: " + lungCancerPrediction + ", Colon Cancer: " + colonCancerPrediction);
                } else {
                    Log.e("Predictions", "Insufficient predictions in the array");
                }
            } catch (JSONException e) {
                Log.e("Predictions", "Error parsing JSON response", e);
            }
        }, error -> {
            Log.e("Volley", "Error in JSON request", error);
        });

        Volley.newRequestQueue(this).add(request);
    }

    private String convertHeartDisease(String prediction) {
        return prediction.equals("No") ? "Low" : "High";
    }

    private String convertColonCancer(String prediction) {
        return prediction.equals("0") ? "Low" : "High";
    }

    private void changeBackgroundColor(String prediction, int linearLayoutId) {
        LinearLayout linearLayout = findViewById(linearLayoutId);

        int color;

        switch (prediction) {
            case "Low":
                color = Color.GREEN;
                break;
            case "High":
                int modifiedRed = 255;
                int modifiedGreen = 140;
                int modifiedBlue = 140;
                color = Color.rgb(modifiedRed, modifiedGreen, modifiedBlue);
                break;
            default:
                color = Color.YELLOW;
                break;
        }

        linearLayout.setBackgroundColor(color);
    }

}