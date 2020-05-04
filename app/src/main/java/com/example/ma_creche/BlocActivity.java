package com.example.ma_creche;

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

import java.util.Random;


public class BlocActivity extends AppCompatActivity {

    ImageView btnDes,btnAct,btnMus,btnDiv,btnCal,btnHist;
    TextView txtDes,txtAct,txtMus,txtDiv,txtCal,txtHist;
    Button btnNouveauCour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reccuperer le paramettre catégorie (enseignant ou parent)
        String stringtoBeReceived = getIntent().getExtras().getString("categorie");
        setContentView(R.layout.activity_bloc);
        this.btnAct=findViewById(R.id.imageViewActMan);
        this.btnDes=findViewById(R.id.imageViewDessin);
        btnNouveauCour= findViewById(R.id.buttonNouveauCour);
        if (stringtoBeReceived.equals("enseignant")) {
            btnNouveauCour.setVisibility(View.VISIBLE);
        }
        else{
            btnNouveauCour.setVisibility(View.INVISIBLE);
        }
        this.btnDes.setOnClickListener((View v)->
                {
                    if (stringtoBeReceived.equals("enseignant")) {
                        Intent intent = new Intent(this, AuthentificationActivity.class);
                        intent.putExtra("categorie", "enseignant");
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("categorie", "parent");
                        startActivity(intent);
                    }

                System.out.println("image testttt");
                });
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
        Vibrator vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(2000);

        this.btnDes.setOnClickListener(v->
        {

                System.out.println("je suis dans la partie dessin enseignant bloc"  );
                Intent intent = new Intent(this, HorsMusiqueActivity.class);
                intent.putExtra("categorie", "enseignant");
                startActivity(intent);


        });
     }

}
