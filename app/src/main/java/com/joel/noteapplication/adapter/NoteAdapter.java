package com.joel.noteapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joel.noteapplication.R;
import com.joel.noteapplication.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public List<Note> allNotes = new ArrayList<>();
    NoteSelectionListener noteSelectionListener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        String title = allNotes.get(position).getNoteTitle();
        String body = allNotes.get(position).getNoteBody();

        if(title.length() > 20){ title = title.substring(0,20); }
        if(body.length() > 50){ body  = body.substring(0, 50); }

        holder.noteTitleView.setText(title);
        holder.noteBodyView.setText(body);
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void updateIndex(List<Note> updatedNotes){
        this.allNotes = updatedNotes;
        this.notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitleView, noteBodyView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleView = itemView.findViewById(R.id.noteTitle);
            noteBodyView = itemView.findViewById(R.id.noteBody);
//            itemView.setOnLongClickListener(view -> {
//                itemView.findViewById(R.id.btnEditNote).setVisibility(View.VISIBLE);
//                return true;
//            });

            itemView.setOnClickListener(view -> {
                Note note = allNotes.get(getAdapterPosition());
                noteSelectionListener.setSelectedNote(note);
            });
        }
    }

    public void setNoteSelectionListener(NoteSelectionListener noteSelectionListener){
        this.noteSelectionListener = noteSelectionListener;
    }

    public Note getNote(int position){
        return allNotes.get(position);
    }


}
