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
import com.iposprinter.printertestdemo.dto.Locacoes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        }



        ListView listaDeCursos = (ListView) findViewById(R.id.lista);

        ArrayAdapter<Locacoes> adapter = new ArrayAdapter<Locacoes>(this,
                android.R.layout.simple_list_item_1, locacoes);

        listaDeCursos.setAdapter(adapter);




        final   Intent intent=new Intent(this,IPosPrinterTestDemo.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

}
