package com.example.phonebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.Database.Note;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.Database.Note;
import com.example.phonebook.databinding.ItemNoteBinding;
import com.example.phonebook.helper.NoteDiffCallback;
import com.example.phonebook.ui.InsertUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends
        RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final ArrayList<Note> listNotes = new ArrayList<>();
    public void setListNotes(List<Note> listNotes) {
        final NoteDiffCallback diffCallback = new
                NoteDiffCallback(this.listNotes, listNotes);
        final DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(diffCallback);
        this.listNotes.clear();
        this.listNotes.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                     parent, int viewType) {
        ItemNoteBinding binding =
                ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()
                ), parent, false);
        return new NoteViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder
                                         holder, int position) {
        holder.bind(listNotes.get(position));
    }
    @Override
    public int getItemCount() {
        return listNotes.size();
    }
    static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        final ItemNoteBinding binding;
        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Note note) {
            binding.tvItemTitle.setText(note.getTitle());
            binding.tvItemDate.setText(note.getTimestamp());
            binding.tvItemDescription.setText(note.getDescription());
            binding.cvItemNote.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(),
                        InsertUpdateActivity.class);

                intent.putExtra(InsertUpdateActivity.EXTRA_NOTE, note);
                v.getContext().startActivity(intent);
            });
        }
    }
}
