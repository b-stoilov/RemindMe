package com.example.calendarapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ListAdapter<Event, EventHolder> {

    public EventAdapter(@NonNull DiffUtil.ItemCallback<Event> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return EventHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        Event currentEvent = getItem(position);

        holder.bindTitle(currentEvent.getEventTitle());
        holder.bindDescr(currentEvent.getEventDescription());
        holder.bindDate(currentEvent.getEventDate());
        holder.bindHour(currentEvent.getEventHour());
    }

    static class WordDiff extends DiffUtil.ItemCallback<Event> {

        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            boolean sameTitle = oldItem.getEventTitle().equals(newItem.getEventTitle());
            boolean sameDescr = oldItem.getEventDescription().equals(newItem.getEventDescription());
            boolean sameDate = oldItem.getEventDate().equals(newItem.getEventDate());
            boolean sameHour = oldItem.getEventHour().equals(newItem.getEventHour());

            return sameDate && sameDescr && sameHour && sameTitle;
        }
    }


}

class EventHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle;
    TextView textViewDescription;
    TextView textViewDate;
    TextView textViewHour;

    public EventHolder(View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.text_view_event_title);
        textViewDescription = itemView.findViewById(R.id.text_view_event_description);
        textViewDate = itemView.findViewById(R.id.text_view_date);
        textViewHour = itemView.findViewById(R.id.text_view_hour);
    }

    public void bindTitle (String text) {
        textViewTitle.setText(text);
    }

    public void bindDescr (String text) {
        textViewDescription.setText(text);
    }

    public void bindDate (String text) {
        textViewDate.setText(text);
    }

    public void bindHour (String text) {
        textViewHour.setText(text);
    }


    static EventHolder create (ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventHolder(view);
    }
}
