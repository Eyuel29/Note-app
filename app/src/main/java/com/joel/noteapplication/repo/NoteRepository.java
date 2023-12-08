package com.joel.noteapplication.repo;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.joel.noteapplication.model.Note;
import com.joel.noteapplication.room.NoteDao;
import com.joel.noteapplication.room.NoteRoomDatabase;

import java.util.List;

public class NoteRepository {
    private LiveData<List<Note>> allNotes;
    private NoteDao noteDao;

    public NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.getNoteDao();
        allNotes = noteDao.getAllNotes();
    }

    @WorkerThread
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    @WorkerThread
    public void update(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.update(note));
    }

    @WorkerThread
    public void delete(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.delete(note));
    }

    @WorkerThread
    public void insert(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.insert(note));
    }
}
