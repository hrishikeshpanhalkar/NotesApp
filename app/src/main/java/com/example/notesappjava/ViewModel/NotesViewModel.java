package com.example.notesappjava.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesappjava.Model.Notes;
import com.example.notesappjava.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
        highToLow = repository.highToLow;
        lowToHigh = repository.lowToHigh;
    }

    public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }
    public void deleteNotes(int id){
        repository.deleteNotes(id);
    }

}
