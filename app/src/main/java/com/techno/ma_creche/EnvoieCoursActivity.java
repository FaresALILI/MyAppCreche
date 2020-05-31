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

    FirebaseAuth fireAuth;

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
               //uploadFile(v);
            }
        });
        fireAuth = FirebaseAuth.getInstance();


        this.txtVwNotification =  findViewById(R.id.textViewNotification);
        this.btnEnvoiCours.setOnClickListener(v->{
            if(this.editTextDesc.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez saisir une description",Toast.LENGTH_LONG).show();
            }
            else{
                //Envoi du cours ....
                envoieCours(v);
            }
        });


        this.btnDecon.setOnClickListener((View v)->
                {
                    /*
                    //this.cat.deconnexion();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    */
                    //download("Dessin");
                }
        );
    }

    private void envoieCours(View v) {
        // uploadFile
        int radioId= typeActivity.getCheckedRadioButtonId();
        this.selectedActivity =findViewById(radioId);
        String storage=selectedActivity.getText().toString();
        StorageReference folder=mStorage.child("ResMyAppCreche").child(storage).child(uri.getLastPathSegment());
        try{
            //envoie
            folder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //ecriture dans la bdd
                    uri = taskSnapshot.getUploadSessionUri();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                    Date date=new Date();
                    System.out.println("mon uri 1 ="+taskSnapshot.getMetadata().getContentType());
                    System.out.println("mon uri 3 ="+taskSnapshot.getStorage().getPath());
                    System.out.println("mon uri 4 ="+taskSnapshot.getStorage().getName());

                    System.out.println("mon uri 6 ="+taskSnapshot.getStorage().getParent().getStream().toString());
                    System.out.println("mon uri 6 ="+taskSnapshot.getStorage().getParent().getName());
                    System.out.println("mon uri 6 ="+taskSnapshot.getStorage().getParent().getDownloadUrl().toString());
                    System.out.println("mon uri 2 ="+taskSnapshot.getMetadata().getContentLanguage());
                    System.out.println("mon uri 2 ="+taskSnapshot.getMetadata().getSizeBytes()/1000 +"Mo");


//                        System.out.println("mon ddduri 2 ="+taskSnapshot.getMetadata().getReference().getDownloadUrl().getResult().toString());
                        //System.out.println("mon ddduri 2 ="+taskSnapshot.getStorage().getDownloadUrl().getResult().toString());
                    System.out.println("mon uri 2 ="+taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                   // System.out.println("mon uri 2 ="+folder.getMetadata().getResult().toString());

                    System.out.println("Test FFFF**:"+taskSnapshot.getMetadata().getName()+"   "+taskSnapshot.getMetadata().getSizeBytes()+"  "+taskSnapshot.getMetadata().getContentType());
                    Toast.makeText(getApplicationContext(), uri.toString(),Toast.LENGTH_LONG).show();
                    // saisie dans la BDD
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("activites");

                    System.out.println("Test FFFF**:"+taskSnapshot.getMetadata().getName()+"   "+taskSnapshot.getMetadata().getSizeBytes()+"  "+taskSnapshot.getMetadata().getContentType());
					HashMap<String,String> hashMap=new HashMap<>();

/*					HashMap<String,Object> hashMap=new HashMap<>();

                    hashMap.put("dateActivity",String.valueOf(format.format(date)));
                    hashMap.put("description", String.valueOf(editTextDesc.getText()));
                    hashMap.put("typeActivity", String.valueOf(selectedActivity.getText()));
                    hashMap.put("etat", false);
                    hashMap.put("nomFile", String.valueOf(uri));
                    hashMap.put("link", String.valueOf(uri));
                    databaseReference.push().setValue(hashMap);
*/

                    MyActivite myActivite =new MyActivite();
                    myActivite.setDateActivity(String.valueOf(format.format(date)));
                    myActivite.setDescription(editTextDesc.getText().toString());
                    myActivite.setEtat(false);

                   // ArrayList<String> fil=new ArrayList<>();
                   // ArrayList<FichierDistant> fil=new ArrayList<>();

                    //   FichierDistant f1 =new FichierDistant();

                    ArrayList<FichierDistant> fil=new ArrayList<>();
                    FichierDistant f1 =new FichierDistant();
                    f1.setNomFile(taskSnapshot.getMetadata().getName());
                    f1.setExtFile(taskSnapshot.getMetadata().getContentType().toString());
                    f1.setLink("www.link.comfff à faire dynamiquement si possible");
                    f1.setTypeActivity(String.valueOf(selectedActivity.getText()));
                    FichierDistant f2 =new FichierDistant();
                    //f2.setDescription(editTextDesc.getText().toString());
                    f2.setExtFile(".pdf");
                    f2.setLink("www.link2.com");
                    f2.setTypeActivity("Dessin");

                    fil.add(f1);
                    fil.add(f2);


                    myActivite.setListFiles(fil);
                    databaseReference.push().setValue(myActivite);
                }
            }
            );
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Erreur lors de l'envoi du fichier"+e.getMessage(),Toast.LENGTH_LONG).show();
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
       // startActivityForResult(intent,PICK_FILE);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select file"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode==RESULT_OK){
            System.out.println("test11 toString"+data.getData().toString());
            System.out.println("test22 toString"+data.getType());
//           System.out.println("test33 toString"+data.getClipData().getDescription());
//            System.out.println("test44 toString"+data.getClipData().toString());

//            System.out.println("test3 toString"+data.getClipData().getItemAt(0).getUri());

        if (requestCode==PDF){
                if (data.getData()!= null){
                  //  System.out.println( "faresssss"+data.getClipData().gettUri().getLastPathSegment().toString());
                uri=data.getData();
                System.out.println(uri.toString());
               // upload();
                }
           else if (requestCode==DOCX){
                if (data.getData()!= null){
                   // System.out.println( "faresssss"+data.getClipData().getItemAt(0).getUri().getLastPathSegment().toString());
                    uri=data.getData();
                    System.out.println("test1 toString"+uri.toString());
                    System.out.println("test2 toString"+data.getType());

                   // System.out.println("test3 toString"+data.getClipData().getItemAt(0).getUri());


                   // upload();
                }
               else if (requestCode==AUDIO) {
                    if (data.getData() != null) {
                      //  System.out.println( "faresssss"+data.getClipData().getItemAt(0).getUri().getLastPathSegment().toString());
                        uri = data.getData();
                        System.out.println("test1 toString"+uri.toString());
                        System.out.println("test2 toString"+data.getType());
                     //   System.out.println("test3 toString"+data.getClipData().getItemAt(0).getUri());
                        System.out.println(uri.toString());
                       // upload();
                    } else if (requestCode == VIDEO) {
                        if (data.getData() != null) {
                         //   System.out.println( "faresssss"+data.getClipData().getItemAt(0).getUri().getLastPathSegment().toString());
                            uri = data.getData();
                            System.out.println("test1 toString"+uri.toString());
                            System.out.println("test2 toString"+data.getType());

                  //          System.out.println("test3 toString"+data.getClipData().getItemAt(0).getUri());

                            System.out.println(uri.toString());
                            //upload();
                        }
                    }
                }
           }


        }
        }
    }
/*
    private void upload() {
        String storage=selectedActivity.getText().toString();

        StorageReference folder=mStorage.child("ResMyAppCreche").child(storage).child(uri.getLastPathSegment());
        try{
        folder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                     @Override
                                                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                       uri = taskSnapshot.getUploadSessionUri();
                                                         Toast.makeText(getApplicationContext(), uri.toString(),Toast.LENGTH_LONG).show();
                                                         System.out.println("mon uri 1 ="+uri.getPath());
                                                         System.out.println("mon uri 2 ="+uri.getLastPathSegment());
                                                         System.out.println("mon uri 3 ="+uri.toString());

                                                         //System.out.println("mon uri 2 ="+uri.get());


                                                     }
                                                 }
            );
    }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Erreur lors de l'envoi du fichier"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
*/





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
