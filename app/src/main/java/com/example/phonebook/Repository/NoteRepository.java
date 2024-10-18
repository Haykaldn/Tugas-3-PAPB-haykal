package com.example.phonebook.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.phonebook.Database.Note;
import com.example.phonebook.Database.NoteDao;
import com.example.phonebook.Database.NoteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private final NoteDao mDaoNotes;

    private final ExecutorService executorService;
    public NoteRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        NoteDatabase db = NoteDatabase.getDatabase(application);
        mDaoNotes = db.daoNote();
    }

    public static NoteRepository getInstance(Application application) {
        return null;
    }

    public LiveData<List<Note>> getAllNotes() {
        return (LiveData<List<Note>>) mDaoNotes.getAllNotes();
    }
    public void insert(final Note note) {
        executorService.execute(() -> mDaoNotes.insert(note));
    }
    public void delete(final Note note){
        executorService.execute(() -> mDaoNotes.delete(note));
    }
    public void update(final Note note){
        executorService.execute(() -> mDaoNotes.update(note));
    }
}
