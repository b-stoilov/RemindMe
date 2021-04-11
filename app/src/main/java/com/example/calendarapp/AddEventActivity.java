package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddEventActivity extends AppCompatActivity {
    Button btnBack;
    Button btnSubmit;
    EditText etEventName;
    EditText eventDescription;
    Spinner spinnerHours;
    Spinner spinnerMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_event);

        Integer[] mins = new Integer[60];
        Integer[] hours = new Integer[24];

        addnumbers(mins);
        addnumbers(hours);

        btnBack = findViewById(R.id.buttonBack);
        btnSubmit = findViewById(R.id.buttonSubmit);
        spinnerHours = findViewById(R.id.spinnerHours);
        spinnerMinutes = findViewById(R.id.spinnerMinutes);

        int spinnerMinutesId = R.id.spinnerMinutes;
        int spinnerHoursId = R.id.spinnerHours;



        @SuppressLint("ResourceType")
        ArrayAdapter<Integer> adapter = new ArrayAdapter(this, spinnerMinutesId, mins);

        spinnerHours.setAdapter(adapter);

        @SuppressLint("ResourceType")
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter(this, spinnerHoursId, hours);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    private void saveEvent() {
    }

    private void addnumbers (Integer[] mins) {
        for (int i = 0; i < mins.length; i ++) {
            mins[i] = i;
        }
    }


}