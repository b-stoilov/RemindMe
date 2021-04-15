package com.example.calendarapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    private static EventDatabase instance;

    public abstract EventDAO eventDao();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EventDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (EventDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), EventDatabase.class,
                            "event_database")
                            .addCallback(roomCallback)
                            .build();
                }
            }

        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                EventDAO eventDAO = instance.eventDao();

                eventDAO.deleteAllEvents();


            });
        }
    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private EventDAO eventDAO;
//
//        private PopulateDbAsyncTask (EventDatabase ed) {
//            this.eventDAO = ed.eventDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            eventDAO.insert();
//            eventDAO.insert(
//            return null;
//        }
//    }
}
