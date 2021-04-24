package com.example.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class DailyViewActivity extends AppCompatActivity {
    Button btnCalendarView;
    Button btnAddEvent;
    RecyclerView rv;
    EventAdapter eventAdapter;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private EventViewModel eventViewModel;

    String title;
    String descr;
    String hour;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_daily_view);



        rv = findViewById(R.id.recycler_view);
        eventAdapter = new EventAdapter(new EventAdapter.WordDiff());
        rv.setAdapter(eventAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));


        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        eventViewModel.getAllEvents().observe(this, events -> {
            eventAdapter.submitList(events);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                eventViewModel.delete(eventAdapter.getEventAtPos(viewHolder.getAdapterPosition()));
                Toast.makeText(DailyViewActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);

        btnCalendarView = findViewById(R.id.buttonCalendarView);
        btnAddEvent = findViewById(R.id.buttonAdd);

        btnCalendarView.setOnClickListener(v -> openCalendarView());

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
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    private void openCalendarView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEventActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddEventActivity.EXTRA_DESC);
            String hour = data.getStringExtra(AddEventActivity.EXTRA_HOUR);
            String date = data.getStringExtra(AddEventActivity.EXTRA_DATE);

            Event event = new Event(title, desc, date, hour);

            eventViewModel.insert(event);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


}