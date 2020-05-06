package com.example.toolrepositoryandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToolRepo extends AppCompatActivity {
    ListView toolsList;

    FirebaseDatabase database;
    DatabaseReference ref;


    ArrayList<Tool> list;
    ToolListAdapter adapter;
    Tool tool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tool = new Tool();

        setContentView(R.layout.activity_tool_repo);

        toolsList = (ListView) findViewById(R.id.toolsListView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("tools");
        list = new ArrayList<>();
//        adapter = new ArrayAdapter<Tool>(this, R.layout.tool_info, R.id.toolNameTextView, list);

        adapter = new ToolListAdapter(this, R.layout.tool_info, list);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    tool = ds.getValue(Tool.class);
                    list.add(tool);
                }
                toolsList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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