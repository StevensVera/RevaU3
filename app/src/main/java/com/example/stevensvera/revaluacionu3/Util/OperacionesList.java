package com.example.stevensvera.revaluacionu3.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stevensvera.revaluacionu3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OperacionesList extends ArrayAdapter {
    Activity context;
    JSONArray json;
    public OperacionesList(Activity context, JSONArray json) {
        super(context, R.layout.operacionesclient, new String[json.length()]);
        this.context= context;
        this.json=json;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.operacionesclient, null, true);

        TextView descripcion= (TextView) listViewItem.findViewById(R.id.decription);
        TextView monto= (TextView) listViewItem.findViewById(R.id.monto);
        TextView accion= (TextView) listViewItem.findViewById(R.id.accion);
        TextView fecha= (TextView) listViewItem.findViewById(R.id.fecha);

        try {
            JSONObject item=json.getJSONObject(position);
            JSONObject transaction=item.getJSONObject("transaccionesid");
            descripcion.setText(transaction.getString("descripcion"));
            monto.setText("monto: "+transaction.getString("monto"));
            if(transaction.getString("accion").equals("compra")){
                accion.setBackgroundColor(Color.RED);
            }else {
                accion.setBackgroundColor(Color.GREEN);
            }
            accion.setText(transaction.getString("accion"));
            fecha.setText(item.getString("fecha"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return listViewItem;
    }
}
