package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity  {
    Button btnBack;
    Button btnSubmit;
    Button btnPickTime;
    Button btnPickDate;
    EditText editTextEventName;
    EditText editTextEventDescr;
    TextView textViewTimePicked;
    TextView textViewDatePicked;

    String date;
    String timeToNotify;
    String selectedDate;

    NotificationManagerCompat notificationManager;

    public static final String EXTRA_TITLE = "com.example.application.example.TITLE";
    public static final String EXTRA_DESC = "com.example.application.example.DESC";
    public static final String EXTRA_HOUR = "com.example.application.example.HOUR";
    public static final String EXTRA_DATE = "com.example.application.example.DATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_event);

        notificationManager = NotificationManagerCompat.from(this);

        Intent intent = getIntent();
        date = intent.getStringExtra(CalendarActivity.EXTRA_TEXT);


        Integer[] mins = new Integer[60];
        Integer[] hours = new Integer[24];

        addnumbers(mins);
        addnumbers(hours);

        btnBack = findViewById(R.id.buttonBack);
        btnSubmit = findViewById(R.id.buttonSubmit);
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventDescr = findViewById(R.id.editTextEventDescription);
        btnPickTime = findViewById(R.id.buttonPickTime);
        btnPickDate = findViewById(R.id.buttonPickDate);
        textViewTimePicked = findViewById(R.id.textViewTimePicked);
        textViewDatePicked = findViewById(R.id.textViewDatePicked);



        btnBack.setOnClickListener(v -> prevActivity());
        btnSubmit.setOnClickListener(v -> saveEvent());
        btnPickTime.setOnClickListener(v -> selectTime());
        btnPickDate.setOnClickListener(v -> selectDate());

    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedDate = dayOfMonth + "-" + (month1 + 1) + "-" + year1;
            textViewDatePicked.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            timeToNotify = hourOfDay + ":" + minute;
            textViewTimePicked.setText(FormatTime(hourOfDay, minute));
        }, hour, mins, false);
        timePickerDialog.show();
    }

    private void setAlarm(String name, String date, String time)  {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("time", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String dateAndTime = date + " " + timeToNotify;


        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");

        try {
            Date newDate = formatter.parse(dateAndTime);
            alarmManager.set(AlarmManager.RTC_WAKEUP, newDate.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        finish();

    }

    public String FormatTime(int hour, int minute) {

        String time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }

    private void prevActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void saveEvent() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(textViewDatePicked.getText()) ||
                TextUtils.isEmpty(textViewTimePicked.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String eventTitle = editTextEventName.getText().toString();
            String eventDescr = editTextEventDescr.getText().toString();
            String eventDate = textViewDatePicked.getText().toString();

            if (eventDescr.isEmpty()) {
                eventDescr = null;
            }


            replyIntent.putExtra(EXTRA_TITLE, eventTitle);
            replyIntent.putExtra(EXTRA_DESC, eventDescr);
            replyIntent.putExtra(EXTRA_HOUR, timeToNotify);
            replyIntent.putExtra(EXTRA_DATE, eventDate);
            setResult(RESULT_OK, replyIntent);

            setAlarm(eventTitle, eventDate, timeToNotify);
        }


    }

    private void addnumbers (Integer[] mins) {
        for (int i = 0; i < mins.length; i ++) {
            mins[i] = i;
        }
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