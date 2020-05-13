package com.example.ma_creche;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ma_creche.utils.CategirieUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    StorageReference reference;
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



        this.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
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

    private void download() {
        StorageReference storageReference =storage.getInstance().getReference();
        reference = storageReference.child("davidson-lamine-mehidi.pdf");
        reference.getDownloadUrl().addOnSuccessListener(uri->{
            String url=uri.toString();
            downloadFile(EnvoieCoursActivity.this,"davidson-lamine-mehidi",".pdf", Environment.DIRECTORY_DOWNLOADS,url);
        }).addOnFailureListener(e->{
        });

    }
    private void downloadFile(Context context, String fileName, String extension, String destination, String url) {
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request =new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destination,fileName+extension);
        downloadManager.enqueue(request);
    }





}
