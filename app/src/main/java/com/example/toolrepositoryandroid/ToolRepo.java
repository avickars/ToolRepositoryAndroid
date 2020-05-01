package com.example.toolrepositoryandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ToolRepo extends AppCompatActivity {
    ListView toolsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_repo);

        toolsList = (ListView) findViewById(R.id.toolsListView);


    }




    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.addItem:
                Log.i("Item Selected", "additem");
                toAddTool();
                return true;
            case R.id.accountSettings:
                Log.i("Item Selected", "item");
                toProfile();
                return true;
            default:
                return false;
        }
    }
    private void toAddTool() {
        Intent intent = new Intent(getApplicationContext(), AddTool.class);
        startActivity(intent);
        finish();
    }

    private void toProfile() {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
    }




}
