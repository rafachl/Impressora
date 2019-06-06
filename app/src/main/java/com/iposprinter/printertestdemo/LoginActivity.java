package com.iposprinter.printertestdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iposprinter.printertestdemo.dto.Locacoes;
import com.iposprinter.printertestdemo.dto.Login;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {


    EditText edtLogin;
    EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        edtLogin.setText("gabriel@actsistemas.com.br");
        edtSenha.setText("123123");


    }

    public void logar(View v) {
        verificarLogin();


    }

    public void verificarLogin() {
       final Context context=this;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://app.actsistemas.com.br/fiscal/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Login login = new Login();
                    login.setSenha(edtSenha.getText().toString());
                    login.setEmail(edtLogin.getText().toString());


                    Gson gson = new Gson();

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(gson.toJson(login));

                    os.flush();
                    os.close();

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG", conn.getResponseMessage());


                    String json_response = "";
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String text = "";
                    while ((text = br.readLine()) != null) {
                        json_response += text;
                    }

                    Login retorno=  gson.fromJson(json_response,Login.class);
                    conn.disconnect();


                    buscarAlocacao(retorno.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Falha ao logar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        thread.start();
    }


    public void buscarAlocacao(final String id) {
        final Intent intent = new Intent(this, MainActivity.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://app.actsistemas.com.br/alocacao/"+id);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");


                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG", conn.getResponseMessage());
                    String json_response = "";
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String text = "";
                    while ((text = br.readLine()) != null) {
                        json_response += text;
                    }


                    intent.putExtra("locacao", json_response);
                    intent.putExtra("codigoFiscal", id);

                    startActivity(intent);
                    finish();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


                    /*public void sendPost() {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL("https://app.actsistemas.com.br/alocacao/1/");
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");
                                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                                    conn.setRequestProperty("Accept","application/json");
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);

                                    Login jsonParam =new Login();
                                    jsonParam.setEmail("gabriel@actsistemas.com.br");
                                    jsonParam.setSenha("123123");


                                    Log.i("JSON", jsonParam.toString());
                                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                                    os.writeBytes("{\"email\":\"gabriel@actsistemas.com.br\",\"senha\" :\"123456\"}"  );

                                    os.flush();
                                    os.close();

                                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                                    Log.i("MSG" , conn.getResponseMessage());

                                    conn.disconnect();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();
                    }*/

}
