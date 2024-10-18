package com.example.phonebook.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.phonebook.Database.Note;
import com.example.phonebook.Repository.NoteRepository;

import java.util.List;

public class NoteMainViewModel extends ViewModel {
    private final NoteRepository mNoteRepository;

    public NoteMainViewModel(Application application) {
        mNoteRepository = new NoteRepository(application);
    }
    LiveData<List<Note>> getAllNotes() {
        return mNoteRepository.getAllNotes();
    }
}
