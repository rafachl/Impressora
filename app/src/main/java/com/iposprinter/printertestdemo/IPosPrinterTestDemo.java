package com.iposprinter.printertestdemo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iposprinter.iposprinterservice.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iposprinter.printertestdemo.Utils.ButtonDelayUtils;
import com.iposprinter.printertestdemo.Utils.BytesUtil;
import com.iposprinter.printertestdemo.Utils.DadosTicket;
import com.iposprinter.printertestdemo.Utils.HandlerUtils;
import com.iposprinter.printertestdemo.dto.Cotacao;
import com.iposprinter.printertestdemo.dto.Locacoes;
import com.iposprinter.printertestdemo.dto.Login;
import com.iposprinter.printertestdemo.dto.SalvarAlocacaoDTO;
import com.iposprinter.printertestdemo.dto.SalvarResposta;

import static com.iposprinter.printertestdemo.Utils.PrintContentsExamples.customCHR;
import static com.iposprinter.printertestdemo.Utils.PrintContentsExamples.customCHZ1;
import static com.iposprinter.printertestdemo.Utils.PrintContentsExamples.Text;
import static com.iposprinter.printertestdemo.Utils.PrintContentsExamples.Elemo;
import static com.iposprinter.printertestdemo.Utils.PrintContentsExamples.Baidu;
import static com.iposprinter.printertestdemo.MemInfo.bitmapRecycle;

public class IPosPrinterTestDemo extends AppCompatActivity {

    private static final String TAG = "IPosPrinterTestDemo";
    /* Demo 版本号*/
    private static final String VERSION = "V1.1.1";


    private Button b_text;

    private final int PRINTER_NORMAL = 0;

    private int printerStatus = 0;

    private final String PRINTER_NORMAL_ACTION = "com.iposprinter.iposprinterservic e.NORMAL_ACTION";
    private final String PRINTER_PAPERLESS_ACTION = "com.iposprinter.iposprinterservice.PAPERLESS_ACTION";
    private final String PRINTER_PAPEREXISTS_ACTION = "com.iposprinter.iposprinterservice.PAPEREXISTS_ACTION";
    private final String PRINTER_THP_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_HIGHTEMP_ACTION";
    private final String PRINTER_THP_NORMALTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_NORMALTEMP_ACTION";
    private final String PRINTER_MOTOR_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION";
    private final String PRINTER_BUSY_ACTION = "com.iposprinter.iposprinterservice.BUSY_ACTION";
    private final String PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION = "com.iposprinter.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION";
    private final String GET_CUST_PRINTAPP_PACKAGENAME_ACTION = "android.print.action.CUST_PRINTAPP_PACKAGENAME";

    /*定义消息*/
    private final int MSG_TEST = 1;
    private final int MSG_IS_NORMAL = 2;
    private final int MSG_IS_BUSY = 3;
    private final int MSG_PAPER_LESS = 4;
    private final int MSG_PAPER_EXISTS = 5;
    private final int MSG_THP_HIGH_TEMP = 6;
    private final int MSG_THP_TEMP_NORMAL = 7;
    private final int MSG_MOTOR_HIGH_TEMP = 8;
    private final int MSG_MOTOR_HIGH_TEMP_INIT_PRINTER = 9;
    private final int MSG_CURRENT_TASK_PRINT_COMPLETE = 10;

    /*循环打印类型*/
    private final int MULTI_THREAD_LOOP_PRINT = 1;
    private final int INPUT_CONTENT_LOOP_PRINT = 2;
    private final int DEMO_LOOP_PRINT = 3;
    private final int PRINT_DRIVER_ERROR_TEST = 4;
    private final int DEFAULT_LOOP_PRINT = 0;

    //循环打印标志位
    private int loopPrintFlag = DEFAULT_LOOP_PRINT;
    private byte loopContent = 0x00;
    private int printDriverTestCount = 0;


    private IPosPrinterService mIPosPrinterService;
    private IPosPrinterCallback callback = null;

    private Random random = new Random();
    private HandlerUtils.MyHandler handler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    RadioGroup radioTipo;
    RadioGroup radioTempo;
    EditText edtVaga;
    EditText edtMotorista;
    EditText edtPlaca;
    EditText edtNumeroDccumento;
    private String codigoFiscal;


    TextView valorGuarani, valorDoalr, valorReal, valorPeso;

    Cotacao cotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipos_printer_test_demo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbara);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        handler = new HandlerUtils.MyHandler(iHandlerIntent);
        innitView();
        callback = new IPosPrinterCallback.Stub() {

            @Override
            public void onRunResult(final boolean isSuccess) throws RemoteException {
                Log.i(TAG, "result:" + isSuccess + "\n");
            }

            @Override
            public void onReturnString(final String value) throws RemoteException {
                Log.i(TAG, "result:" + value + "\n");
            }
        };

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(IPosPrinterTestDemo.this, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        writePrintDataToCacheFile("*****************", null);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        radioTipo = (RadioGroup) findViewById(R.id.tipoLocacao);
        radioTempo = (RadioGroup) findViewById(R.id.radioTempo);
        edtVaga = (EditText) findViewById(R.id.edtVaga);
        edtMotorista = (EditText) findViewById(R.id.edtMotorista);
        edtPlaca = (EditText) findViewById(R.id.edtPlaca);
        edtNumeroDccumento = (EditText) findViewById(R.id.numeroDocumento);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            codigoFiscal = b.getString("codigoFiscal");
            Gson gson = new Gson();

            cotacao = gson.fromJson(b.getString("cotacao"), Cotacao.class);
        }

        valorDoalr = (TextView) findViewById(R.id.valorDolar);
        valorGuarani = (TextView) findViewById(R.id.valorGuarani);
        valorPeso = (TextView) findViewById(R.id.valorPeso);
        valorReal = (TextView) findViewById(R.id.valorReal);

    }

    public void exibirValores(View v) {

        int itenRadioTipo = radioTipo.getCheckedRadioButtonId();
        int itenRadioTempo = radioTempo.getCheckedRadioButtonId();

        float tempo = 0;


        if (itenRadioTempo == findViewById(R.id.radio15).getId()) {
            tempo = 15;

        } else if (itenRadioTempo == findViewById(R.id.radio30).getId()) {
            tempo = 30;
        } else if (itenRadioTempo == findViewById(R.id.radio1).getId()) {
            tempo = 60;
        } else if (itenRadioTempo == findViewById(R.id.radio2).getId()) {
            tempo = 120;
        }
        float valorTipo = 0;

        String tipo = "moto";
        if (itenRadioTipo == findViewById(R.id.radiocarro).getId()) {
            tipo = "carro";
            valorTipo = cotacao.getValorHoraCarro();
        } else {
            valorTipo = cotacao.getValorHoraMoto();

        }


        float hora = tempo / 60;


        float dolar = (valorTipo * hora) / cotacao.getCotacaoDolar();
        float real = (valorTipo * hora) / cotacao.getCotacaoReal();
        float peso = (valorTipo * hora) / cotacao.getCotacaoPeso();
        float guarani = (valorTipo * hora) / cotacao.getCotacaoGuarani();


        valorDoalr.setText("U$ " + round(dolar, 2));
        valorReal.setText("R$: " + round(real, 2));
        valorGuarani.setText("GS: " + round(guarani, 2));
        valorPeso.setText("Peso: " + round(peso, 2));


    }

    public void coletarDados(View v) {


        int itenRadioTipo = radioTipo.getCheckedRadioButtonId();
        int itenRadioTempo = radioTempo.getCheckedRadioButtonId();

        float tempo = 0;


        if (itenRadioTempo == findViewById(R.id.radio15).getId()) {
            tempo = 15;

        } else if (itenRadioTempo == findViewById(R.id.radio30).getId()) {
            tempo = 30;
        } else if (itenRadioTempo == findViewById(R.id.radio1).getId()) {
            tempo = 60;
        } else if (itenRadioTempo == findViewById(R.id.radio2).getId()) {
            tempo = 120;
        }
        float valorTipo = 0;

        String tipo = "moto";
        if (itenRadioTipo == findViewById(R.id.radiocarro).getId()) {
            tipo = "carro";
            valorTipo = cotacao.getValorHoraCarro();
        } else {
            valorTipo = cotacao.getValorHoraMoto();

        }


        float hora = tempo / 60;


        float dolar = (valorTipo * hora) / cotacao.getCotacaoDolar();
        float real = (valorTipo * hora) / cotacao.getCotacaoReal();
        float peso = (valorTipo * hora) / cotacao.getCotacaoPeso();
        float guarani = (valorTipo * hora) / cotacao.getCotacaoGuarani();


        valorDoalr.setText("U$ " + round(dolar, 2));
        valorReal.setText("R$: " + round(real, 2));
        valorGuarani.setText("GS: " + round(guarani, 2));
        valorPeso.setText("Peso: " + round(peso, 2));

        SalvarAlocacaoDTO alocacaoDTO = new SalvarAlocacaoDTO();
        alocacaoDTO.setTipo(tipo);
        alocacaoDTO.setTempo(tempo + "");
        alocacaoDTO.setMoeda("GS");
        alocacaoDTO.setPlaca(edtPlaca.getText().toString());
        alocacaoDTO.setVaga(edtVaga.getText().toString());
        alocacaoDTO.setMotorista(edtMotorista.getText().toString());
        alocacaoDTO.setFiscal_id(codigoFiscal);
        alocacaoDTO.setValorPago(guarani + "");
        alocacaoDTO.setMotoristaRuc(edtNumeroDccumento.getText().toString());
        setContentView(R.layout.progres);


        this.salvarLocacao(alocacaoDTO);

    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public void salvarLocacao(final SalvarAlocacaoDTO salvarAlocacaoDTO) {
        final Context context = this;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://app.actsistemas.com.br/alocacao/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);


                    Gson gson = new Gson();

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(gson.toJson(salvarAlocacaoDTO));

                    os.flush();
                    os.close();
                    Log.e("Json", gson.toJson(salvarAlocacaoDTO));

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG", conn.getResponseMessage());

                    String json_response = "";
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String text = "";
                    while ((text = br.readLine()) != null) {
                        json_response += text;
                    }
                    SalvarResposta respost = gson.fromJson(json_response, SalvarResposta.class);


                    conn.disconnect();


                    printText(respost);
                    sendPost();
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            setContentView(R.layout.activity_ipos_printer_test_demo);
                            Toast.makeText(context, "Erro ao salvar locacoes", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        thread.start();

    }


    private void setButtonEnable(boolean flag) {

        b_text.setEnabled(flag);

    }

    /**
     * 消息处理
     */
    private HandlerUtils.IHandlerIntent iHandlerIntent = new HandlerUtils.IHandlerIntent() {
        @Override
        public void handlerIntent(Message msg) {
            switch (msg.what) {
                case MSG_TEST:
                    break;
                case MSG_IS_NORMAL:
                    if (getPrinterStatus() == PRINTER_NORMAL) {
                        loopPrint(loopPrintFlag);
                    }
                    break;
                case MSG_IS_BUSY:
                    Toast.makeText(IPosPrinterTestDemo.this, R.string.printer_is_working, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_PAPER_LESS:
                    loopPrintFlag = DEFAULT_LOOP_PRINT;
                    Toast.makeText(IPosPrinterTestDemo.this, R.string.out_of_paper, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_PAPER_EXISTS:
                    Toast.makeText(IPosPrinterTestDemo.this, R.string.exists_paper, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_THP_HIGH_TEMP:
                    Toast.makeText(IPosPrinterTestDemo.this, R.string.printer_high_temp_alarm, Toast.LENGTH_SHORT).show();
                    break;
                case MSG_MOTOR_HIGH_TEMP:
                    loopPrintFlag = DEFAULT_LOOP_PRINT;
                    Toast.makeText(IPosPrinterTestDemo.this, R.string.motor_high_temp_alarm, Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(MSG_MOTOR_HIGH_TEMP_INIT_PRINTER, 180000);  //马达高温报警，等待3分钟后复位打印机
                    break;
                case MSG_MOTOR_HIGH_TEMP_INIT_PRINTER:
                    printerInit();
                    break;
                case MSG_CURRENT_TASK_PRINT_COMPLETE:
                    Toast.makeText(IPosPrinterTestDemo.this, R.string.printer_current_task_print_complete, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private BroadcastReceiver IPosPrinterStatusListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                Log.d(TAG, "IPosPrinterStatusListener onReceive action = null");
                return;
            }
            Log.d(TAG, "IPosPrinterStatusListener action = " + action);
            if (action.equals(PRINTER_NORMAL_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_IS_NORMAL, 0);
            } else if (action.equals(PRINTER_PAPERLESS_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_PAPER_LESS, 0);
            } else if (action.equals(PRINTER_BUSY_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_IS_BUSY, 0);
            } else if (action.equals(PRINTER_PAPEREXISTS_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_PAPER_EXISTS, 0);
            } else if (action.equals(PRINTER_THP_HIGHTEMP_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_THP_HIGH_TEMP, 0);
            } else if (action.equals(PRINTER_THP_NORMALTEMP_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_THP_TEMP_NORMAL, 0);
            } else if (action.equals(PRINTER_MOTOR_HIGHTEMP_ACTION))  //此时当前任务会继续打印，完成当前任务后，请等待2分钟以上时间，继续下一个打印任务
            {
                handler.sendEmptyMessageDelayed(MSG_MOTOR_HIGH_TEMP, 0);
            } else if (action.equals(PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION)) {
                handler.sendEmptyMessageDelayed(MSG_CURRENT_TASK_PRINT_COMPLETE, 0);
            } else if (action.equals(GET_CUST_PRINTAPP_PACKAGENAME_ACTION)) {
                String mPackageName = intent.getPackage();
                Log.d(TAG, "*******GET_CUST_PRINTAPP_PACKAGENAME_ACTION：" + action + "*****mPackageName:" + mPackageName);

            } else {
                handler.sendEmptyMessageDelayed(MSG_TEST, 0);
            }
        }
    };

    public void sendPost() {
        final Context context = this;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://app.actsistemas.com.br/alocacao/1");
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
                    String aaa = json_response;

                    Intent intent = new Intent();
                    intent.putExtra("locacao", json_response);
                    setResult(RESULT_OK, intent);
                    conn.disconnect();
                    finish();

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            setContentView(R.layout.activity_ipos_printer_test_demo);
                            Toast.makeText(context, "Erro ao buscar locacoes", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        thread.start();
    }


    private ServiceConnection connectService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPosPrinterService = IPosPrinterService.Stub.asInterface(service);
            setButtonEnable(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIPosPrinterService = null;
        }
    };

    public static void writePrintDataToCacheFile(String printStr, byte[] printByteData) {
        String printDataDirPath = Environment.getExternalStorageDirectory() + File.separator + "PrintDataCache";
        String printDataFilePath1 = printDataDirPath + File.separator + "printdata_1.txt";
        String printDataFilePath2 = printDataDirPath + File.separator + "printdata_2.txt";
        Log.d(TAG, "printDataDirPath:" + printDataDirPath);

        File fileDir = new File(printDataDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }


        if (fileDir.exists()) {
            Log.d(TAG, printDataDirPath + " is exists!!!!!");
        } else {
            Log.d(TAG, printDataDirPath + " is not exists!!!!!");
        }

        File printDataFile = new File(printDataFilePath1);
        if (printDataFile.exists() && printDataFile.isFile()) {
            if (printDataFile.length() > 5 * 1024 * 1024) {
                printDataFile = new File(printDataFilePath2);
                if (printDataFile.exists() && printDataFile.isFile()) {
                    if (printDataFile.length() > 5 * 1024 * 1024) {
                        return;
                    }
                } else {
                    try {
                        printDataFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            printDataFile = new File(printDataFilePath2);
            if (printDataFile.exists() && printDataFile.isFile()) {
                if (printDataFile.length() > 5 * 1024 * 1024) {
                    printDataFile = new File(printDataFilePath1);
                }
            } else {
                printDataFile = new File(printDataFilePath1);
                try {
                    printDataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if ((printStr == null) && (printByteData == null)) {
            return;
        }

        FileOutputStream fileOut = null;
        if (printStr != null) {
            BufferedWriter outw = null;
            try {
                fileOut = new FileOutputStream(printDataFile, true);
                outw = new BufferedWriter(new OutputStreamWriter(fileOut));
                outw.write(printStr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if (outw != null) {
                        outw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (printByteData != null) {
            BufferedOutputStream bufOut = null;
            try {
                fileOut = new FileOutputStream(printDataFile, true);
                bufOut = new BufferedOutputStream(fileOut);
                bufOut.write(printByteData);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (bufOut != null) {
                        bufOut.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //询问用户权限
        if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            //用户同意
        } else {
            //用户不同意
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "activity onResume");
        super.onResume();
        //绑定服务
        Intent intent = new Intent();
        intent.setPackage("com.iposprinter.iposprinterservice");
        intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
        bindService(intent, connectService, Context.BIND_AUTO_CREATE);
        IntentFilter printerStatusFilter = new IntentFilter();
        printerStatusFilter.addAction(PRINTER_NORMAL_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPERLESS_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPEREXISTS_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_NORMALTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_MOTOR_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_BUSY_ACTION);
        printerStatusFilter.addAction(GET_CUST_PRINTAPP_PACKAGENAME_ACTION);

        registerReceiver(IPosPrinterStatusListener, printerStatusFilter);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "activity onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "activity onStop");
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        loopPrintFlag = DEFAULT_LOOP_PRINT;
        unregisterReceiver(IPosPrinterStatusListener);
        unbindService(connectService);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "activity onDestroy");
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void innitView() {
        b_text = (Button) findViewById(R.id.b_text);


        setButtonEnable(false);
    }


    public int getPrinterStatus() {

        Log.i(TAG, "***** printerStatus" + printerStatus);
        try {
            printerStatus = mIPosPrinterService.getPrinterStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "#### printerStatus" + printerStatus);
        return printerStatus;
    }

    /**
     * 打印机初始化
     */
    public void printerInit() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    mIPosPrinterService.printerInit(callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void printText(final SalvarResposta respost) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
                Bitmap qrcode = BitmapFactory.decodeResource(getResources(), R.mipmap.qrcode);


                try {


                    String fatura = respost.getId();
                    String faturaData = respost.getData();
                    String faturaDataValidade = respost.getDataHoraExpiracao();
                    String faturaCliente = respost.getMotorista();
                    String faturaClienteRuc = respost.getMotoristaRuc();
                    String faturaChapa = respost.getPlaca();
                    String faturaFiscal = respost.getFiscal_id();
                    String faturaEntrada = respost.getDataHora();
                    String faturaSaida = respost.getDataHoraExpiracao();
                    String faturaEspacio = respost.getVaga();
                    String faturaValor = respost.getValorPago();

                    String faturaUrl = respost.getUrlValidacao();

                    mIPosPrinterService.printBitmap(1, 16, mBitmap, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.PrintSpecFormatText("COMPROBANTE \n", "ST", 48, 1, callback);
                    mIPosPrinterService.printSpecifiedTypeText("    ESTACIONAMIENTO DIGITAL", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("A-TEC PARAGUAY    RUC 80012312-3", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printBlankLines(1, 10, callback);
                    mIPosPrinterService.PrintSpecFormatText("TIMBRADO N.: " + fatura, "ST", 24, 1, callback);
                    mIPosPrinterService.printBlankLines(1, 10, callback);
                    mIPosPrinterService.printSpecifiedTypeText("Val. " + faturaData + " hasta " + faturaDataValidade, "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.PrintSpecFormatText("FACTURA  001-002-" + fatura, "ST", 32, 1, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.printSpecifiedTypeText("C.N.: " + fatura + "         " + faturaData, "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("--------------------------------", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("Cliente: ", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText(faturaCliente, "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("Ci/Ruc: " + faturaClienteRuc, "ST", 24, callback);
                    mIPosPrinterService.printBlankLines(1, 20, callback);
                    String[] text = new String[2];
                    int[] width = new int[]{10, 19};
                    int[] align = new int[]{0, 2};
                    text[0] = "Chapa";
                    text[1] = faturaChapa;
                    mIPosPrinterService.printColumnsText(text, width, align, 1, callback);
                    text[0] = "Fiscal";
                    text[1] = faturaFiscal;
                    mIPosPrinterService.printColumnsText(text, width, align, 1, callback);
                    text[0] = "Entrada";
                    text[1] = faturaEntrada;
                    mIPosPrinterService.printColumnsText(text, width, align, 1, callback);
                    text[0] = "Salida";
                    text[1] = faturaSaida;
                    mIPosPrinterService.printColumnsText(text, width, align, 1, callback);
                    text[0] = "Espacio";
                    text[1] = faturaEspacio;
                    mIPosPrinterService.printColumnsText(text, width, align, 1, callback);
                    text[0] = "Valor(Gs)";
                    text[1] = faturaValor;
                    mIPosPrinterService.printColumnsText(text, width, align, 0, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printBlankLines(1, 25, callback);
                    mIPosPrinterService.setPrinterPrintAlignment(1, callback);
                    mIPosPrinterService.printQRCode(faturaUrl, 11, 2, callback);

                    mIPosPrinterService.printBlankLines(1, 25, callback);

                    bitmapRecycle(mBitmap);
                    mIPosPrinterService.printerPerformPrint(80, callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 打印大数据
     * numK: 打印数据的大小，以4k为单位，最大127个4k，既十六进制0x7f
     * data: 打印内容
     */
    public void bigDataPrintTest(final int numK, final byte data) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                int num4K = 1024 * 4;
                int length = numK > 127 ? num4K * 127 : num4K * numK;
                byte[] dataBytes = new byte[length];
                for (int i = 0; i < length; i++) {
                    dataBytes[i] = data;
                }
                try {
                    mIPosPrinterService.printRawData(dataBytes, callback);
                    mIPosPrinterService.printerPerformPrint(160, callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 循环打印
     */
    public void loopPrint(int flag) {
        switch (flag) {
            case MULTI_THREAD_LOOP_PRINT:
                multiThreadLoopPrint();
                break;
            case DEMO_LOOP_PRINT:
                break;
            case INPUT_CONTENT_LOOP_PRINT:
                bigDataPrintTest(127, loopContent);
                break;
            case PRINT_DRIVER_ERROR_TEST:
                printDriverTest();
                break;
            default:
                break;
        }
    }

    /**
     * 并发多线程打印
     */
    public void multiThreadLoopPrint() {

    }


    /**
     * 每次下发内容以64k为单位递增，最大512k
     */
    public void printDriverTest() {
        if (printDriverTestCount >= 8) {
            loopPrintFlag = DEFAULT_LOOP_PRINT;
            printDriverTestCount = 0;
        } else {
            printDriverTestCount++;
            bigDataPrintTest(printDriverTestCount * 16, (byte) 0x11);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("IPosPrinterTestDemo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
}
