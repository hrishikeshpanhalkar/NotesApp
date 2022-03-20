package com.example.notesappjava.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesappjava.Dao.NotesDao;
import com.example.notesappjava.Database.NotesDatabase;
import com.example.notesappjava.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> lowToHigh;
    public LiveData<List<Notes>> highToLow;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAllNotes();
        highToLow = notesDao.highToLow();
        lowToHigh = notesDao.lowToHigh();
    }

    public void insertNotes(Notes notes){
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes){
        notesDao.updateNotes(notes);
    }
}
