package com.joel.noteapplication.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private @NotNull int noteId;

    private @NotNull String noteTitle, noteBody;

    public Note(@NotNull String noteTitle,@NotNull String noteBody){
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
    }

    @NotNull
    public String getNoteBody() {
        return noteBody;
    }

    @NotNull
    public String getNoteTitle() {
        return noteTitle;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId){
        this.noteId = noteId;
    }
}