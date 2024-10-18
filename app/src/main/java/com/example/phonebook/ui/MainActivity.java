package com.example.phonebook.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.phonebook.NoteAdapter;
import com.example.phonebook.R;
import com.example.phonebook.databinding.ActivityMainBinding;
import com.example.phonebook.helper.ViewModelFactory;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NoteMainViewModel noteMainViewModel =
                obtainViewModel(MainActivity.this);
        noteMainViewModel.getAllNotes().observe(this, notes -> {
            if (notes != null) {
                adapter.setListNotes(notes);
            }
        });
        adapter = new NoteAdapter();
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(view -> {
            if (view.getId() == R.id.fab_add) {
                Intent intent = new Intent(MainActivity.this,
                        InsertUpdateActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    @NonNull
    private static NoteMainViewModel
    obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity,
                factory).get(NoteMainViewModel.class);
    }
}