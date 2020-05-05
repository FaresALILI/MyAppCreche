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
        setContentView(R.layout.activity_authentification);
        btnAuth= findViewById(R.id.butonConnect);
        btnAuth.setOnClickListener(v->{
            Intent intent= new Intent(this, BlocActivity.class);
            editlogin=findViewById(R.id.editLogin);
            editmotpass=findViewById(R.id.editPass);
            cat.login=editlogin.getText().toString();
            cat.motpass=editmotpass.getText().toString();

            startActivity(intent);
        });
    }
}
