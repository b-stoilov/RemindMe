package com.example.calendarapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

public class EventRepository {
    private EventDAO eventDAO;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        EventDatabase eventDatabase = EventDatabase.getInstance(application);

        eventDAO = eventDatabase.eventDAO();
        allEvents = eventDAO.selectAllEvents();
    }

    public void insert (Event event) {
        new InsertEventASyncTask(eventDAO).execute(event);
    }

    public void update (Event event) {
        new UpdateEventASyncTask(eventDAO).execute(event);
    }

    public void delete (Event event) {
        new UpdateEventASyncTask(eventDAO).execute(event);
    }

    public void deleteAllEvents () {
        new DeleteAllEventsASyncTask(eventDAO).execute();

    }

    public LiveData<List<Event>> getAllEvents () {
        return allEvents;
    }

    private static class InsertEventASyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAO eventDAO;

        private InsertEventASyncTask (EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.insert(events[0]);
            return null;
        }
    }

    private static class DeleteEventASyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAO eventDAO;

        private DeleteEventASyncTask (EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.delete(events[0]);
            return null;
        }
    }

    private static class UpdateEventASyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAO eventDAO;

        private UpdateEventASyncTask (EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.update(events[0]);
            return null;
        }
    }

    private static class DeleteAllEventsASyncTask extends AsyncTask<Void, Void, Void> {
        private EventDAO eventDAO;

        private DeleteAllEventsASyncTask (EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventDAO.deleteAllEvents();
            return null;
        }
    }
}
