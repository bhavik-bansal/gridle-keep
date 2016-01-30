package com.example.test.gridle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.test.gridle.BaseDaoLayer.BaseDao;

public class AddNote extends AppCompatActivity {
    EditText title;
    EditText note;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        initializeView();
        Intent intent = getIntent();
        id  = intent.getStringExtra("id");
        if (id !=null){
            BaseDao baseDao = new BaseDao(this);
            NotesData data = baseDao.getNotes(id);
            title.setText(data.getTitle());
            note.setText(data.getNote());
        }

    }

    @Override
    public void onBackPressed() {
       insertNote();
        super.onBackPressed();
    }

    private void initializeView() {
        title = (EditText) findViewById(R.id.notes_title);
        note = (EditText) findViewById(R.id.note);
    }

    private void insertNote(){
        NotesData data = new NotesData();
        data.setTitle(title.getText().toString());
        data.setNote(note.getText().toString());
        if (id!=null){
            data.setId(id);
        }else {
            data.setId(" ");
        }
        if (note.length() != 0 || title.length() !=0) {
            BaseDao baseDao = new BaseDao(this);
            baseDao.insertUpdateNote(data);
        }
    }
 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
