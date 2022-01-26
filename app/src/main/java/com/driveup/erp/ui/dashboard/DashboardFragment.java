package com.driveup.erp.ui.dashboard;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.CommandDetailActivity;
import com.driveup.erp.HomeActivity;
import com.driveup.erp.R;
import com.driveup.erp.adapter.CommandAdapter;
import com.driveup.erp.model.Command;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment {

    private Activity mActivity;
    private DatabaseReference mDatabase;
    private RecyclerView mRecycler;
    private DatabaseReference databaseReference;

    private int numberOFCurrentCommand = 0;
    List<Command> commandList;
    CommandAdapter commandAdapter;

    public static List<String> keys;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFgmt.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        Toast.makeText(getContext(), "" + HomeActivity.message, Toast.LENGTH_SHORT).show();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("commands");

        mRecycler = rootView.findViewById(R.id.recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setHasFixedSize(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (Command cmd: commandList){
                    if (cmd.getStatus_cmd().equals("Non payé")){
                        numberOFCurrentCommand = numberOFCurrentCommand + 1;
                    }
                }

                TextView nbrCommand = rootView.findViewById(R.id.nombre_commandes);
                String finalCommand = "("+ numberOFCurrentCommand + ")";

                Toast.makeText(getContext(), finalCommand, Toast.LENGTH_SHORT).show();
                nbrCommand.setText(finalCommand);
            }
        }, 5000);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commandList = new ArrayList<>();
                keys = new ArrayList<>();
                for (DataSnapshot commandSnap: snapshot.getChildren()){
                    String key = commandSnap.getRef().getKey();
                    Command command = commandSnap.getValue(Command.class);
                    if (command.getStatus_cmd().equals("Non payé")){
                        commandList.add(command);
                        keys.add(key);
                    }
                }

                //List<Command> mList = new ArrayList<>();
                Collections.reverse(commandList);

                commandAdapter = new CommandAdapter(getContext(), commandList);
                mRecycler.setAdapter(commandAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}