package com.example.ma_creche;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ma_creche.utils.CategirieUser;
import com.google.firebase.auth.FirebaseAuth;

public class AuthentificationActivity extends AppCompatActivity {
Button btnAuth;
EditText editlogin;
EditText editmotpass;
TextView textCreateAccount;
CategirieUser cat;
FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        fireAuth = FirebaseAuth.getInstance();
        btnAuth = findViewById(R.id.butonbtnRegister);
        textCreateAccount = findViewById(R.id.textViewCreateAccount);
        btnAuth.setOnClickListener(v->{
            Intent intent= new Intent(this, BlocActivity.class);
            editlogin=findViewById(R.id.editLogin);
            editmotpass=findViewById(R.id.editPass);
            cat.login=editlogin.getText().toString();
            cat.motpass=editmotpass.getText().toString();
            String login=editlogin.getText().toString();
            String motpass=editmotpass.getText().toString();

            if(TextUtils.isEmpty(login)) {
                this.editlogin.setError("le login doit etre renseigné");
                return;
            }
            else if(!login.contains("@")||(!login.contains("."))) {
                this.editlogin.setError("Veuillez saisir une adresse mail correcte");
                return;
            }
            else if(motpass.length()<6){
                this.editmotpass.setError("Au moins 6 caractèreé");
                return;
            }
            fireAuth.signInWithEmailAndPassword(login,motpass).addOnCompleteListener(task->{
                if(task.isSuccessful()){
                    Toast.makeText(this,"Connexion réusie",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else
                    Toast.makeText(this,"Connexion échouée",Toast.LENGTH_SHORT).show();
            });

        });

        //Aller à l'écran de création d'un compte
        textCreateAccount.setOnClickListener(v->{
            Intent intent= new Intent(this, CreationCompteActivity.class);
                startActivity(intent);
        });
    }
}
