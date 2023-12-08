package com.joel.noteapplication.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.joel.noteapplication.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    private static volatile NoteRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract NoteDao getNoteDao();

    public static NoteRoomDatabase getDatabase(final Context context){

        if (INSTANCE == null){
            synchronized (NoteRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database")
                                    .addCallback(noteRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

    public static RoomDatabase.Callback noteRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                NoteDao noteDao = INSTANCE.getNoteDao();
                noteDao.insert(new Note("Red fox","The red fox..."));
            });

        }
    };
}
