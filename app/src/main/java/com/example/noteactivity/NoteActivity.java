package com.example.noteactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashSet;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    int noteId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText editNoteText = findViewById(R.id.edit_note);

        Intent editNote = getIntent();
        noteId = editNote.getIntExtra("noteId", -1);

        if (noteId != -1){
            editNoteText.setText(NoteListActivity.notes.get(noteId));
            editNoteText.setSelection(editNoteText.getText().length());
        }else{
            NoteListActivity.notes.add("");
            noteId = NoteListActivity.notes.size() - 1;
            NoteListActivity.noteAdapter.notifyDataSetChanged();
        }


        editNoteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence data, int i, int i1, int i2) {

                NoteListActivity.notes.set(noteId, String.valueOf(data));
                NoteListActivity.noteAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.example.noteactivity", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(NoteListActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
