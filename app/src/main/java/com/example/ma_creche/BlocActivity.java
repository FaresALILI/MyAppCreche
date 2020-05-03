package com.example.ma_creche;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class BlocActivity extends AppCompatActivity {

    ImageView btnDes,btnAct,btnMus,btnDiv,btnCal,btnHist;
    TextView txtDes,txtAct,txtMus,txtDiv,txtCal,txtHist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc);
        this.btnAct=findViewById(R.id.imageViewActMan);
        this.btnDes=findViewById(R.id.imageViewDessin);
        this.btnHist=findViewById(R.id.imageViewHist);
        this.btnMus=findViewById(R.id.imageViewMus);
        this.btnCal=findViewById(R.id.imageViewCalcul);
        this.btnDiv=findViewById(R.id.imageViewDivers);

        this.txtDes=findViewById(R.id.textViewDessin);

        int i= 2;//2 pour une notification dessin
        this.btnDes.setBackgroundColor(Color.argb(120,120,120,120));
        this.btnDes.setBackgroundColor(Color.DKGRAY);

        this.txtDes.setText(this.txtDes.getText()+" (3)");

        //Générer une vibration
        Vibrator vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(2000);
     }

}
