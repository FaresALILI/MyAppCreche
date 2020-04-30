package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button butonEnseignant;
    Button butonParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

               this.butonEnseignant= findViewById(R.id.butonEnseignant);
               this.butonParent=findViewById(R.id.buttonParent);

               this.butonParent.setOnClickListener((View v)->{
                 Intent intent= new Intent(this, AuthentificationActivity.class);
                 intent.putExtra("categorie","parent");
                   startActivity(intent);
                   System.out.println("salut Parent...");
        });
             this.butonEnseignant.setOnClickListener((View v)->
                {
               Intent intent = new Intent(this,AuthentificationActivity.class);
               intent.putExtra("categorie","enseignant");
               startActivity(intent);
                System.out.println("salut ....");
                }

                );


    }
}
