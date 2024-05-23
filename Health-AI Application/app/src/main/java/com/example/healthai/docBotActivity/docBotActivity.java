package com.example.healthai.docBotActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.healthai.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class docBotActivity extends AppCompatActivity {
    private docBotAdapter adapter;
    private Button sendBtn;
    private TextInputEditText textInput;
    private DocBot docBot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_bot);
        sendBtn = findViewById(R.id.btnSendMessage);
        textInput = findViewById(R.id.tiMessage);
        docBot = new DocBot(DOCBOT_API_KEY);



        List<Message> messageList = new ArrayList<Message>();
        RecyclerView recyclerView = findViewById(R.id.docBotRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new docBotAdapter(this,messageList);

        recyclerView.setAdapter(adapter);
        sendBtn.setOnClickListener(v -> {
            String prompt = textInput.getText().toString();
            textInput.getText().clear();
            textInput.clearFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
            messageList.add(new Message("user",prompt));



            // Create a new AsyncTask for the network activity, as it is not allowed on the main thread in android
             class Task extends AsyncTask<Void , Void , Void>{
                @Override
                protected Void doInBackground(Void... voids) {
                    try
                    {
                        String response = docBot.sendPrompt(prompt);
                        messageList.add(new Message("system",response));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return null;
                }

                 @Override
                 protected void onPostExecute(Void unused) {
                     adapter.notifyItemInserted(messageList.size()-1);
                 }
             }

            new Task().execute();
        });

    }
}
