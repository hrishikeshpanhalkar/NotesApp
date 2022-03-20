package com.example.notesappjava.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.notesappjava.Adapter.NotesAdapter;
import com.example.notesappjava.Model.Notes;
import com.example.notesappjava.R;
import com.example.notesappjava.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private NotesViewModel notesViewModel;
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private TextView noFilter, highToLow, lowToHigh;
    private List<Notes> filterNotesAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E91E63")));
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        floatingActionButton = findViewById(R.id.newNotesBtn);
        recyclerView = findViewById(R.id.notesRecyclerView);
        noFilter = findViewById(R.id.no_filter);
        highToLow = findViewById(R.id.high_to_low);
        lowToHigh = findViewById(R.id.low_to_high);

        noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
        noFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(0);
                noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_unselected_shape);
                highToLow.setBackgroundResource(R.drawable.filter_unselected_shape);
            }
        });
        highToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(1);
                noFilter.setBackgroundResource(R.drawable.filter_unselected_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_unselected_shape);
                highToLow.setBackgroundResource(R.drawable.filter_selected_shape);
            }
        });
        lowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(2);
                noFilter.setBackgroundResource(R.drawable.filter_unselected_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_selected_shape);
                highToLow.setBackgroundResource(R.drawable.filter_unselected_shape);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
            }
        });
        notesViewModel.getAllNotes.observe(MainActivity.this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNotesAll = notes;
            }
        });

    }

    private void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(MainActivity.this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAll = notes;
                }
            });
        } else if (i == 1) {
            notesViewModel.highToLow.observe(MainActivity.this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAll = notes;
                }
            });
        } else if (i == 2) {
            notesViewModel.lowToHigh.observe(MainActivity.this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAll = notes;
                }
            });
        }
    }

    private void setAdapter(List<Notes> notes) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(MainActivity.this, notes);
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search Notes Here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                notesFilter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void notesFilter(String s) {
        ArrayList<Notes> filterNames = new ArrayList<>();
        for(Notes notes: this.filterNotesAll){
            if(notes.notesTitle.contains(s) || notes.notesSubtitle.contains(s)){
                filterNames.add(notes);
            }
        }
        this.notesAdapter.searchNotes(filterNames);
    }
}