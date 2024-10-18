package com.example.phonebook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.phonebook.Database.Note;
import com.example.phonebook.R;
import com.example.phonebook.databinding.ActivityInsertUpdateBinding;
import com.example.phonebook.helper.DateHelper;
import com.example.phonebook.helper.ViewModelFactory;

public class InsertUpdateActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE = "extra_note";
    private static final int ALERT_DIALOG_CLOSE = 10;
    private static final int ALERT_DIALOG_DELETE = 20;
    private boolean isEdit = false;

    private Note note;
    private NoteInsertUpdateViewModel noteInsertUpdateViewModel;
    private ActivityInsertUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteInsertUpdateViewModel = obtainViewModel(this);
        note = getIntent().getParcelableExtra(EXTRA_NOTE);

        if (note != null) {
            isEdit = true;
        } else {
            note = new Note();
        }

        String actionBarTitle;
        String btnTitle;
        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);
            binding.btnDelete.setVisibility(View.VISIBLE);
            binding.btnDelete.setOnClickListener(v -> showAlertDialog(ALERT_DIALOG_DELETE));

            if (note != null) {
                binding.edtTitle.setText(note.getTitle());
                binding.edtDescription.setText(note.getDescription());
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
            binding.btnDelete.setVisibility(View.INVISIBLE);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnSubmit.setText(btnTitle);
        binding.btnSubmit.setOnClickListener(view -> {
            String title = binding.edtTitle.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();
            if (title.isEmpty()) {
                binding.edtTitle.setError(getString(R.string.empty));
            } else if (description.isEmpty()) {
                binding.edtDescription.setError(getString(R.string.empty));
            } else {
                note.setTitle(title);
                note.setDescription(description);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_NOTE, note); // Correctly putting note as Parcelable

                if (isEdit) {
                    noteInsertUpdateViewModel.update(note);
                    showToast(getString(R.string.changed));
                } else {
                    note.setTimestamp(DateHelper.getCurrentDate());
                    noteInsertUpdateViewModel.insert(note);
                    showToast(getString(R.string.added));
                }

                setResult(RESULT_OK, intent); // Set result to notify parent activity
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (item.getItemId() == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogTitle = getString(R.string.delete);
            dialogMessage = getString(R.string.message_delete);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    if (!isDialogClose) {
                        noteInsertUpdateViewModel.delete(note);
                        showToast(getString(R.string.deleted));
                    }
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Caution: avoid potential NullPointerExceptions by managing binding lifecycle
    }

    @NonNull
    private NoteInsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(NoteInsertUpdateViewModel.class);
    }
}
