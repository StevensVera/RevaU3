package com.example.stevensvera.revaluacionu3.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
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

/**
 * Created by gustavo on 15/12/16.
 */

public class ClientList extends ArrayAdapter {
    Activity context;
    JSONArray json;
    public ClientList(Activity context, JSONArray json) {
        super(context, R.layout.clientelista, new String[json.length()]);
        this.context= context;
        this.json=json;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.clientelista, null, true);
        ImageView image= (ImageView) listViewItem.findViewById(R.id.imageclient);
        TextView nombre= (TextView) listViewItem.findViewById(R.id.nombreclient);
        TextView saldo= (TextView) listViewItem.findViewById(R.id.saldoclient);
        TextView fecha= (TextView) listViewItem.findViewById(R.id.fechaclient);

        try {
            JSONObject item=json.getJSONObject(position);
            nombre.setText(item.getString("nombrecliente"));
            saldo.setText("Saldo: "+item.getString("saldo"));
            fecha.setText("fecha: "+item.getString("fecha"));
            nombre.setTag(item.getString("clienteid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return listViewItem;
    }
}
