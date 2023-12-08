package com.joel.noteapplication.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joel.noteapplication.R;
import com.joel.noteapplication.adapter.NoteAdapter;
import com.joel.noteapplication.adapter.NoteSelectionListener;
import com.joel.noteapplication.model.Note;
import com.joel.noteapplication.util.Extras;
import com.joel.noteapplication.viewmodel.NoteViewModel;

public class MainActivity
        extends AppCompatActivity
        implements NoteSelectionListener{

    RecyclerView noteRecyclerView;
    NoteViewModel noteViewModel;
    FloatingActionButton addNoteBtn;
    final NoteAdapter noteAdapter = new NoteAdapter();

    public static final int ADD_NOTE_ACTIVITY_REQUEST_CODE = 10,
                         UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 30;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRecyclerView = findViewById(R.id.noteRecyclerView);
        addNoteBtn = findViewById(R.id.addNewNoteBtn);

        addNoteBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WriteNoteActivity.class);
            intent.putExtra(Extras.EXTRA_REQUEST_TYPE,Extras.EXTRA_REQUEST_ADD);
            startActivityForResult(intent, ADD_NOTE_ACTIVITY_REQUEST_CODE);
        });

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteAdapter.setNoteSelectionListener(this);
        noteRecyclerView.setAdapter(noteAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callbackMethod);
        itemTouchHelper.attachToRecyclerView(noteRecyclerView);

        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteViewModel.getAllNotes().observe(this, noteAdapter::updateIndex);
    }

    ItemTouchHelper.SimpleCallback callbackMethod = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Note noteToDelete = noteAdapter.getNote(viewHolder.getAdapterPosition());
            noteViewModel.delete(noteToDelete);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Note newNote;
        if (requestCode == ADD_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            newNote = new Note(
                    data.getStringExtra(Extras.EXTRA_REPLY_TITLE),
                    data.getStringExtra(Extras.EXTRA_REPLY_BODY)
            );
            noteViewModel.insert(newNote);

        }else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            if (data.getStringExtra(Extras.EXTRA_NOTE_CHANGED).equals("TRUE")){
                newNote = new Note(
                        data.getStringExtra(Extras.EXTRA_REPLY_TITLE),
                        data.getStringExtra(Extras.EXTRA_REPLY_BODY)
                );
                newNote.setNoteId(data.getIntExtra(Extras.EXTRA_REPLY_ID, -1));
                noteViewModel.update(newNote);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu,menu);
        return true;
    }

    @Override
    public void setSelectedNote(Note note) {
        Intent intent = new Intent(MainActivity.this, WriteNoteActivity.class);
        intent.putExtra(Extras.EXTRA_REPLY_TITLE, note.getNoteTitle());
        intent.putExtra(Extras.EXTRA_REPLY_BODY, note.getNoteBody());
        intent.putExtra(Extras.EXTRA_REPLY_ID, note.getNoteId());
        intent.putExtra(Extras.EXTRA_REQUEST_TYPE, Extras.EXTRA_REQUEST_UPDATE);
        startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
    }
}