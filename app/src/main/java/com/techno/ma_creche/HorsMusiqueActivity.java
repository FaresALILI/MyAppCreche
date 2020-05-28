package com.techno.ma_creche;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.annotation.NonNull;

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
import com.techno.ma_creche.dao.MyActivite;
import com.techno.ma_creche.utils.CategirieUser;
import com.techno.ma_creche.utils.StorageUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HorsMusiqueActivity extends AppCompatActivity {

    Button btnDecon;
    CategirieUser cat;
    CalendarView calendarView;

    DatabaseReference databaseReference;
    List<MyActivite> activities ;
    String[] myActivities;
    MyActivite myActivity;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hors_musique);
        calendarView = findViewById(R.id.calendarView);

        ListView sp = (ListView) findViewById(R.id.listViewMusic);
        this.btnDecon = findViewById(R.id.buttonDeconnexion);
        TextView textView = findViewById(R.id.textViewNomAct);
        textView.setText(getIntent().getStringExtra("activite"));

        // th.start();
        calendarView.setOnDateChangeListener((cal, y, m, d) -> {
            String date = "date = " + d + "_" + (m + 1) + "_" + y;
            getListeCours(date);
            System.out.println("date = " + d + "_" + (m + 1) + "_" + y);

            MyActivite myActivite1 = new MyActivite("15_05_2020", "ma première activité");
            MyActivite myActivite2 = new MyActivite("05_04_2020", "ma deuxième activité");
            MyActivite myActivite3 = new MyActivite("12/07/2019", "ma troisième activité");
            String nomActivity1 = myActivite1.getDateActivity() + "  " + myActivite1.getDescription();
            String nomActivity2 = myActivite2.getDateActivity() + "  " + myActivite2.getDescription();
            String nomActivity3 = myActivite3.getDateActivity() + "  " + myActivite3.getDescription();

            String[] myActivities = new String[]{nomActivity1, nomActivity2, nomActivity3};

            ArrayAdapter<String> arrayAdapter
                    = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myActivities);


            sp.setAdapter(arrayAdapter);

        });


        sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("--->" + myActivities[position]);
                Intent intent = new Intent(HorsMusiqueActivity.this, AffichageCoursActivity.class);
                intent.putExtra("currentActivity", myActivities[position]);
                startActivity(intent);
            }
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

