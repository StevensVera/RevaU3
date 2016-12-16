package com.example.stevensvera.revaluacionu3;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stevensvera.revaluacionu3.Util.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transacciones extends AppCompatActivity {
    private String clienteid, accion;
    private EditText descripcion, monto;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);
        clienteid=getIntent().getStringExtra("clienteid");
        accion=getIntent().getStringExtra("accion");
        descripcion=(EditText)findViewById(R.id.editTextDescription);
        monto=(EditText)findViewById(R.id.editTextMonto);
        button=(Button) findViewById(R.id.accept);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest request = new HttpRequest("POST",MainActivity.DEFAULT_DOMAIN+"/AERevaluacionU3/webresources/ejercicios/createTrans"){
                    @Override
                    protected void onPostExecute(String s) {
                        new AlertDialog.Builder(Transacciones.this).setMessage(s).show();
                    }
                };
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                String formattedDate = df.format(Calendar.getInstance().getTime());
                Toast.makeText(Transacciones.this,formattedDate,Toast.LENGTH_LONG).show();
                request.execute("clienteid:"+clienteid,"accion:"+accion, "descripcion:"+descripcion.getText(),
                        "monto:"+monto.getText());
            }
        });
    }
}
