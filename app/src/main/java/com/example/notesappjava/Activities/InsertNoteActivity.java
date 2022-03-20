package com.example.notesappjava.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.example.notesappjava.Model.Notes;
import com.example.notesappjava.R;
import com.example.notesappjava.ViewModel.NotesViewModel;
import com.example.notesappjava.databinding.ActivityInsertNoteBinding;

import java.util.Date;
import java.util.Objects;

public class InsertNoteActivity extends AppCompatActivity {
    private ActivityInsertNoteBinding binding;
    private String title, subtitle, notes;
    private NotesViewModel notesViewModel;
    private String priority = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriority.setImageResource(R.drawable.ic_done);
                binding.yellowPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);
                priority="1";
            }
        });

        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.yellowPriority.setImageResource(R.drawable.ic_done);
                binding.redPriority.setImageResource(0);
                binding.greenPriority.setImageResource(0);
                priority="2";
            }
        });

        binding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.redPriority.setImageResource(R.drawable.ic_done);
                binding.yellowPriority.setImageResource(0);
                binding.greenPriority.setImageResource(0);
                priority="3";
            }
        });
        binding.doneNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = Objects.requireNonNull(binding.notesTitle.getEditText()).getText().toString();
                subtitle = Objects.requireNonNull(binding.notesSubTitle.getEditText()).getText().toString();
                notes = Objects.requireNonNull(binding.notesData.getEditText()).getText().toString();
                createNotes(title, subtitle, notes);
            }
        });
    }

    private void createNotes(String title, String subtitle, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subtitle;
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();
        notesViewModel.insertNote(notes1);

        Toast.makeText(InsertNoteActivity.this, "Notes Created Successfully!", Toast.LENGTH_LONG).show();
        finish();
    }
}