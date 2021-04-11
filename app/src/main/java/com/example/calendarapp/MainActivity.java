package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    Button btnAddEvent;
    Button btnDailyView;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_activity);

        btnAddEvent = findViewById(R.id.buttonAddEvent);
        btnDailyView = (Button) findViewById(R.id.buttonDailyView);

        btnDailyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDailyView();
            }
        });
        
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEvent();
            }
        });

    }

    private void openAddEvent() {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    private void openDailyView() {
        Intent intent = new Intent(this, DailyViewActivity.class);
        startActivity(intent);
    }
}