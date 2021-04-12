package com.example.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";

    Button buttonSaveDate;
    Button buttonBack;
    CalendarView cv;
    TextView textViewDate;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        buttonSaveDate = findViewById(R.id.buttonSaveCalendar);
        buttonBack = findViewById(R.id.buttonBackCalendar);
        cv = findViewById(R.id.calendarView);
        textViewDate = findViewById(R.id.textViewSelectedDate);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textViewDate.setText(date);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });



        buttonSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewDate.getText().equals("DD/MM/YYYY")) {
                    new AddEventActivity().showPopup(v, R.layout.popup_invalid_calendar_date);
                } else {
                    submitDate();
                }

            }
        });

    }

    private void back() {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private void submitDate() {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra(EXTRA_TEXT, date);
        startActivity(intent);
    }
}