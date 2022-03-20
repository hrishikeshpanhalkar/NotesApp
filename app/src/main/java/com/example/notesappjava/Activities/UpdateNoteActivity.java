package com.example.notesappjava.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappjava.Model.Notes;
import com.example.notesappjava.R;
import com.example.notesappjava.ViewModel.NotesViewModel;
import com.example.notesappjava.databinding.ActivityUpdateNoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;
import java.util.Objects;

public class UpdateNoteActivity extends AppCompatActivity {
    private ActivityUpdateNoteBinding binding;
    private String Priority = "1";
    private int sid;
    private String stitle, ssubtitle, spriority, snotes;
    private String title, subtitle, notes;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        sid = getIntent().getIntExtra("id", 0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        spriority = getIntent().getStringExtra("priority");
        snotes = getIntent().getStringExtra("notes");

        binding.titleUT.getEditText().setText(stitle);
        binding.subTitleUT.getEditText().setText(ssubtitle);
        binding.notesUT.getEditText().setText(snotes);
        if(spriority.equals("1")){
            binding.greenPriorityUT.setImageResource(R.drawable.ic_done);
        }else if(spriority.equals("2")){
            binding.yellowPriorityUT.setImageResource(R.drawable.ic_done);
        }else {
            binding.redPriorityUT.setImageResource(R.drawable.ic_done);
        }
        Priority = spriority;


        binding.greenPriorityUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriorityUT.setImageResource(R.drawable.ic_done);
                binding.yellowPriorityUT.setImageResource(0);
                binding.redPriorityUT.setImageResource(0);

                Priority = "1";
            }
        });

        binding.yellowPriorityUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriorityUT.setImageResource(0);
                binding.yellowPriorityUT.setImageResource(R.drawable.ic_done);
                binding.redPriorityUT.setImageResource(0);

                Priority = "2";
            }
        });

        binding.redPriorityUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriorityUT.setImageResource(0);
                binding.yellowPriorityUT.setImageResource(0);
                binding.redPriorityUT.setImageResource(R.drawable.ic_done);

                Priority = "3";
            }
        });

        binding.updateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = binding.titleUT.getEditText().getText().toString();
                subtitle = binding.subTitleUT.getEditText().getText().toString();
                notes = binding.notesUT.getEditText().getText().toString();
                updateNotes(title, subtitle, notes);
            }
        });
    }

    private void updateNotes(String title, String subtitle, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes notes1 = new Notes();
        notes1.id = sid;
        notes1.notesTitle = title;
        notes1.notesSubtitle = subtitle;
        notes1.notes = notes;
        notes1.notesPriority = Priority;
        notes1.notesDate = sequence.toString();
        notesViewModel.updateNote(notes1);

        Toast.makeText(UpdateNoteActivity.this, "Notes Updated Successfully!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNoteActivity.this, R.style.AppBottomSheetDialogTheme);
            View view = LayoutInflater.from(UpdateNoteActivity.this).inflate(R.layout.delete_bottom_sheet,
                    (LinearLayout) findViewById(R.id.bottomSheet));
            sheetDialog.setContentView(view);

            TextView yes = view.findViewById(R.id.yesBtn);
            TextView no = view.findViewById(R.id.noBtn);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesViewModel.deleteNotes(sid);
                    finish();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetDialog.dismiss();
                }
            });
            sheetDialog.show();
        }
        return true;
    }
}