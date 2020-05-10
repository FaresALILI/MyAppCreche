package com.example.ma_creche;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ma_creche.utils.CategirieUser;
import com.google.firebase.auth.FirebaseAuth;

public class AuthentificationActivity extends AppCompatActivity {
Button btnAuth;
EditText editlogin;
EditText editmotpass;
TextView textCreateAccount;
TextView  textViewReinitPassword;
CategirieUser cat;
FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        fireAuth = FirebaseAuth.getInstance();
        btnAuth = findViewById(R.id.butonbtnRegister);
        textCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewReinitPassword = findViewById(R.id.textViewReinitPassword);

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
