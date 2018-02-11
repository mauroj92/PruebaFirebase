package com.example.maurojuarez.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtCantidad;
    TextView txvCantidad;
    Button btnCantidad;

    DatabaseReference referencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        txvCantidad = (TextView) findViewById(R.id.txvCantidad);
        btnCantidad = (Button) findViewById(R.id.btnCantidad);
        btnCantidad.setOnClickListener(this);

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
