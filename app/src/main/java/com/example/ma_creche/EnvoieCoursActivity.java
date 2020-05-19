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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class EnvoieCoursActivity extends AppCompatActivity {
int PDF=0;
int DOCX =1;
int AUDIO=2;
    int VIDEO= 3;
        public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 2001;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_cours);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println("la permisssion est refuséeeeee");



            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }

        }
        else
            System.out.println("la permisssion est OKOKOKO");





        if (Build.VERSION.SDK_INT >= 22 && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},110);
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 110);


            //Après ce point, vous attendez le callback dans onRequestPermissionsResult
            checkAndRequestPermissions(); //verifie la permission
        } else {

        }



        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing Please Wait.....");
        this.editTextDesc = findViewById(R.id.editTextDescription);
        this. btnDecon= findViewById(R.id.buttonDeconnexion);
        this.btnEnvoiCours =  findViewById(R.id.buttonEnvoyer);
        this.btnUpload =  findViewById(R.id.buttonUpload);
        this.btnSelectFile =  findViewById(R.id.buttonSelectFile);
        this.typeActivity =  findViewById(R.id.typeActivity);
        int radioId= typeActivity.getCheckedRadioButtonId();
        this.selectedActivity =findViewById(radioId);
        mStorage = FirebaseStorage.getInstance().getReference("Uploads");
        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(v);
            }
        });

        this.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(v);
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
        Intent intent=new Intent(Intent.ACTION_SEND);
        checkButton(view);
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
       Intent in = Intent.createChooser(intent,null);
       startActivity(in);
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
        System.out.println("je suis upload");
        StorageReference folder=mStorage.child(uri.getLastPathSegment());
        try{
        folder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                     @Override
                                                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                         System.out.println("je suis ici dans le succcess");
                                                       uri = taskSnapshot.getUploadSessionUri();
                                                         System.out.println(uri.toString());

                                                     }
                                                 }
            );
    }catch(Exception e)
        {
            System.out.println("  System.out.println(\"je suis ici dans le ko\");"+e.getStackTrace());
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



    private  boolean checkAndRequestPermissions() {
System.out.println("je verifie la permission");
        List<String> listPermissionsNeeded = new ArrayList<>();
        listPermissionsNeeded.clear();
        int contact= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (contact != PackageManager.PERMISSION_GRANTED) {
            System.out.println("je verifie la permission 2 ");

            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 System.out.println(" La permission est garantie");
                } else {
                 System.out.println("La permission est refusée");
                }
                return;
            }
        }
    }

    public void uploadFile(View view){
        Toast.makeText(this,"if takes time , you can press Again",Toast.LENGTH_SHORT).show();
        for (int j=0;j<FilList.size();j++){
            Uri PerFile=FilList.get(j);
            String storage=selectedActivity.getText().toString();
            //StorageReference folder=FirebaseStorage.getInstance().getReference().child("ResMyAppCreche").child(selectedActivity.getText().toString());
            StorageReference folder=FirebaseStorage.getInstance().getReference().child("ResMyAppCreche").child(selectedActivity.getText().toString()).child(hebdodate());
            // StorageReference filename=folder.child(selectedActivity.getText().toString()+System.currentTimeMillis());
            StorageReference filename=folder.child(selectedActivity.getText().toString()+PerFile.getLastPathSegment());
            filename.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            System.out.println("extension = "+PerFile.getLastPathSegment());
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("user");
                            HashMap<String, FichierDistant> hashMap=new HashMap<>();
                            FichierDistant file = new FichierDistant(String.valueOf(uri),
                                                                    String.valueOf(editTextDesc.getText()),
                                                                    String.valueOf(selectedActivity.getText()),
                                                                    String.valueOf(PerFile.getLastPathSegment()));
                            hashMap.put("fichier",file);
                            databaseReference.push().setValue(hashMap);
                            FilList.clear();
                        }
                    });
                }
            });
        }
    }
}
