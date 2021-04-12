package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnBack;
    Button btnSubmit;
    Button btnCalendar;
    EditText etEventName;
    EditText eventDescription;
    Spinner spinnerHours;
    Spinner spinnerMinutes;
    EditText editTextHrs;
    EditText editTextMins;
    EditText etDate;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_event);

        Intent intent = getIntent();
        date = intent.getStringExtra(CalendarActivity.EXTRA_TEXT);


        Integer[] mins = new Integer[60];
        Integer[] hours = new Integer[24];

        addnumbers(mins);
        addnumbers(hours);

        btnBack = findViewById(R.id.buttonBack);
        btnSubmit = findViewById(R.id.buttonSubmit);
        btnCalendar = findViewById(R.id.buttonCalendar);
        spinnerHours = findViewById(R.id.spinnerHours);
        spinnerMinutes = findViewById(R.id.spinnerMinutes);
        editTextHrs = findViewById(R.id.editTextTextHoursSelect);
        editTextMins = findViewById(R.id.editTextTextMinsSelect);
        etDate = findViewById(R.id.editTextDate2);


        spinnerMinutes.setOnItemSelectedListener(this);
        spinnerHours.setOnItemSelectedListener(this);

        @SuppressLint("ResourceType")
        ArrayAdapter<Integer> adapterMins = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        adapterMins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinutes.setAdapter(adapterMins);


        @SuppressLint("ResourceType")
        ArrayAdapter<Integer> adapterHours = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours);
        adapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHours.setAdapter(adapterHours);

        etDate.setText(date);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevActivity();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });
    }

    private void prevActivity() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    private void openCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
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
        String newText = text;

        if (text.length() == 1) {
            newText = "0" + text;
        }

        if (parent.getId() == R.id.spinnerHours) {
            editTextHrs.setText(newText);
        } else {
            editTextMins.setText(newText);
        }
        Log.d("value of drop down", text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}