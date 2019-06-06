package com.iposprinter.printertestdemo.dto;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iposprinter.printertestdemo.R;

import java.util.List;


/**
 * Created by alex on 02/07/17.
 */

public class AdapterCursosPersonalizado extends BaseAdapter {

    private final List<Locacoes> cursos;
    private final Activity act;

    public AdapterCursosPersonalizado(List<Locacoes> cursos, Activity act) {
        this.cursos = cursos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.lista_curso_personalizada, parent, false);

        Locacoes curso = cursos.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_nome);
        TextView descricao = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_descricao);

        TextView vaga = (TextView)
                view.findViewById(R.id.vaga);


        String motorista = curso.getMotorista() != null && curso.getMotorista().length() > 0 ? " | "+curso.getMotorista() : "";
        nome.setText(curso.getPlaca().toUpperCase() +  motorista);
        vaga.setText("Vaga "+curso.getVaga()+" - "+ curso.getMoeda()+", "+curso.getTempo()+", "+curso.getVeiculoNacao());

        return view;
    }
}