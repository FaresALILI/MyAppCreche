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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ma_creche.utils.CategirieUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
   // FirebaseDatabase database;
    ProgressDialog progressDialog;
    InputStream image;
    private static final int PICK_FILE=1;
    ArrayList<Uri> FilList=new ArrayList<Uri>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_cours);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing Please Wait.....");

        this.editTextDesc = findViewById(R.id.editTextDescription);
        this. btnDecon= findViewById(R.id.buttonDeconnexion);
        this.btnEnvoiCours =  findViewById(R.id.buttonEnvoyer);
        this.btnUpload =  findViewById(R.id.buttonUpload);
        this.btnSelectFile =  findViewById(R.id.buttonSelectFile);

        storage= FirebaseStorage.getInstance();//return une instance de firebase storage
       // database=FirebaseDatabase.getInstance();//return une instance de firebase Database

        this.btnSelectFile.setOnClickListener(new View.OnClickListener() {
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
/*
        this.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
*/
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
/*
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
    }*/

    public void selectFile(View view){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent,PICK_FILE);
        System.out.println("test dans select");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("etap1");
        if (requestCode==PICK_FILE){
            System.out.println("etap2");
            if(resultCode==RESULT_OK){
                System.out.println("etap3");
                if (data.getClipData()!= null){
                    int count=data.getClipData().getItemCount();
                    System.out.println("cout: "+count);
                    int i=0;
                    while (i<count){
                        Uri File=data.getClipData().getItemAt(i).getUri();
                        FilList.add(File);
                        i++;
                    }
                    System.out.println("test dans onActivity");
                    Toast.makeText(this,"You have selected "+ FilList.size()+" Files", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    public void uploadFile(View view){
        progressDialog.show();
        System.out.println("test dans upload");
        Toast.makeText(this,"if takes time , you can press Again",Toast.LENGTH_SHORT).show();
        System.out.println("mytest:  "+FilList.size());
        for (int j=0;j<FilList.size();j++){
            Uri PerFile=FilList.get(j);
            StorageReference folder=FirebaseStorage.getInstance().getReference().child("ResMyAppCreche");
            StorageReference filename=folder.child("file"+PerFile.getLastPathSegment());
            System.out.println("montest:  "+PerFile.toString());
            filename.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("user");
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("link",String.valueOf(uri));
                            databaseReference.push().setValue(hashMap);
                            System.out.println("test dans upload");
                            progressDialog.dismiss();
                            FilList.clear();
                        }
                    });
                }
            });
        }

    }
}
