package com.driveup.erp.ui.provisioning;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driveup.erp.R;
import com.driveup.erp.adapter.CommandAdapter;
import com.driveup.erp.adapter.FurnitureAdapter;
import com.driveup.erp.model.Command;
import com.driveup.erp.model.Furniture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProvisioningFragment extends Fragment {
    private RecyclerView mRecycler;
    private DatabaseReference databaseReference;

    private int numberOFCurrentCommand = 0;
    List<Furniture> furnituresList;
    FurnitureAdapter furnituresAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProvisioningFragment() {
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
    public static ProvisioningFragment newInstance(String param1, String param2) {
        ProvisioningFragment fragment = new ProvisioningFragment();
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_provisioning, container, false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("furnitures");

        mRecycler = rootView.findViewById(R.id.recycler_view_furnitures);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                furnituresList = new ArrayList<>();
                for (DataSnapshot snap: snapshot.getChildren()){
                    //String key = snap.getRef().getKey();
                    Furniture furniture = snap.getValue(Furniture.class);
                    furnituresList.add(furniture);

                }

                //Collections.reverse(furnituresList);

                furnituresAdapter = new FurnitureAdapter(getContext(), furnituresList);
                mRecycler.setAdapter(furnituresAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}