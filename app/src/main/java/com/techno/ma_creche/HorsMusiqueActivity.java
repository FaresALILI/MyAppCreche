package com.techno.ma_creche;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.techno.ma_creche.dao.FichierDistant;
import com.techno.ma_creche.dao.MyActivite;
import com.techno.ma_creche.utils.CategirieUser;
import com.techno.ma_creche.utils.StorageUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HorsMusiqueActivity extends AppCompatActivity {

    Button btnDecon;
    CategirieUser cat;
    CalendarView calendarView;

    DatabaseReference databaseReference;
    List<MyActivite> activities ;
    String[] myActivities;
    MyActivite[] mesActivities;
    MyActivite myActivity;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hors_musique);
        calendarView = findViewById(R.id.calendarView);

        databaseReference = FirebaseDatabase.getInstance().getReference("activites");
        this.activities = new ArrayList<>();
        myActivities = new String[100];
        mesActivities = new MyActivite[100];
        ListView sp = (ListView) findViewById(R.id.listViewMusic);
        this.btnDecon = findViewById(R.id.buttonDeconnexion);
        TextView textView = findViewById(R.id.textViewNomAct);
        textView.setText(getIntent().getStringExtra("activite"));

        // th.start();
        calendarView.setOnDateChangeListener((cal, y, m, d) -> {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    myActivity = new MyActivite();
                    myActivity.setListFiles((Collection<FichierDistant>) snap.child("listFiles").getValue());
                    myActivity.setEtat((Boolean) snap.child("etat").getValue());
                    myActivity.setDescription((String) snap.child("description").getValue());
                    myActivity.setDateActivity((String) snap.child("dateActivity").getValue());
                    myActivity.setObjet((String) snap.child("objet").getValue());
                    myActivity.setIdActivity(snap.getKey());
                    //myActivities[i] = myActivity.getDateActivity().toString() + " / " + myActivity.getDescription().toString();
                    mesActivities[i]=myActivity;
                    i++;
                }
                int taille = 0;
                for (MyActivite s : mesActivities) {
                    if (s != null)
                        taille++;
                }
               String[] myActivitiesRelle = new String[taille];
                int j = 0;
                for (MyActivite s : mesActivities) {
                    if (s != null) {
                        myActivitiesRelle[j] = s.getDateActivity()+"/"+s.getDescription();
                        j++;
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(HorsMusiqueActivity.this, android.R.layout.simple_list_item_1, myActivitiesRelle);
                sp.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
            String date = "date = " + d + "_" + (m + 1) + "_" + y;
            getListeCours(date);
            sp.setAdapter(arrayAdapter);
        });

        sp.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, AffichageCoursActivity.class);
            startActivity(intent);
        });
        this.btnDecon.setOnClickListener((View v) ->
                {
                    this.cat.deconnexion();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );

        sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HorsMusiqueActivity.this, AffichageCoursActivity.class);
                MyActivite myActivite = mesActivities[position];
                intent.putExtra("currentActivity",(Serializable)(myActivite));
                startActivity(intent);            }
        });
    }

    private void getListeCours(String date) {
        //Récupérer le lundi de la semaine
        StorageUtils storageUtils =new StorageUtils();
        String monday = storageUtils.hebdodate(date);
        //Se connexter à la base de données
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        //Récupérer toutes les informations de l'activités nom du fichier,
    }
}

