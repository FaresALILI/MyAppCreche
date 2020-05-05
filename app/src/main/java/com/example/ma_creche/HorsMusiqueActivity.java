package com.example.ma_creche;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ma_creche.utils.CategirieUser;

import java.util.ArrayList;

public class HorsMusiqueActivity extends AppCompatActivity {
    ArrayAdapter<String> model;
    Button btnDecon;
    CategirieUser cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hors_musique);
        ListView sp = findViewById(R.id.listViewMusic);
        this.btnDecon = findViewById(R.id.buttonDeconnexion);
        TextView textView =findViewById(R.id.textViewNomAct);
        ArrayList<String> listMus = new ArrayList<>();
        textView.setText(getIntent().getStringExtra("nomActivity"));
        listMus.add("Aller Crocro");
        listMus.add("Au clair de la lune");
        listMus.add("Une souris verte");
        listMus.add("Dans la forêt lointaine");
       // th.start();
        model = new ArrayAdapter(HorsMusiqueActivity.this, android.R.layout.simple_list_item_activated_1, listMus);
        sp.setAdapter(model);
        this.btnDecon.setOnClickListener((View v)->
                {
                    this.cat.deconnexion();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }
}

