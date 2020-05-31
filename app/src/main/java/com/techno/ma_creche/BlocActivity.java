package com.techno.ma_creche;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.techno.ma_creche.utils.CategirieUser;
import com.google.firebase.auth.FirebaseAuth;


public class BlocActivity extends AppCompatActivity {
CategirieUser cat;

    ImageView btnDes,btnAct,btnMus,btnDiv,btnCal,btnHist;
    TextView txtDes,txtAct,txtMus,txtDiv,txtCal,txtHist,textViewClasse;
    Button btnNouveauCour,btnDecon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc);
        this.btnAct=findViewById(R.id.imageViewActMan);
        this.btnDes=findViewById(R.id.imageViewDessin);
        this.textViewClasse = findViewById(R.id.textViewClasse);
        this.btnDecon = findViewById(R.id.buttonDeconnexion);
        btnNouveauCour= findViewById(R.id.buttonNouveauCours);
        String currentClasse = getIntent().getStringExtra("currentClasse");
        this.textViewClasse.setText(currentClasse);
        if (cat.categorie.equals("enseignant")) {
            this.btnNouveauCour.setVisibility(View.VISIBLE);
            this.btnNouveauCour.setOnClickListener((View v)-> {
                Intent intent = new Intent(this, EnvoieCoursActivity.class);
                System.out.println("test dans btnNouveauCour ");
                startActivity(intent);
            });
        }
        else{
            btnNouveauCour.setVisibility(View.INVISIBLE);
        }
        this.btnDes.setOnClickListener((View v)->
                {
                    if (cat.categorie.equals("enseignant")) {
                        Intent intent = new Intent(this, AuthentificationActivity.class);
                        intent.putExtra("categorie", "enseignant");
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("categorie", "parent");
                        startActivity(intent);
                    }

                });


        this.btnDecon.setOnClickListener((View v)->
        {
          //this.cat.deconnexion();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        );
        this.btnHist=findViewById(R.id.imageViewHist);
        this.btnMus=findViewById(R.id.imageViewMus);
        this.btnCal=findViewById(R.id.imageViewCalcul);
        this.btnDiv=findViewById(R.id.imageViewDivers);

        this.txtDes=findViewById(R.id.textViewDessin);

        int i= 2;//2 pour une notification dessin
        this.btnDes.setBackgroundColor(Color.BLUE);
        this.txtDes.setTextSize(19);
        this.txtDes.setText(this.txtDes.getText()+" (3)");

        //Générer une vibration
        /*Vibrator vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(2000);
*/
        this.btnDes.setOnClickListener(v->
        {
                Intent intent = new Intent(this, HorsMusiqueActivity.class);
                intent.putExtra("categorie", "enseignant");
                intent.putExtra("activite", "dessin");
                startActivity(intent);
        });

        this.btnHist.setOnClickListener(v->
        {
            Intent intent = new Intent(this, HorsMusiqueActivity.class);
            intent.putExtra("categorie", "enseignant");
            intent.putExtra("activite", "histoire");
            startActivity(intent);
        });

        this.btnAct.setOnClickListener(v->
        {
            Intent intent = new Intent(this, HorsMusiqueActivity.class);
            intent.putExtra("categorie", "enseignant");
            intent.putExtra("activite", "activite manuelle");
            startActivity(intent);
        });

        this.btnCal.setOnClickListener(v->
        {
            Intent intent = new Intent(this, HorsMusiqueActivity.class);
            intent.putExtra("categorie", "enseignant");
            intent.putExtra("activite", "calcul");
            startActivity(intent);
        });

        this.btnDiv.setOnClickListener(v->
        {
            System.out.println("je suis dans la partie dessin enseignant bloc"  );
            Intent intent = new Intent(this, HorsMusiqueActivity.class);
            intent.putExtra("categorie", "enseignant");
            intent.putExtra("activite", "divers");
            startActivity(intent);
        });

        this.btnMus.setOnClickListener(v->
        {
            System.out.println("je suis dans la partie dessin enseignant bloc"  );
            Intent intent = new Intent(this, HorsMusiqueActivity.class);
            intent.putExtra("categorie", "enseignant");
            intent.putExtra("activite", "musique");
            startActivity(intent);
        });


     }

}
