package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    Spinner spinnerHours;
    Spinner spinnerMinutes;
    EditText editTextHrs;
    EditText editTextMins;
    EditText etDate;
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

    final static int req1=1;
    public String a = "0";

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
        btnSubmit.setOnClickListener(v -> {
            try {
                saveEvent();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        btnPickTime.setOnClickListener(v -> selectTime());
        btnPickDate.setOnClickListener(v -> selectDate());

    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = day + "/" + (month + 1) + "/" + year;
                textViewDatePicked.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeToNotify = hourOfDay + ":" + minute;
                textViewTimePicked.setText(timeToNotify);
            }
        }, hour, mins, false);
        timePickerDialog.show();
    }

    private void setAlarm (String name, String descr, String date, String time) throws ParseException {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
        intent.putExtra("name", name);
        intent.putExtra("descr", descr);
        intent.putExtra("date", date);
        intent.putExtra("time", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String dateAndTime = date + "" + timeToNotify;

        @SuppressLint("SimpleDateFormat")
        DateFormat formatter = new SimpleDateFormat("d/M/yyyy hh:mm");

        try {
            Date newDate = formatter.parse(dateAndTime);
            assert newDate != null;
            alarmManager.set(AlarmManager.RTC_WAKEUP, newDate.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }

    private void sendNotification() {
        String eventName = editTextEventName.getText().toString();
        String eventDescription = editTextEventDescr.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_baseline_5g_24)
                .setContentTitle(eventName)
                .setContentText(eventDescription)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1, notification);
    }


    private void prevActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    private void saveEvent() throws ParseException {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(textViewDatePicked.getText()) ||
                TextUtils.isEmpty(textViewTimePicked.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String eventTitle = editTextEventName.getText().toString();
            String eventDescr = editTextEventDescr.getText().toString();

            if (eventDescr.isEmpty()) {
                eventDescr = null;
            }


            String eventDate = textViewDatePicked.getText().toString();


            replyIntent.putExtra(EXTRA_TITLE, eventTitle);
            replyIntent.putExtra(EXTRA_DESC, eventDescr);
            replyIntent.putExtra(EXTRA_HOUR, timeToNotify);
            replyIntent.putExtra(EXTRA_DATE, eventDate);
            setResult(RESULT_OK, replyIntent);

            setAlarm(eventTitle, eventDescr, eventDate, timeToNotify);
        }

        finish();

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