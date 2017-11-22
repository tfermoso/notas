package com.spinoffpyme.notas.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spinoffpyme.notas.Models.Nota;
import com.spinoffpyme.notas.R;

import java.util.List;

/**
 * Created by Tomás on 21/11/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private List<Nota> notas;
    private Activity activity;
    private int layoutTarea;

    public CustomAdapter(List<Nota> notas, Activity activity, int idLayout) {
        this.notas = notas;
        this.activity = activity;
        this.layoutTarea = idLayout;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Object getItem(int position) {
        return notas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notas.get(position).getIdnota();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View vista=inflater.inflate(layoutTarea,null);

        TextView titulo=(TextView)vista.findViewById(R.id.lblTitulo);
        TextView descripcion=(TextView)vista.findViewById(R.id.lblDescripcion);

        titulo.setText(notas.get(position).getTitulo());
        descripcion.setText(notas.get(position).getDescripcion());
        return vista;
    }
}
