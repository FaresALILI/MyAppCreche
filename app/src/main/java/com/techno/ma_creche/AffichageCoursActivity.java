package com.techno.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


import android.widget.EditText;
import android.widget.TextView;

public class AffichageCoursActivity extends AppCompatActivity {

    EditText editTextDescription;
    TextView textViewDateEnvoi;
    TextView textViewStatut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cours);

        this.editTextDescription =findViewById(R.id.editTextDescription);
        this.textViewDateEnvoi = findViewById(R.id.textViewDateEnvoi);
        this.textViewStatut = findViewById(R.id.textViewStatut);

        String[] activity = getIntent().getStringExtra("currentActivity").split("/" );;
        this.textViewDateEnvoi.setText(activity[0]);
        this.editTextDescription.setText(activity[1]);

    }
}
