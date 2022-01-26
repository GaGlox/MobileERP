package com.driveup.erp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.driveup.erp.adapter.CommandAdapter;
import com.driveup.erp.model.Command;
import com.driveup.erp.ui.command.CommandFragment;
import com.driveup.erp.ui.dashboard.DashboardFragment;
import com.driveup.erp.ui.provisioning.ProvisioningFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static FragmentManager fragmentManager;
    private TextView username, user_role;
    public static HomeActivity HomeContextActivity;

    private DatabaseReference mDatabase;
    private RecyclerView mRecycler;
    private DatabaseReference databaseReference, databaseReference2, databaseReference3, databaseReference4, databaseReference5;
    List<Command> commandList;
    CommandAdapter commandAdapter;
    public static List<String> keys;

    static public String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("commands");
        databaseReference2 = firebaseDatabase.getReference("agents");
        databaseReference3 = firebaseDatabase.getReference("customers");
        databaseReference4 = firebaseDatabase.getReference("furnitures");
        databaseReference5 = firebaseDatabase.getReference("products");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        username = (TextView) headerView.findViewById(R.id.user_name);
        user_role = (TextView) headerView.findViewById(R.id.user_role);

        String nom = getIntent().getStringExtra("name");
        String role = getIntent().getStringExtra("role");

        username.setText(nom);
        user_role.setText(role);

        message = "Hello fragment";

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_dashboard:
                        fragment = new DashboardFragment();
                        toolbar.setTitle("Tableau de board");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_command:
                        fragment = new CommandFragment();
                        toolbar.setTitle("Commande");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_provisioning:
                        fragment = new ProvisioningFragment();
                        toolbar.setTitle("Approvisionnement");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_production:
                        //fragment = new ProductionFragment();
                        toolbar.setTitle("Production");
                        //setMyFragment(fragment);
                        break;
                    case R.id.nav_catalog:
                        //fragment = new CatalogFragment();
                        toolbar.setTitle("Catalogue");
                        //setMyFragment(fragment);
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                        break;
                    /*case R.id.nav_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Télécharger l'application");
                        intent.putExtra(Intent.EXTRA_TEXT, "https://mydom-kinsafety.web.app/" );
                        startActivity(Intent.createChooser(intent, "Partager via..."));
                        break;*/
                    case R.id.nav_logout:
                        //startActivity(new Intent(HomeActivity.this, InfoActivity.class));
                        break;
                    default:
                        fragment = new DashboardFragment();
                        setMyFragment(fragment);
                }

                // Close the navigation drawer
                drawer.closeDrawers();

                return true;
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_frame, new DashboardFragment()).commit();

        HomeActivity.HomeContextActivity = HomeActivity.this;
    }

    private void setMyFragment(Fragment fragment) {
        //get current fragment manager
        fragmentManager = getSupportFragmentManager();
        //get fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //set new fragment in fragment_container (FrameLayout)
        fragmentTransaction.replace(R.id.nav_frame, fragment, "CURRENT");
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("")
                    .setMessage("Quitter l'application ?")
                    .setNegativeButton(R.string.confirm_no, null)
                    .setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            HomeActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            //case R.id.action_fingerprint:
            //    break;
            default:
                Toast.makeText(this, "Great", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}