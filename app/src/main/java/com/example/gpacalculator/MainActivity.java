package com.example.gpacalculator;
import android.text.TextWatcher;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText[] gradeFields = new EditText[5];
    private TextView gpaDisplay;
    private Button computeButton;
    private boolean isCalculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradeFields[0] = findViewById(R.id.grade1);
        gradeFields[1] = findViewById(R.id.grade2);
        gradeFields[2] = findViewById(R.id.grade3);
        gradeFields[3] = findViewById(R.id.grade4);
        gradeFields[4] = findViewById(R.id.grade5);
        gpaDisplay = findViewById(R.id.gpaDisplay);
        computeButton = findViewById(R.id.computeButton);

        //Compute Button Functionality
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCalculated) {
                    clearForm();
                } else {
                    calculateGPA();
                }
            }
        });

        for (EditText editText : gradeFields) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isCalculated) {
                        computeButton.setText("COMPUTE");
                        isCalculated = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }


    //GPA Calculation
    private void calculateGPA() {
        float sum = 0;
        boolean filled = true; //For detecting empty strings
        for (EditText gradeField : gradeFields) {
            String gradeStr = gradeField.getText().toString();
            if (gradeStr.isEmpty()) {
                gradeField.setBackgroundColor(Color.RED);
                filled = false;
            } else {
                float grade = Float.parseFloat(gradeStr);
                sum += grade;
                gradeField.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        // If the fields are not empty
        if (filled) {
            float total_gpa = sum / (gradeFields.length) ;
            gpaDisplay.setText(String.format("GPA: %.2f", total_gpa));
            BackgroundChange(total_gpa);
            computeButton.setText("CLEAR");
            isCalculated = true;
        }
    }

    //Background Change Functionality
    private void BackgroundChange(float gpa) {
        if (gpa < 6) {
            findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.RED);
        } else if (gpa >= 60 && gpa < 80) {
            findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.YELLOW);
        } else {
            findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.GREEN);
        }
    }

    // Clearing form with button click
    private void clearForm() {
        for (EditText gradeField : gradeFields) {
            gradeField.setText("");
            gradeField.setBackgroundColor(Color.TRANSPARENT);
        }
        gpaDisplay.setText("");
        computeButton.setText("COMPUTE");
        findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.WHITE);
        isCalculated = false;
    }

}
