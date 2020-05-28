package com.techno.ma_creche;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.techno.ma_creche.utils.CategirieUser;

public class MainActivity extends AppCompatActivity {
    Button butonEnseignant;
    Button butonParent;
    CategirieUser cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.butonEnseignant= findViewById(R.id.butonEnseignant);
        this.butonParent=findViewById(R.id.buttonParent);
        this.butonParent.setOnClickListener((View v)->{
            Intent intent= new Intent(this, AuthentificationActivity.class);
            cat.categorie="parent";
            startActivity(intent);
        });
        this.butonEnseignant.setOnClickListener((View v)->{
            Intent intent = new Intent(this,AuthentificationActivity.class);
            cat.categorie="enseignant";
            startActivity(intent);
        });
    }
}
