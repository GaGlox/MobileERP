package com.driveup.erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.driveup.erp.adapter.LignCommandAdapter;
import com.driveup.erp.model.LignCommand;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommandDetailActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    List<LignCommand> commandLignList;
    LignCommandAdapter commandLignAdapter;

    private String code, customer, order, delivery, status, total;
    private TextView code_cmd, customer_cmd, order_cmd, delivery_cmd, status_cmd, total_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_detail);

        String key = getIntent().getExtras().getString("command_id");
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();

        code = getIntent().getExtras().getString("code");
        customer = getIntent().getExtras().getString("customer");
        delivery = getIntent().getExtras().getString("delivery");
        status = getIntent().getExtras().getString("status");
        order = getIntent().getExtras().getString("order");

        // Récupération des variables
        code_cmd = findViewById(R.id.code_content);
        customer_cmd = findViewById(R.id.customer_content);
        order_cmd = findViewById(R.id.order_date);
        delivery_cmd = findViewById(R.id.delivery_content);
        status_cmd = findViewById(R.id.status_content);
        total_cost = findViewById(R.id.total_bill_content);

        // Remplissage par des valeurs
        code_cmd.setText(code);
        customer_cmd.setText(customer);
        delivery_cmd.setText(delivery);
        status_cmd.setText(status);
        order_cmd.setText(order);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("commands").child(key).child("ligns_cmd");

        mRecycler = findViewById(R.id.recycler_view_lign);
        mRecycler.setLayoutManager(new LinearLayoutManager(CommandDetailActivity.this));
        mRecycler.setHasFixedSize(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CommandDetailActivity.this, total, Toast.LENGTH_SHORT).show();
                total_cost.setText(total);
            }
        }, 5000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandLignList = new ArrayList<>();
                int pt_total = 0;
                for (DataSnapshot commandSnap: snapshot.getChildren()){
                    LignCommand lignCommand = commandSnap.getValue(LignCommand.class);
                    pt_total = pt_total + lignCommand.getTotal_price_cmd();
                    commandLignList.add(lignCommand);
                }

                total = "" + pt_total + " FC";
                //Toast.makeText(CommandDetailActivity.this, total, Toast.LENGTH_SHORT).show();
                //List<Command> mList = new ArrayList<>();
                //Collections.reverse(commandLignList);

                commandLignAdapter = new LignCommandAdapter(CommandDetailActivity.this, commandLignList);
                mRecycler.setAdapter(commandLignAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}