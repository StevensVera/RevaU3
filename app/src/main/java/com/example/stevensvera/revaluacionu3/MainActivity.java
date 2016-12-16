package com.example.stevensvera.revaluacionu3;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stevensvera.revaluacionu3.Util.ClientList;
import com.example.stevensvera.revaluacionu3.Util.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String DEFAULT_DOMAIN="http://192.168.1.71:8080";//AQUI PONER IP DE LA COMPU (SERVER)

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.listclient);

        HttpRequest clientRequest= new HttpRequest("get",DEFAULT_DOMAIN+"/AERevaluacionU3/webresources/ejercicios/buscarcliente"){//<<<<<<<<<<<<<<< web service URI !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            @Override
            protected void onPostExecute(String s) {
                try {
                    new AlertDialog.Builder(MainActivity.this).setMessage(s).show();
                    JSONObject jsonObject= new JSONObject(s);
                    JSONArray json=new JSONArray(jsonObject.getString("msg"));
                    ClientList adapter= new ClientList(MainActivity.this,json);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String cliente= ((TextView)view.findViewById(R.id.nombreclient)).getText().toString();
                            String clienteid= (String)((TextView)view.findViewById(R.id.nombreclient)).getTag();
                            Intent intent = new Intent(MainActivity.this,Operaciones.class);
                            intent.putExtra("clienteid",clienteid);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        clientRequest.execute();


    }
}
