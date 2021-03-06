package com.example.maurojuarez.firebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtCantidad;
    TextView txvCantidad;
    Button btnCantidad;
    ImageView imvFoto;

    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imvFoto = (ImageView) findViewById(R.id.imvFoto);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        txvCantidad = (TextView) findViewById(R.id.txvCantidad);
        btnCantidad = (Button) findViewById(R.id.btnCantidad);
        btnCantidad.setOnClickListener(this);

        //Prueba de DATABASE
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        referencia = database.getReference("persona");

        referencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int valor = dataSnapshot.getValue(Integer.class);
                Log.i("DATO", valor + "");
                txvCantidad.setText(String.valueOf(valor));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Prueba de STORAGE
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://probando-firebase-50610.appspot.com").child("ANDROID.png");
        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imvFoto.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCantidad:
                int valor = Integer.parseInt(edtCantidad.getText().toString());
                referencia.setValue(valor);
                break;
        }
    }
}
