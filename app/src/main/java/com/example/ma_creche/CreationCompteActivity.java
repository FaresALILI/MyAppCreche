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
            String login=editlogin.getText().toString();
            String pass=editPassword.getText().toString();
            String passConfirm=editPasswordConfirm.getText().toString();

            if(TextUtils.isEmpty(login)) {
                this.editlogin.setError("le login doit etre renseigné");
                return;
            }
            else if(pass.length()<6){
                this.editPassword.setError("Au moins 6 caractèreé");
                return;
            }
            else if(!pass.equals(passConfirm)){
                this.editPasswordConfirm.setError("Les mots de passes doivent etre identiques");
                return;
            }

            fireAuth.createUserWithEmailAndPassword(login,pass).addOnCompleteListener(task->{
                    if(task.isSuccessful()){
                    Toast.makeText(this,"compte créé",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),BlocActivity.class));
                    }
                    else{
                        Toast.makeText(this,"Erreur lors de la création de votre compte"+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                    }

            );
        });

    }
}
