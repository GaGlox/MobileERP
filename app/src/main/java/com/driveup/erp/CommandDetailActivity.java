package com.driveup.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.Toast;

import com.driveup.erp.adapter.CommandAdapter;
import com.driveup.erp.adapter.LigneCommandAdapter;
import com.driveup.erp.model.Command;
import com.driveup.erp.model.LigneCommand;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandDetailActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    List<LigneCommand> commandLignList;
    LigneCommandAdapter commandLignAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_detail);

        String key = "" + getIntent().getExtras().getString("command_id");
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("commands").child(key).child("ligns_cmd");

        mRecycler = findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(CommandDetailActivity.this));
        mRecycler.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandLignList = new ArrayList<>();
                for (DataSnapshot commandSnap: snapshot.getChildren()){
                    //String key = commandSnap.getKey();
                    LigneCommand lignCommand = commandSnap.getValue(LigneCommand.class);
                    commandLignList.add(lignCommand);
                }

                //List<Command> mList = new ArrayList<>();
                //Collections.reverse(commandLignList);

                commandLignAdapter = new LigneCommandAdapter(CommandDetailActivity.this, commandLignList);
                mRecycler.setAdapter(commandLignAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}