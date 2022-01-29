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
import com.driveup.erp.ui.catalog.CatalogFragment;
import com.driveup.erp.ui.command.CommandFragment;
import com.driveup.erp.ui.dashboard.DashboardFragment;
import com.driveup.erp.ui.production.ProductionFragment;
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
    public static FragmentManager fragmentManager;
    public static HomeActivity HomeContextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On définit le contenu c-à-d on attache l'interface xml au code Java correspondante
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bouton Flottant qu'on appelle depuis l'interface (trouver l'élément à partir de son ID)
        FloatingActionButton fab = findViewById(R.id.fab);
        // On programme un écouteur d'évènement quand on va cliquer sur le bouton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Bienvenu sur Drive Up", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // On définit le menu de navigation tiroir ou flottant en l'appellant depuis l'interface grâce à son ID
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        // On appelle les textView qui afficheront le nom et la position de l'utilisateur
        TextView username = (TextView) headerView.findViewById(R.id.user_name);
        TextView user_role = (TextView) headerView.findViewById(R.id.user_role);
        // On récupère ce nom et role en fonction des coordonnées fournies par l'interface login après connexion
        String nom = getIntent().getStringExtra("name");
        String role = getIntent().getStringExtra("role");
        // On affiche le nom et le rôle das les textView préparés
        username.setText(nom);
        user_role.setText(role);
        // On définit le reste du menu tiroire ou flottant en ajoutant des écouteurs pour quand l'utilisateur va cliquer sur
        // un élément de ce menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null; // On prépare en avance une variable Fragment qui contiendra une instance du fragment
                                          // désiré quand on va cliquer

                switch (item.getItemId()){
                    case R.id.nav_dashboard: // Quand on clique sur Table de bord
                        fragment = new DashboardFragment();
                        toolbar.setTitle("Tableau de board");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_command: // Quand on clique sur Command
                        fragment = new CommandFragment();
                        toolbar.setTitle("Commande");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_provisioning: // Quand on clique sur Approvisionnement
                        fragment = new ProvisioningFragment();
                        toolbar.setTitle("Approvisionnement");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_production: // Quand on clique sur Production
                        fragment = new ProductionFragment();
                        toolbar.setTitle("Production");
                        setMyFragment(fragment);
                        break;
                    case R.id.nav_catalog: // Quand on clique sur catalogue
                        fragment = new CatalogFragment();
                        toolbar.setTitle("Catalogue");
                        setMyFragment(fragment);
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

        // Pour gérer le changement des fragments au clic, on utilise un gestionnaire
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_frame, new DashboardFragment()).commit();

        HomeActivity.HomeContextActivity = HomeActivity.this;
    }

    // Méthode pour replacer un fragment par un autre au clic
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
        // Quand on clique sur le bouton retour alors qu'on est sur l'écran principal, on attache un écouteur qui :
        if (drawer.isDrawerOpen(GravityCompat.END)) { // Ferme le menu tiroir si celui-ci est ouvert
            drawer.closeDrawer(GravityCompat.END);
        } else { // Fait apparaître une boîte d'alerte pour confirmer si l'on veut quitter l'application
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