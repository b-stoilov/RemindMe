package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnBack;
    Button btnSubmit;
    Button btnCalendar;
    Spinner spinnerHours;
    Spinner spinnerMinutes;
    EditText editTextHrs;
    EditText editTextMins;
    EditText etDate;
    EditText editTextEventName;
    EditText editTextEventDescr;
    String date;
    Events events;

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

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
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventDescr = findViewById(R.id.editTextEventDescription);
        etDate = findViewById(R.id.editTextDate2);


        spinnerMinutes.setOnItemSelectedListener(this);
        spinnerHours.setOnItemSelectedListener(this);

        @SuppressLint("ResourceType")
        ArrayAdapter adapterMins = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        adapterMins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinutes.setAdapter(adapterMins);


        @SuppressLint("ResourceType")
        ArrayAdapter adapterHours = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours);
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

//                events = new Events(editTextEventName.getText().toString(), editTextEventDescr.getText().toString(), null);
//                if (events.getDate() == null || events.getEventTitle() == null) {
//                    showPopup(v, R.layout.popup_empty_field);
//                } else {
//                    saveEvent();
//                }

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
        Intent replyIntent = new Intent(this, DailyViewActivity.class);
        if (TextUtils.isEmpty(editTextEventName.getText()) ||
                TextUtils.isEmpty(etDate.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String eTitle = editTextEventName.getText().toString();
            String eDescr = editTextEventDescr.getText().toString();

            if (eDescr.isEmpty()) {
                eDescr = "";
            }

            String eHour = editTextHrs.getText().toString() + ":" + editTextMins.getText().toString();
            String eDate = etDate.getText().toString();

            replyIntent.putExtra("title", eTitle);
            replyIntent.putExtra("description", eDescr);
            replyIntent.putExtra("hour", eHour);
            replyIntent.putExtra("date", eDate);


            setResult(RESULT_OK, replyIntent);

        }
        startActivity(replyIntent);
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

    void showPopup (View v, int popup) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                v.getContext().getSystemService
                        (v.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView =
                inflater.inflate(popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // Makes sure you can also press outside the popup to dismiss it:
        boolean focusable = true;
        PopupWindow popupWindow =
                new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}