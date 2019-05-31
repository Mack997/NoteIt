package com.example.noteactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();

    static ArrayAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView noteList = findViewById(R.id.list_notes);

        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("com.example.noteactivity", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null){

        }else{
            notes = new ArrayList<>(set);
        }


        noteAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);

        noteList.setAdapter(noteAdapter);

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent addNote = new Intent(getApplicationContext(), NoteActivity.class);
                addNote.putExtra("noteId", i);
                startActivity(addNote);
            }
        });


        noteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                final int itemToDelete = i;

                new AlertDialog.Builder(NoteListActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                notes.remove(itemToDelete);
                                noteAdapter.notifyDataSetChanged();


                                SharedPreferences sharedPreferences = getApplicationContext()
                                        .getSharedPreferences("com.example.noteactivity", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet<>(NoteListActivity.notes);

                                sharedPreferences.edit().putStringSet("notes", set).apply();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(addNote);
            }
        });
    }


}
