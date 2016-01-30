package com.example.test.gridle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.test.gridle.BaseDaoLayer.BaseDao;
import com.example.test.gridle.BaseDaoLayer.InitDB;

import java.util.List;

public class MainActivity extends NavigationDrawerActivity {

    List<NotesData> data;
    GridLayoutManager mLayoutManager;
    BaseDao baseDao;
    RecyclerView mRecyclerView;
    NotesAdapter mAdapter;
    View view;
    MenuItem delete;
    BroadcastReceiver cardSelected;
    String noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
        view = View.inflate(this,R.layout.activity_main,null);
        frameLayout.addView(view);
        initializeView();
        navigationView.getMenu().getItem(0).setChecked(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
            }
        });
        cardSelected = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                delete.setVisible(true);
//                 noteId= intent.getStringExtra("id");
            }
        };

    }

    @Override
    protected void openNotes() {
        mDrawerLayout.closeDrawers();
    }

    private void initializeView() {
        new InitDB(this);
        baseDao = new BaseDao(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_notes);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cardSelected);
        super.onPause();
    }

    @Override
    public void onResume() {
     super.onResume();
     data = baseDao.getNotesData();
     mAdapter = new NotesAdapter(data);
     mRecyclerView.setAdapter(mAdapter);
     LocalBroadcastManager.getInstance(this).registerReceiver(cardSelected, new IntentFilter(NotesAdapter.CARD_SELECTED));
 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notes, menu);
        delete = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_delete:
                delete.setVisible(false);
                BaseDao baseDao = new BaseDao(this);
                baseDao.remove(noteId);
                data = baseDao.getNotesData();
                mAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

}
