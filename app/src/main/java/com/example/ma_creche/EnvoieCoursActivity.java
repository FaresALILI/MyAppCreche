package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.ma_creche.utils.CategirieUser;
public class EnvoieCoursActivity extends AppCompatActivity {

    Button btnDecon;
    CategirieUser cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_cours);

        btnDecon= findViewById(R.id.buttonDeconnexion);
        this.btnDecon.setOnClickListener((View v)->
                {
                    this.cat.deconnexion();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }
}
