package com.joel.noteapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.joel.noteapplication.R;
import com.joel.noteapplication.util.Extras;

public class WriteNoteActivity extends AppCompatActivity {

    private int updateNoteId;
    private EditText noteTitleInput, noteBodyInput;
    private String currentRequestType = "", updateNoteTitle = "", updateNoteBody = "";
    private String noteChanged = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteBodyInput = findViewById(R.id.noteBodyInput);
        noteTitleInput = findViewById(R.id.noteTitleInput);
        getAndSetData();
    }

    public void getAndSetData(){
        if (getIntent().getStringExtra(Extras.EXTRA_REQUEST_TYPE).equals(Extras.EXTRA_REQUEST_UPDATE)){

            currentRequestType = Extras.EXTRA_REQUEST_UPDATE;
            noteBodyInput.setText(getIntent().getStringExtra(Extras.EXTRA_REPLY_BODY));
            noteTitleInput.setText(getIntent().getStringExtra(Extras.EXTRA_REPLY_TITLE));
            updateNoteTitle = getIntent().getStringExtra(Extras.EXTRA_REPLY_TITLE);
            updateNoteBody = getIntent().getStringExtra(Extras.EXTRA_REPLY_BODY);
            updateNoteId = getIntent().getIntExtra(Extras.EXTRA_REPLY_ID,-1);

        }else if(getIntent().getStringExtra(Extras.EXTRA_REQUEST_TYPE).equals(Extras.EXTRA_REQUEST_ADD)){
            currentRequestType = Extras.EXTRA_REQUEST_ADD;
        }
    }

    public void checkInput(){
        if (!noteTitleInput.getText().toString().equals("") && !noteBodyInput.getText().toString().equals("")){
                if (currentRequestType.equals(Extras.EXTRA_REQUEST_UPDATE) &&
                    noteTitleInput.getText().toString().equals(updateNoteTitle) &&
                    noteBodyInput.getText().toString().equals(updateNoteBody)){
                    this.noteChanged = "FALSE";
                }else {
                    this.noteChanged = "TRUE";
                }
            submitNote();
        }else{
            Toast.makeText(this, "Please write title and description!", Toast.LENGTH_SHORT).show();
        }
    }

    public void submitNote(){
        Intent data = new Intent();
        data.putExtra(Extras.EXTRA_REPLY_TITLE,noteTitleInput.getText().toString());
        data.putExtra(Extras.EXTRA_REPLY_BODY,noteBodyInput.getText().toString());
        data.putExtra(Extras.EXTRA_NOTE_CHANGED, this.noteChanged);

        if (updateNoteId != -1){
            data.putExtra(Extras.EXTRA_REPLY_ID,this.updateNoteId);
        }

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_save_button,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.buttonAddNote){
            checkInput();
        }else if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}