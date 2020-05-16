package com.example.ma_creche;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

import com.example.ma_creche.dao.FichierDistant;
import com.example.ma_creche.utils.CategirieUser;
import com.example.ma_creche.utils.StorageUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.HashMap;

public class EnvoieCoursActivity extends AppCompatActivity {

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
        storageUtils =new StorageUtils();
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
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        checkButton(view);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent,PICK_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_FILE){
            if(resultCode==RESULT_OK){
                if (data.getClipData()!= null){
                    int count=data.getClipData().getItemCount();
                    int i=0;
                    while (i<count){
                        Uri File=data.getClipData().getItemAt(i).getUri();
                        FilList.add(File);
                        i++;
                    }
                    Toast.makeText(this,"You have selected "+ FilList.size()+" Files", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void uploadFile(View view){
        progressDialog.show();
        Toast.makeText(this,"if takes time , you can press Again",Toast.LENGTH_SHORT).show();
        for (int j=0;j<FilList.size();j++){
            Uri PerFile=FilList.get(j);
            String storage=selectedActivity.getText().toString();
            //StorageReference folder=FirebaseStorage.getInstance().getReference().child("ResMyAppCreche").child(selectedActivity.getText().toString());
            StorageReference folder=FirebaseStorage.getInstance().getReference().child("ResMyAppCreche").child(selectedActivity.getText().toString()).child(storageUtils.hebdodate());
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
                            progressDialog.dismiss();
                            FilList.clear();
                        }
                    });
                }
            });
        }
    }
}
