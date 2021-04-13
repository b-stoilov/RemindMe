package com.example.calendarapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;

@Database(entities = Event.class, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    private static EventDatabase instance;

    public abstract EventDAO eventDAO();

    public static synchronized EventDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), EventDatabase.class,
                    "event_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventDAO eventDAO;

        private PopulateDbAsyncTask (EventDatabase ed) {
            this.eventDAO = ed.eventDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventDAO.insert(new Event("Wash dishes", "",  "2021-04-13"));
            eventDAO.insert(new Event("Call Lora", "",  "2022-04-13"));
            return null;
        }
    }
}
