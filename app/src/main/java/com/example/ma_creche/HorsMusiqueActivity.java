package com.example.ma_creche;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ma_creche.R;
//import com.example.ma_creche.StorageActivity;


import java.util.ArrayList;
import java.util.Timer;

public class HorsMusiqueActivity extends AppCompatActivity {
    ArrayAdapter<String> model;
    String currentActivity;
    Thread th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hors_musique);
        ListView sp = findViewById(R.id.listViewMusic);
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
/*
        model.notifyDataSetChanged();
        sp.setOnItemClickListener((parent, view,  position, id)-> {
                    currentActivity=sp.getItemAtPosition(position).toString();

        );
    }*/
    }
}

