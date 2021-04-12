package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyViewActivity extends AppCompatActivity {
    Button btnCalendarView;
    Button btnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_daily_view);

        btnCalendarView = findViewById(R.id.buttonCalendarView);
        btnAddEvent = findViewById(R.id.buttonAdd);

        btnCalendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendarView();
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
        intent.putExtra("from", "dailyView");
        startActivity(intent);
    }

    private void openCalendarView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}