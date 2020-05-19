package com.example.ma_creche;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ma_creche.dao.MyActivite;
import com.example.ma_creche.utils.CategirieUser;
import com.example.ma_creche.utils.StorageUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HorsMusiqueActivity extends AppCompatActivity {

    Button btnDecon;
    CategirieUser cat;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hors_musique);
        calendarView = findViewById(R.id.calendarView);
        ListView sp = (ListView) findViewById(R.id.listViewMusic);
        this.btnDecon = findViewById(R.id.buttonDeconnexion);
        TextView textView =findViewById(R.id.textViewNomAct);
        textView.setText("Catgorie de l'activité à récupérer");


        //textView.setText(getIntent().getStringExtra("nomActivity"));

       // th.start();
        calendarView.setOnDateChangeListener((cal,y,m,d)->{
            String date = "date = "+ d+"_"+(m+1)+"_"+y;
            getListeCours(date);
            System.out.println("date = "+ d+"_"+(m+1)+"_"+y);

            MyActivite myActivite1= new MyActivite("15_05_2020","ma première activité");
            MyActivite myActivite2= new MyActivite("05_04_2020","ma deuxième activité");
            MyActivite myActivite3= new MyActivite("12/07/2019","ma troisième activité");
            String nomActivity1=myActivite1.getDateActivity()+"  "+myActivite1.getDescription();
            String nomActivity2=myActivite2.getDateActivity()+"  "+myActivite2.getDescription();
            String nomActivity3=myActivite3.getDateActivity()+"  "+myActivite3.getDescription();

            String[] myActivities = new String[]{nomActivity1,nomActivity2, nomActivity3};

            ArrayAdapter<String> arrayAdapter
                    = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , myActivities);


            sp.setAdapter(arrayAdapter);

        });

        sp.setOnItemClickListener((parent,  view,  position,  id)->{
            System.out.println("--->adam + "+sp.getItemAtPosition(position).toString());
            Intent intent = new Intent(this, AffichageCoursActivity.class);
            startActivity(intent);

        });

        //ssp.setAdapter(model);
        this.btnDecon.setOnClickListener((View v)->
                {
                    this.cat.deconnexion();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
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

