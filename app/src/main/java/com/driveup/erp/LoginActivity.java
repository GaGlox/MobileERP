package com.driveup.erp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText login_username, login_password;
    private Button bt_cancel, bt_validate;
    private String nom, mot_de_passe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des récupérations de variables de l'interface Login
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText)findViewById(R.id.login_password);

        bt_cancel = (Button) findViewById(R.id.login_cancel_button);
        bt_validate = (Button) findViewById(R.id.login_validate_button);

        nom = login_username.getText().toString().trim();
        mot_de_passe = login_password.getText().toString().trim();

        // Evènement Clic sur bouton annuler
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_username.setText(null);
                login_password.setText(null);
            }
        });

        // Evènement Clic sur bouton connexion
        bt_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp_username = login_username.getText().toString().trim();
                String tmp_password = login_password.getText().toString().trim();

                if (tmp_username.equals("ANNE") && tmp_password.equals("1")){
                    // On prépare une Intention d'ouverture de la fenêtre Accueil
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("name", "MONKAMBULA ANNE");
                    intent.putExtra("role", "Chef de projet");
                    startActivity(intent); // On lance l'interface Accueil grâce à l'intention préparée
                    finish();

                    //Toast.makeText(LoginActivity.this, "Bienvenu", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Nom d\'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}