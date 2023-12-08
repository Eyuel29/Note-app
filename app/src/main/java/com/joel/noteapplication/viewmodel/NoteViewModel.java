package com.joel.noteapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.joel.noteapplication.model.Note;
import com.joel.noteapplication.repo.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final LiveData<List<Note>> allNotes;
    private NoteRepository noteRepository;


    public NoteViewModel(@NonNull Application application) {

        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = (LiveData<List<Note>>) noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void update(Note note){
        noteRepository.update(note);
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }

    public void insert(Note note){
                noteRepository.insert(note);
    }

}
