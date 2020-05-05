package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.example.ma_creche.utils.CategirieUser;

public class AuthentificationActivity extends AppCompatActivity {
Button btnAuth;
EditText editlogin;
EditText editmotpass;
CategirieUser cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reccuperer le paramettre catégorie (enseignant ou parent)
        //String stringtoBeReceived = getIntent().getExtras().getString("categorie");
        //cat.categorie=getIntent().getExtras().getString("categorie");

        editlogin=findViewById(R.id.editLogin);
        editmotpass=findViewById(R.id.editPass);

        System.out.println("je suis dans authentification "+cat.categorie);
        setContentView(R.layout.activity_authentification);
        btnAuth= findViewById(R.id.butonConnect);
        btnAuth.setOnClickListener(v->{
            Intent intent= new Intent(this, BlocActivity.class);
           // intent.putExtra("categorie",cat.getCategorie());

            cat.login=editlogin.getText().toString();
            cat.motpass=editmotpass.getText().toString();

            startActivity(intent);
        });
    }
}
