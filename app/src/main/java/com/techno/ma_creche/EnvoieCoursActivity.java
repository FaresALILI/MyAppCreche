package com.techno.ma_creche;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techno.ma_creche.dao.FichierDistant;
import com.techno.ma_creche.dao.MyActivite;
import com.techno.ma_creche.utils.CategirieUser;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class EnvoieCoursActivity extends AppCompatActivity {
    int PDF=0;
    int DOCX =1;
    int AUDIO=2;
    int VIDEO= 3;
    int j;
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
    EditText editTextObjet;
    TextView txtVwNotification;
    ProgressDialog progressDialog;
    private static final int PICK_FILE=1;
    ArrayList<Uri> FilList=new ArrayList<Uri>();
    FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_cours);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                } else {
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
        this.editTextObjet = findViewById(R.id.editTextObjet);
        this. btnDecon= findViewById(R.id.buttonDeconnexion);
        this.btnEnvoiCours =  findViewById(R.id.buttonVlider);
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

        fireAuth = FirebaseAuth.getInstance();
        this.txtVwNotification =  findViewById(R.id.textViewNotification);
        this.btnEnvoiCours.setOnClickListener(v->{
            if(this.editTextDesc.getText().toString().isEmpty() || this.editTextObjet.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Merci de remplire  les parties ayant *",Toast.LENGTH_LONG).show();
            }
            else{
                     envoieCours(v);
            }
        });

        this.btnDecon.setOnClickListener((View v)->
                {
                    this.cat.deconnexion();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }

    private void envoieCours(View v) {
        int radioId= typeActivity.getCheckedRadioButtonId();
        this.selectedActivity =findViewById(radioId);
        String storage=selectedActivity.getText().toString();
        ArrayList<FichierDistant> fil = new ArrayList<>();
        for (j=0;j<FilList.size();j++) {
            Uri PerFile = FilList.get(j);
            StorageReference folder = mStorage.child("ResMyAppCreche").child(storage).child(PerFile.getLastPathSegment());
            folder.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                       Date date = new Date();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("activites");
                        System.out.println("Test FFFF**:" + taskSnapshot.getMetadata().getName() + "   " + taskSnapshot.getMetadata().getSizeBytes() + "  " + taskSnapshot.getMetadata().getContentType());
                        MyActivite myActivite = new MyActivite();
                        myActivite.setDateActivity(String.valueOf(format.format(date)));
                        myActivite.setObjet(editTextObjet.getText().toString());
                        myActivite.setDescription(editTextDesc.getText().toString());
                        myActivite.setEtat(false);
                        FichierDistant f1 = new FichierDistant();
                        f1.setNomFile(taskSnapshot.getMetadata().getName());
                        f1.setExtFile(taskSnapshot.getMetadata().getContentType().toString());
                        f1.setLink("www.link.comfff à faire dynamiquement si possible");
                        f1.setTypeActivity(String.valueOf(selectedActivity.getText()));
                        fil.add(f1);
                        if (j== fil.size()) {
                        myActivite.setListFiles(fil);
                        databaseReference.push().setValue(myActivite);}
                    }
            });
        }
    }

    public void checkButton(View view){
        int radioId= typeActivity.getCheckedRadioButtonId();
        selectedActivity =findViewById(radioId);
    }

    public void selectFile(View view){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        checkButton(view);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select file"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (data.getClipData()!= null){
                int count=data.getClipData().getItemCount();
                int i=0;
                while (i<count){
                    Uri File=data.getClipData().getItemAt(i).getUri();
                    FilList.add(File);
                    i++;
                }
                System.out.println("Test FilList.size()"+FilList.size());
                Toast.makeText(this,"You have selected "+ FilList.size()+" Files", Toast.LENGTH_LONG).show();
            }
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
