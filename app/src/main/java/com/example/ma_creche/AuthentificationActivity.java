package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AuthentificationActivity extends AppCompatActivity {
Button btnAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        System.out.println("je suis dans la partie authentification");
        btnAuth= findViewById(R.id.butonConnect);
        btnAuth.setOnClickListener(v->{
            Intent intent= new Intent(this, BlocActivity.class);
            startActivity(intent);
        });
    }
}
