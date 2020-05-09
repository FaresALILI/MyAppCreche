package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ma_creche.utils.CategirieUser;

public class AuthentificationActivity extends AppCompatActivity {
Button btnAuth;
EditText editlogin;
EditText editmotpass;
TextView textCreateAccount;
CategirieUser cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        btnAuth = findViewById(R.id.butonConnect);
        textCreateAccount = findViewById(R.id.textViewCreateAccount);
        btnAuth.setOnClickListener(v->{
            Intent intent= new Intent(this, BlocActivity.class);
            editlogin=findViewById(R.id.editLogin);
            editmotpass=findViewById(R.id.editPass);
            cat.login=editlogin.getText().toString();
            cat.motpass=editmotpass.getText().toString();

            if(!cat.login.isEmpty() &&(!cat.motpass.isEmpty()))
            startActivity(intent);
            else
                Toast.makeText(getApplicationContext(), "Veuillez-vous authentifier", Toast.LENGTH_LONG).show();
        });

        //Aller à l'écran de création d'un compte

        textCreateAccount.setOnClickListener(v->{
            Intent intent= new Intent(this, CreationCompteActivity.class);
                startActivity(intent);

        });
    }
}
