package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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

        spinnerMinutes.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        @SuppressLint("ResourceType")
        ArrayAdapter<Integer> adapterMins = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        adapterMins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinutes.setAdapter(adapterMins);


        @SuppressLint("ResourceType")
        ArrayAdapter<Integer> adapterHours = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours);

        spinnerHours.setAdapter(adapterHours);

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Log.d("value of drop down", text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}