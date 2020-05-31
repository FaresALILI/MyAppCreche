package com.techno.ma_creche;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.techno.ma_creche.utils.CategirieUser;
import com.techno.ma_creche.utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class AuthentificationActivity extends AppCompatActivity {
Button btnAuth;
EditText editlogin;
EditText editmotpass;
TextView textCreateAccount;
TextView  textViewReinitPassword;
CategirieUser cat;
FirebaseAuth fireAuth;
FirebaseFirestore firestore;
String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        fireAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnAuth = findViewById(R.id.butonbtnRegister);
        textCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewReinitPassword = findViewById(R.id.textViewReinitPassword);

        btnAuth.setOnClickListener(v->{
            editlogin=findViewById(R.id.editLogin);
            editmotpass=findViewById(R.id.editPass);
            cat.login=editlogin.getText().toString();
            cat.motpass=editmotpass.getText().toString();
           // String login=editlogin.getText().toString();
            //String motpass=editmotpass.getText().toString();
            String   login ="lamine1305@gmail.com";
            String  motpass="1234567";
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

                    /***********************************/
                    Toast.makeText(this,"compte créé",Toast.LENGTH_SHORT).show();
                    int lower = 1; int higher = 10000;
                    int random = (int)(Math.random() * (higher-lower)) + lower;
                    this.userID = fireAuth.getCurrentUser().getUid()+random;


                    //Enregistrement en base de données
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("utilisateurs");
                    User us= new User();
                    us.setLogin("lamine1305@gmail.com");
                    us.setPassword(motpass);
                    us.setDateCreation(new Date());
                    //ref.push().setValue(us);
                    ref.child("utilisateurs").push().setValue(us);
                    /**************************************/


                    Toast.makeText(this,"Connexion réusie",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(this, ChoixSectionActivity.class);
                    startActivity(intent);
                }
          }).addOnFailureListener(e->{
                Toast.makeText(this,"Connexion échouée"+e.getStackTrace(),Toast.LENGTH_SHORT).show();
            });

        });

        //Aller à l'écran de création d'un compte
        textCreateAccount.setOnClickListener(v->{
            Intent intent= new Intent(this, CreationCompteActivity.class);
                startActivity(intent);
        });

        //Réinitialisation du mot de passe
        textViewReinitPassword.setOnClickListener(v->{
            EditText resetEmail = new EditText(v.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Réinitialiser votre mot de passe ?");
            passwordResetDialog.setMessage("Entrer l'adresse mail pour recevoir un lien de réinitialisation");
            passwordResetDialog.setView(resetEmail);
            passwordResetDialog.setPositiveButton("oui", (dialog,  which)-> {
                String mail = resetEmail.getText().toString();
                fireAuth.sendPasswordResetEmail(mail).addOnSuccessListener(v1->{
                    Toast.makeText(AuthentificationActivity.this,"Un lien vient d'etre envoyé sur votre boite mail",Toast.LENGTH_LONG);

                }).addOnFailureListener(e->{
                    Toast.makeText(AuthentificationActivity.this,"Probleme lors de l'envoi du lien de rééinitialisation du mot de passe",Toast.LENGTH_LONG);

                });
            });
            passwordResetDialog.setNegativeButton("non",(dialog, which)-> {

            });
            passwordResetDialog.create().show();
        });
    }
}
