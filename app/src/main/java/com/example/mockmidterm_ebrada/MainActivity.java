package com.example.mockmidterm_ebrada;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textDisplay;

    private double num1 = 0.0;
    private double num2 = 0.0;
    private double num3 = 0.0;
    private String activeOperator = "";
    private boolean isOperatorSelected = false;
    private boolean isErrorState = false;

    private static final String addOperator = "+";
    private static final String subOperator = "-";
    private static final String mulOperator = "*";
    private static final String divOperator = "/";
    private static final int STUDENT_ID_LAST3 = 83;
    private static final double CUSTOM_FACTOR = STUDENT_ID_LAST3 / 100.0;

    private static final String KEY_DISPLAY = "key_display";
    private static final String KEY_NUM1 = "key_num1";
    private static final String KEY_NUM2 = "key_num2";
    private static final String KEY_NUM3 = "key_num3";
    private static final String KEY_OPERATOR = "key_operator";
    private static final String KEY_OPERATOR_SELECTED = "key_isOperatorSelected";
    private static final String KEY_ERROR_STATE = "key_isErrorState";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDisplay = findViewById(R.id.textDisplay);

        int[] digitIds = {
                R.id.btn0,
                R.id.btn1,
                R.id.btn2,
                R.id.btn3,
                R.id.btn4,
                R.id.btn5,
                R.id.btn6,
                R.id.btn7,
                R.id.btn8,
                R.id.btn9
        };
        for (int id : digitIds) {
            Button b = findViewById(id);
            b.setOnClickListener(this);
        }

        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSub).setOnClickListener(this);
        findViewById(R.id.btnMul).setOnClickListener(this);
        findViewById(R.id.btnDiv).setOnClickListener(this);
        findViewById(R.id.btnEqual).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnCustom).setOnClickListener(this);

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (isErrorState && id != R.id.btnClear) {
            return;
        }
        if (id == R.id.btnClear) {
            clearAll();
            return;
        }
        if (id == R.id.btnAdd || id == R.id.btnSub ||
                id == R.id.btnMul || id == R.id.btnDiv) {
            onOperatorClicked(id);
            return;
        }
        if (id == R.id.btnEqual) {
            onEqualClicked();
            return;
        }
        if (id == R.id.btnCustom) {
            onCustomOperatorClicked();
            return;
        }
        onDigitOrDotClicked((Button) v);
    }
    private void onDigitOrDotClicked(Button b) {
        String text = b.getText().toString();
        String current = textDisplay.getText().toString();
        if (current.equals("0") || isOperatorSelected || isErrorState) {
            current = "";
            isOperatorSelected = false;
            isErrorState = false;
        }
        if (text.equals(".") && current.contains(".")) {
            return;
        }
        current = current + text;
        textDisplay.setText(current);
    }
    private void onOperatorClicked(int id) {
        String current = textDisplay.getText().toString();
        if (current.isEmpty() || current.equals("Cannot divide by zero")) {
            return;
        }
        try {
            num1 = Double.parseDouble(current);
        } catch (NumberFormatException e) {
            num1 = 0.0;
        }
        if (id == R.id.btnAdd) activeOperator = addOperator;
        else if (id == R.id.btnSub) activeOperator = subOperator;
        else if (id == R.id.btnMul) activeOperator = mulOperator;
        else if (id == R.id.btnDiv) activeOperator = divOperator;
        isOperatorSelected = true;
    }
    private void onEqualClicked() {
        if (activeOperator.isEmpty()) {
            return;
        }
        String current = textDisplay.getText().toString();
        if (current.isEmpty()) {
            return;
        }
        try {
            num2 = Double.parseDouble(current);
        } catch (NumberFormatException e) {
            num2 = 0.0;
        }
        String resultText = calculate(num1, num2, activeOperator);
        textDisplay.setText(resultText);

        if (!isErrorState) {
            try {
                num3 = Double.parseDouble(resultText);
            } catch (NumberFormatException e) {
                num3 = 0.0;
            }
        }
        activeOperator = "";
        isOperatorSelected = false;
    }
    private void onCustomOperatorClicked() {
        String current = textDisplay.getText().toString();
        if (current.isEmpty() || current.equals("Cannot divide by zero")) {
            return;
        }

        try {
            double value = Double.parseDouble(current);
            double customResult = value * CUSTOM_FACTOR;
            textDisplay.setText(String.valueOf(customResult));
            num3 = customResult;
        } catch (NumberFormatException e) {
            textDisplay.setText("Error");
            isErrorState = true;
        }
    }

    private String calculate(double first, double second, String op) {
        double result;

        if (op.equals(divOperator)) {
            if (second == 0.0) {
                textDisplay.setText("Cannot divide by zero");
                isErrorState = true;
                return "Cannot divide by zero";
            }
        }

        if (op.equals(addOperator)) {
            result = first + second;
        } else if (op.equals(subOperator)) {
            result = first - second;
        } else if (op.equals(mulOperator)) {
            result = first * second;
        } else if (op.equals(divOperator)) {
            result = first / second;
        } else {
            result = second;
        }

        isErrorState = false;
        return String.valueOf(result);
    }

    private void clearAll() {
        num1 = 0.0;
        num2 = 0.0;
        num3 = 0.0;
        activeOperator = "";
        isOperatorSelected = false;
        isErrorState = false;
        textDisplay.setText("0");
    }

    private void restoreState(Bundle savedInstanceState) {
        String displayText = savedInstanceState.getString(KEY_DISPLAY, "0");
        num1 = savedInstanceState.getDouble(KEY_NUM1, 0.0);
        num2 = savedInstanceState.getDouble(KEY_NUM2, 0.0);
        num3 = savedInstanceState.getDouble(KEY_NUM3, 0.0);
        activeOperator = savedInstanceState.getString(KEY_OPERATOR, "");
        isOperatorSelected = savedInstanceState.getBoolean(KEY_OPERATOR_SELECTED, false);
        isErrorState = savedInstanceState.getBoolean(KEY_ERROR_STATE, false);

        textDisplay.setText(displayText);
    }

}
