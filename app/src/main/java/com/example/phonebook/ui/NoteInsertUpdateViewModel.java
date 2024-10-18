package com.example.phonebook.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.phonebook.Database.Note;
import com.example.phonebook.Repository.NoteRepository;

import java.util.List;

public class NoteInsertUpdateViewModel extends ViewModel {
    private NoteRepository noteRepository;

    public NoteInsertUpdateViewModel(Application application) {
        noteRepository = new NoteRepository(application);
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteRepository.getAllNotes();
    }
}
