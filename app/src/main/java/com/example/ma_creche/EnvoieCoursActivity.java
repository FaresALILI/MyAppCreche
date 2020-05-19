package com.example.ma_creche;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ma_creche.dao.FichierDistant;
import com.example.ma_creche.utils.CategirieUser;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.HashMap;

public class EnvoieCoursActivity extends AppCompatActivity {
int PDF=0;
int DOCX =1;
int AUDIO=2;
    int VIDEO= 3;
        private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Uri uri;
StorageReference mStorage;
    Button btnDecon;
    Button btnEnvoiCours;
    Button btnSelectFile;
    Button btnUpload;
    RadioGroup typeActivity;
    RadioButton selectedActivity;
    CategirieUser cat;
    EditText editTextDesc;
    TextView txtVwNotification;
    ProgressDialog progressDialog;
    private static final int PICK_FILE=1;
    ArrayList<Uri> FilList=new ArrayList<Uri>();
    StorageUtils storageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_cours);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                }
            } else {
            }

        }
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing Please Wait.....");
        this.editTextDesc = findViewById(R.id.editTextDescription);
        this. btnDecon= findViewById(R.id.buttonDeconnexion);
        this.btnEnvoiCours =  findViewById(R.id.buttonVlider);
        this.btnUpload =  findViewById(R.id.buttonUpload);
        this.btnSelectFile =  findViewById(R.id.buttonSelectFile);
        this.typeActivity =  findViewById(R.id.typeActivity);
        int radioId= typeActivity.getCheckedRadioButtonId();
        this.selectedActivity =findViewById(radioId);

        mStorage = FirebaseStorage.getInstance().getReference();
        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(v);
            }
        });

        this.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  uploadFile(v);
            }
        });

        this.txtVwNotification =  findViewById(R.id.textViewNotification);
        this.btnEnvoiCours.setOnClickListener(v->{
            if(this.editTextDesc.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez saisir une description",Toast.LENGTH_LONG).show();
            }
            else{
                //Envoi du cours ....
            }
        });

        this.btnDecon.setOnClickListener((View v)->{
                    this.cat.deconnexion();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }

    public void checkButton(View view){
        int radioId= typeActivity.getCheckedRadioButtonId();
        selectedActivity =findViewById(radioId);
    }

    public void selectFile(View view){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        checkButton(view);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
       // startActivityForResult(intent,PICK_FILE);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select file"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
        if (requestCode==PDF){
                if (data.getData()!= null){
                uri=data.getData();
                System.out.println(uri.toString());
                upload();
                }
           else if (requestCode==DOCX){
                if (data.getData()!= null){
                    uri=data.getData();
                    System.out.println(uri.toString());
                    upload();
                }
               else if (requestCode==AUDIO) {
                    if (data.getData() != null) {
                        uri = data.getData();
                        System.out.println(uri.toString());
                        upload();
                    } else if (requestCode == VIDEO) {
                        if (data.getData() != null) {
                            uri = data.getData();
                            System.out.println(uri.toString());
                            upload();
                        }
                    }
                }
           }
            }
        }
    }

    private void upload() {
        String storage=selectedActivity.getText().toString();

        StorageReference folder=mStorage.child("ResMyAppCreche").child(storage).child(uri.getLastPathSegment());
        try{
        folder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                     @Override
                                                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                       uri = taskSnapshot.getUploadSessionUri();
                                                         Toast.makeText(getApplicationContext(), uri.toString(),Toast.LENGTH_LONG).show();


                                                     }
                                                 }
            );
    }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Erreur lors de l'envoi du fichier"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public String hebdodate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int mondayNo = c.get(Calendar.DAY_OF_MONTH)-c.get(Calendar.DAY_OF_WEEK)+2;
        c.set(Calendar.DAY_OF_MONTH,mondayNo);
        int d=c.get(c.MONTH)+1;
        return c.get(Calendar.DAY_OF_MONTH)+"_"+d+"_"+c.get(Calendar.YEAR);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

}
