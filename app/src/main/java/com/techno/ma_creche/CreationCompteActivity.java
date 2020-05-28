package com.techno.ma_creche;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techno.ma_creche.utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CreationCompteActivity extends AppCompatActivity {

    Button btnRegister;
    EditText editlogin;
    EditText editPassword;
    EditText editPasswordConfirm;
    String login;
    String pass;
    FirebaseAuth fireAuth;
    FirebaseFirestore firestore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);
        fireAuth = FirebaseAuth.getInstance();
        firestore =FirebaseFirestore.getInstance();

        //Recupération de la saisie
        btnRegister = findViewById(R.id.butonbtnRegister);
        editlogin=findViewById(R.id.editLogin);
        editPassword=findViewById(R.id.editPass);
        editPasswordConfirm=findViewById(R.id.editPassConfirm);

        this.btnRegister.setOnClickListener(v->{
             login=editlogin.getText().toString();
             pass=editPassword.getText().toString();
            String passConfirm=editPasswordConfirm.getText().toString();

            if(TextUtils.isEmpty(login)) {
                this.editlogin.setError("le login doit etre renseigné");
                return;
            }
            else if(!login.contains("@")||(!login.contains("."))) {
                this.editlogin.setError("Veuillez saisir une adresse mail correcte");
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
                    this.userID = fireAuth.getCurrentUser().getUid();

                    //Enregistrement en base de données
                        /***********************************/
                        Toast.makeText(this,"compte créé",Toast.LENGTH_SHORT).show();
                        int lower = 1; int higher = 10000;
                        int random = (int)(Math.random() * (higher-lower)) + lower;
                        this.userID = fireAuth.getCurrentUser().getUid()+random;

                        //Enregistrement en base de données
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("utilisateurs");
                        User us= new User();
                        us.setLogin(login);
                        us.setPassword(pass);
                        us.setDateCreation(new Date());
                        ref.child("utilisateurs").push().setValue(us);
                        Intent intent= new Intent(this, BlocActivity.class);
                        startActivity(intent);
                        /**************************************/
                    }
                    else{
                        Toast.makeText(this,"Erreur lors de la création de votre compte"+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                    }

            );
        });

    }
}
