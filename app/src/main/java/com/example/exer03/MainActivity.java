package com.example.exer03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLog";
    public static final String KEY_USER_MESSAGE = "USER_MESSAGE";

    private EditText edtMessage;
    private Button btnSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate started");

        edtMessage = findViewById(R.id.edtMessage);
        btnSendMessage = findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(v -> {
            Log.d(TAG, "Send Message button clicked");
            String message = edtMessage.getText().toString();

            Intent intent = new Intent(MainActivity.this, EchoActivity.class);
            intent.putExtra(KEY_USER_MESSAGE, message);
            startActivity(intent);
        });
    }
}
