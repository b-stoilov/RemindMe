package com.example.calendarapp.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {
    private EventDAO eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        EventDatabase eventDatabase = EventDatabase.getDatabase(application);

        eventDao = eventDatabase.eventDao();
        allEvents = eventDao.selectAllEvents();
    }

    public void insert (Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insert(event);
        });
    }

    public void update (Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.update(event);
        });
    }

    public void delete (Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.delete(event);
        });
    }

    public void deleteAllEvents () {
        EventDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteAllEvents();
        });
    }

    public LiveData<List<Event>> getAllEvents () {
        return allEvents;
    }

//    private static class InsertEventASyncTask extends AsyncTask<Event, Void, Void> {
//        private EventDAO eventDAO;
//
//        private InsertEventASyncTask (EventDAO eventDAO) {
//            this.eventDAO = eventDAO;
//        }
//
//        @Override
//        protected Void doInBackground(Event... events) {
//            eventDAO.insert(events[0]);
//            return null;
//        }
//    }

//    private static class DeleteEventASyncTask extends AsyncTask<Event, Void, Void> {
//        private EventDAO eventDAO;
//
//        private DeleteEventASyncTask (EventDAO eventDAO) {
//            this.eventDAO = eventDAO;
//        }
//
//        @Override
//        protected Void doInBackground(Event... events) {
//            eventDAO.delete(events[0]);
//            return null;
//        }
//    }
//
//    private static class UpdateEventASyncTask extends AsyncTask<Event, Void, Void> {
//        private EventDAO eventDAO;
//
//        private UpdateEventASyncTask (EventDAO eventDAO) {
//            this.eventDAO = eventDAO;
//        }
//
//        @Override
//        protected Void doInBackground(Event... events) {
//            eventDAO.update(events[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteAllEventsASyncTask extends AsyncTask<Void, Void, Void> {
//        private EventDAO eventDAO;
//
//        private DeleteAllEventsASyncTask (EventDAO eventDAO) {
//            this.eventDAO = eventDAO;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            eventDAO.deleteAllEvents();
//            return null;
//        }
//    }
}
