package com.example.ma_creche;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ma_creche.utils.CategirieUser;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EnvoieCoursActivity extends AppCompatActivity {

    Button btnDecon;
    Button btnEnvoiCours;
    Button btnSelectFile;
    Button btnUpload;
    CategirieUser cat;
    EditText editTextDesc;
    TextView txtVwNotification;
    Uri pdfUri;//Les Uri sont en fait des URL destinées au stockage local.
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    InputStream image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_cours);

        this.editTextDesc = findViewById(R.id.editTextDescription);
        this. btnDecon= findViewById(R.id.buttonDeconnexion);
        this.btnEnvoiCours =  findViewById(R.id.buttonEnvoyer);
        this.btnUpload =  findViewById(R.id.buttonUpload);
        this.btnSelectFile =  findViewById(R.id.buttonSelectFile);

        storage= FirebaseStorage.getInstance();//return une instance de firebase storage
        database=FirebaseDatabase.getInstance();//return une instance de firebase Database

        this.btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EnvoieCoursActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPDF();
                }
                else{
                    ActivityCompat.requestPermissions(EnvoieCoursActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        this.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri!=null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(EnvoieCoursActivity.this, "Selectionner un Fichier SVP",Toast.LENGTH_SHORT).show();
             }
        });

        this.txtVwNotification =  findViewById(R.id.textViewNotification);
        this.btnEnvoiCours.setOnClickListener(v->{
            if(this.editTextDesc.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Veuillez saisir une description",Toast.LENGTH_LONG).show();
            }
            else{
                //Envoi du cours ....
            }
        });


        this.btnDecon.setOnClickListener((View v)->
                {
                    this.cat.deconnexion();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }

    private void uploadFile(Uri pdfUri) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File:...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName=System.currentTimeMillis()+"";
        StorageReference storageReference=storage.getReference();// renvoie un chemin racine
        System.out.println("dans uploadFile : "+fileName+":"+pdfUri);

        //storageReference.child("ResMyAppCreche/"+"calcul.jpg");

        storageReference = storage.getReference().child("ResMyAppCreche/"+"calcul");
        if (image!=null)
        storageReference.putStream(image);

        System.out.println("test 1");
        //storageReference.putStream(image);
        System.out.println("test 2");
        /*
        this.storageRef = storage.getReference().child("images/"+val);
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                      System.out.println("dans onSuccess1 : ");
                      String url=taskSnapshot.getStorage().getDownloadUrl().toString();//return l'URL du fichier telechargé
                        System.out.println("dans onSuccess12222 : "+url);
                      //stocker l'url dans database...
                      DatabaseReference databaseReference=database.getReference();// return the pah to root..

                        System.out.println("dans onSuccess 2 : ");
                        databaseReference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    System.out.println("dans onComplete if: ");
                                    Toast.makeText(EnvoieCoursActivity.this, "File successfuly uploaded", Toast.LENGTH_SHORT).show();
                            }
                                else {
                                    System.out.println("dans onComplete else: ");
                                    Toast.makeText(EnvoieCoursActivity.this, "file not successfuly uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("dans onFailure:  " +e.getStackTrace());
                Toast.makeText(EnvoieCoursActivity.this,"file not successfuly uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                //track the progressof our upload ....
                int currentProgress=(int)(1000*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }
        else{
            Toast.makeText(EnvoieCoursActivity.this,"Merci de donner la permission..", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF() {

        //Intent intent=new Intent(Intent.ACTION_PICK);
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/");
        intent.setType("application/pdf");
        //intent.setAction(Intent.ACTION_GET_CONTENT);//pour récupérer le fichier.
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //vérifier si l'utilisateur a sélectionné le fichier ou non.

        if(requestCode==86 && resultCode==RESULT_OK && data!=null){
            System.out.println("dans onActivityResult if"+"::"+pdfUri+"::"+requestCode+"::"+resultCode+"::"+RESULT_OK);
            pdfUri=data.getData();//return le chemin du fichier selectionné

            System.out.println("dans onActivityResult if apres data"+"::"+pdfUri+"::"+requestCode+"::"+resultCode+"::"+RESULT_OK);

            try {
                image= getContentResolver().openInputStream(pdfUri);

               // InputStream inputStream= getContentResolver().openInputStream(pdfUri);
                //System.out.println("dans onActivityResult if apres data"+"::"+inputStream.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
          //  final Uri imageUri = data.getData();
           // imageStream = getContentResolver().openInputStream(imageUri);

            txtVwNotification.setText("le fichier est selectionné: "+data.getData().getLastPathSegment());
        }
        else {
            System.out.println("Merci de selectionner un fichier"+"::"+pdfUri+"::"+requestCode+"::"+resultCode+"::");
            Toast.makeText(EnvoieCoursActivity.this,"Merci de selectionner un fichier"+"::"+pdfUri+"::"+requestCode+"::"+resultCode+"::", Toast.LENGTH_SHORT).show();
        }
    }
}
