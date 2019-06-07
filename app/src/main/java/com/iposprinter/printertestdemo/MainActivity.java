package com.iposprinter.printertestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iposprinter.printertestdemo.dto.AdapterCursosPersonalizado;
import com.iposprinter.printertestdemo.dto.Cotacao;
import com.iposprinter.printertestdemo.dto.Locacoes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listaDeCursos;
    private String codigoFiscal;
    private String cotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        List<Locacoes> locacoes=new ArrayList<>();
        if(b != null){

            Gson gson=new Gson();
            Type listType = new TypeToken<ArrayList<Locacoes>>(){}.getType();

           locacoes=gson.fromJson(b.getString("locacao"),listType);
            codigoFiscal=b.getString("codigoFiscal");


            cotacao=b.getString("cotacao");
        }



         listaDeCursos = (ListView) findViewById(R.id.lista);


        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(locacoes, this);


        listaDeCursos.setAdapter(adapter);




        final   Intent intent=new Intent(this,IPosPrinterTestDemo.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("cotacao", cotacao);
                intent.putExtra("codigoFiscal", codigoFiscal);

                startActivityForResult(intent, 1);            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  AdapterCursosPersonalizado ta = (AdapterCursosPersonalizado) listaDeCursos.getAdapter();

        Locacoes a=new Locacoes();
        a.setPlaca("äaaaaaaaa");
        ta.adiciionarIten(a);
        ta.notifyDataSetChanged();*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                Gson gson=new Gson();
                Type listType = new TypeToken<ArrayList<Locacoes>>(){}.getType();



                AdapterCursosPersonalizado ta = (AdapterCursosPersonalizado) listaDeCursos.getAdapter();
               List <Locacoes> aaa=  gson.fromJson( data.getStringExtra("locacao"),listType);
                ta.atualizar(aaa);
                ta.notifyDataSetChanged();

            }
        }
    }

}
