package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CreationCompteActivity extends AppCompatActivity {

    Button btnRegister;
    EditText editlogin;
    EditText editPassword;
    EditText editPasswordConfirm;
    FirebaseAuth fireAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);
        fireAuth = FirebaseAuth.getInstance();
        //Recupération de la saisie
        btnRegister = findViewById(R.id.butonbtnRegister);
        editlogin=findViewById(R.id.editLogin);
        editPassword=findViewById(R.id.editPass);
        editPasswordConfirm=findViewById(R.id.editPassConfirm);

        this.btnRegister.setOnClickListener(v->{

            if(TextUtils.isEmpty(editlogin.getText()))
                this.editlogin.setError();
            if(!cat.login.isEmpty() &&(!cat.motpass.isEmpty()))
                startActivity(intent);
            else
                Toast.makeText(getApplicationContext(), "Veuillez-vous authentifier", Toast.LENGTH_LONG).show();
        });

    }
}
