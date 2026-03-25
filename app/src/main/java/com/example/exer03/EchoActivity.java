package com.example.exer03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EchoActivity extends AppCompatActivity {

    private static final String TAG = "EchoActivityLog";
    private TextView txtEcho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echo);

        Log.d(TAG, "onCreate started");

        txtEcho = findViewById(R.id.txtEcho);

        Intent receivedIntent = getIntent();
        String message = receivedIntent.getStringExtra(MainActivity.KEY_USER_MESSAGE);

        Log.d(TAG, "Received message: " + message);

        if (message != null && !message.isEmpty()) {
            txtEcho.setText(message);
        } else {
            txtEcho.setText(getString(R.string.no_message_received));
        }
    }
}