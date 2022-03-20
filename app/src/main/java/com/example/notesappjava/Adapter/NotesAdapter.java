package com.example.notesappjava.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappjava.Activities.UpdateNoteActivity;
import com.example.notesappjava.Model.Notes;
import com.example.notesappjava.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    private Context context;
    private List<Notes> notes;
    private List<Notes> allNotesItem;

    public NotesAdapter(Context context, List<Notes> notes) {
        this.context = context;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchNotes(List<Notes> filterName){
        this.notes = filterName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesAdapter.notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.notesViewHolder holder, int position) {
        Notes note = notes.get(position);
        if(note.notesPriority.equals("1")){
            holder.notesPriority.setBackgroundResource(R.drawable.greenshape);
        }else if(note.notesPriority.equals("2")){
            holder.notesPriority.setBackgroundResource(R.drawable.yellowshape);
        }else{
            holder.notesPriority.setBackgroundResource(R.drawable.redshape);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateNoteActivity.class);
                intent.putExtra("id", note.id);
                intent.putExtra("title", note.notesTitle);
                intent.putExtra("subtitle", note.notesSubtitle);
                intent.putExtra("priority", note.notesPriority);
                intent.putExtra("notes", note.notes);
                context.startActivity(intent);
            }
        });
        holder.title.setText(note.notesTitle);
        holder.subTitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class notesViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subTitle, notesDate;
        private View notesPriority;
        public notesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notesTitle);
            subTitle = itemView.findViewById(R.id.notesSubTitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
