package com.example.stevensvera.revaluacionu3;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stevensvera.revaluacionu3.Util.HttpRequest;
import com.example.stevensvera.revaluacionu3.Util.OperacionesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Operaciones extends AppCompatActivity {
    private String clienteid;
    private ListView listView;
    private Button abono, compra;
    private TextView tabono, tsaldo, tcompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operaciones);
        clienteid=getIntent().getStringExtra("clienteid");
        abono= (Button) findViewById(R.id.abonar);
        compra= (Button) findViewById(R.id.comprar);
        listView=(ListView) findViewById(R.id.opelacioneslist);
        tabono=(TextView) findViewById(R.id.totalabono);
        tsaldo= (TextView) findViewById(R.id.saldo);
        tcompra= (TextView) findViewById(R.id.totalcompras);


        HttpRequest request =
                new HttpRequest("get",MainActivity.DEFAULT_DOMAIN+"/AERevaluacionU3/webresources/ejercicios/buscarPorCliente/"+clienteid){
                    @Override
                    protected void onPostExecute(String s) {

                        new AlertDialog.Builder(Operaciones.this).setMessage(s).show();
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(s);
                            JSONArray json=new JSONArray(jsonObject.getString("msg"));
                            OperacionesList adapter= new OperacionesList(Operaciones.this,json);
                            listView.setAdapter(adapter);
                            int totalAbono=0;
                            int totalCompra=0;
                            for(int i=0; i<json.length();i++){
                                JSONObject item= json.getJSONObject(i).getJSONObject("transaccionesid");
                                if(item.getString("accion").equals("abono")){
                                    totalAbono+=item.getInt("monto");
                                }else {
                                    totalCompra+=item.getInt("monto");
                                }
                            }
                            tabono.setText("Total abono: "+totalAbono+"");
                            tcompra.setText("Total compra: "+totalCompra+"");
                            tsaldo.setText("Saldo: "+(totalCompra-totalAbono)+"");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

        request.execute();
        abono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Operaciones.this,Transacciones.class);
                intent.putExtra("accion","abono");
                intent.putExtra("clienteid",clienteid);
                startActivity(intent);
            }
        });
        compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Operaciones.this,Transacciones.class);
                intent.putExtra("accion","compra");
                intent.putExtra("clienteid",clienteid);
                startActivity(intent);
            }
        });


    }
}
