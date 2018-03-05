package br.com.andersonsoares.madeiramadeirateste;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;

import br.com.andersonsoares.activityutil.LocationActivity;
import br.com.andersonsoares.madeiramadeirateste.model.Transporte;

public class DetalheActivity extends LocationActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private android.widget.TextView tipo;
    private android.widget.TextView endereco;
    private android.widget.TextView instrucoes;
    private android.widget.RelativeLayout acoes;
    private android.widget.RelativeLayout entrega;
    private android.widget.RelativeLayout ausente;
    private android.widget.RelativeLayout voltar;
    private android.widget.RelativeLayout licacao;
    private android.widget.RelativeLayout mapa;
    Transporte transporte;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        this.mapa = (RelativeLayout) findViewById(R.id.mapa);
        this.licacao = (RelativeLayout) findViewById(R.id.licacao);
        this.voltar = (RelativeLayout) findViewById(R.id.voltar);
        this.ausente = (RelativeLayout) findViewById(R.id.ausente);
        this.entrega = (RelativeLayout) findViewById(R.id.entrega);
        this.acoes = (RelativeLayout) findViewById(R.id.acoes);
        this.instrucoes = (TextView) findViewById(R.id.instrucoes);
        this.endereco = (TextView) findViewById(R.id.endereco);
        this.tipo = (TextView) findViewById(R.id.tipo);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        transporte = getIntent().getParcelableExtra("transporte");
        toolbar.setTitle(transporte.getNome());
        endereco.setText(transporte.getEndereco());
        tipo.setText(transporte.getTipo());

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        acoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalheActivity.this);
                builder.setTitle("Ações de Comunicação com o destinatário")
                        .setItems(new CharSequence[]{"Adicionar Prova Fotográfica","Capturar Assinatura","Capturar QR / Codigo de Barras",
                                "Informar Ida ao Local"," Chegada ao Local"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        if (ActivityCompat.checkSelfPermission(DetalheActivity.this,
                                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(DetalheActivity.this,new String[]{Manifest.permission.CAMERA},
                                                    REQUEST_PERMISSIONS_CODE_LIGAR_SMS);
                                        }else{
                                            takepicture();
                                        }
                                        break;
                                    case 1:
                                        takesignature();
                                        break;
                                    case 2:
                                        Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
                                        intent.setAction("com.google.zxing.client.android.SCAN");
                                        intent.putExtra("SAVE_HISTORY", false);
                                        startActivityForResult(intent, 0);
                                        break;
                                }
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalheActivity.this);

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                builder.setTitle("Deseja informar que foi entregue?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ausente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalheActivity.this);
                builder.setTitle("Ações de insucesso")
                        .setItems(new CharSequence[]{"Destinatário ausente","Endereço incorreto","Recusa do destinatário",
                        "Veículo quebrado","Mau tempo","Avaria","Extravio"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        licacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalheActivity.this);
                builder.setTitle("Ações de Comunicação com o destinatário")
                        .setItems(new CharSequence[]{"Enviar SMS de Pré-Entrega(" +transporte.getCelular()  +")","Ligar p/ o Celular(" +transporte.getCelular()  +")",
                                "Ligar p/ o Telefone Fixo("+ transporte.getTelefone()+ ")"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        if (ActivityCompat.checkSelfPermission(DetalheActivity.this,
                                                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(DetalheActivity.this,new String[]{Manifest.permission.SEND_SMS},
                                                    REQUEST_PERMISSIONS_CODE_LIGAR_SMS);
                                        }else{
                                            call(transporte.getTelefone());
                                        }

                                        break;
                                    case 1:
                                        if (ActivityCompat.checkSelfPermission(DetalheActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(DetalheActivity.this,new String[]{Manifest.permission.CALL_PHONE},
                                                    REQUEST_PERMISSIONS_CODE_LIGAR_FIXO);
                                        }else{
                                            call(transporte.getTelefone());
                                        }
                                        break;
                                    case 2:
                                        if (ActivityCompat.checkSelfPermission(DetalheActivity.this,
                                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(DetalheActivity.this,new String[]{Manifest.permission.CALL_PHONE},
                                                    REQUEST_PERMISSIONS_CODE_LIGAR_FIXO);
                                        }else{
                                            call(transporte.getCelular());
                                        }
                                        break;
                                }
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(DetalheActivity.this);
                builder.setTitle("Ações de Localização")
                        .setItems(new CharSequence[]{"Visualizar Local de Entrega","Iniciar Rota com Google"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        try {
                                            String uri = "geo:" + transporte.getLatitude() + "," + transporte.getLongitude()  + "?q=" + Uri.encode(transporte.getEndereco());
                                            // String uri = "geo:" + event.getLatitude() + "," + event.getLongitude();
                                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(uri)).setPackage("com.google.android.apps.maps"));
                                        } catch (Exception ex) {

                                            try {
                                                String uri = "geo:" + transporte.getLatitude() + "," + transporte.getLongitude();
                                                startActivity(new Intent(Intent.ACTION_VIEW,
                                                        Uri.parse(uri)));
                                            } catch (Exception e2) {
                                                Toast.makeText(DetalheActivity.this, "Não é possivel abrir o Google Maps.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                                break;
                                    case 1:
                                        try {
                                            String uri = "google.navigation:q=" + transporte.getLatitude() + "," + transporte.getLongitude();
                                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(uri)).setPackage("com.google.android.apps.maps"));
                                        } catch (Exception ex) {

                                            try {
                                                String uri = "geo:" + transporte.getLatitude() + "," + transporte.getLongitude();
                                                startActivity(new Intent(Intent.ACTION_VIEW,
                                                        Uri.parse(uri)));
                                            } catch (Exception e2) {
                                                Toast.makeText(DetalheActivity.this, "Não é possivel abrir o Google Maps.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        break;

                                }
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void takepicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    static final int REQUEST_IMAGE_SIGNATURE = 2;
    private void takesignature() {
        Intent intent = new Intent(this,AssinaturaActivity.class);
        startActivityForResult(intent, REQUEST_IMAGE_SIGNATURE);
    }


    public void call(String number){
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" +number));
            startActivity(callIntent);
        }catch (SecurityException ex){
            Toast.makeText(this, "Ocorreu um erro ao fazer a ligação", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(this, "Ocorreu um erro ao fazer a ligação", Toast.LENGTH_SHORT).show();
        }
    }

    public void sms(String number){
        try {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:" + number));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("sms_body"  , "Pré-Entrega");
            startActivity(smsIntent);
        }catch (SecurityException ex){
            Toast.makeText(this, "Ocorreu um erro ao fazer a ligação", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(this, "Ocorreu um erro ao fazer a ligação", Toast.LENGTH_SHORT).show();
        }
    }

    private final static int REQUEST_PERMISSIONS_CODE_LIGAR_FIXO = 128;
    private final static int REQUEST_PERMISSIONS_CODE_LIGAR_CELULAR = 129;
    private final static int REQUEST_PERMISSIONS_CODE_LIGAR_SMS = 130;
    private final static int REQUEST_PERMISSIONS_CODE_CAMERA = 131;

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(REQUEST_PERMISSIONS_CODE_LIGAR_FIXO == requestCode){
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.CALL_PHONE)
                        && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            call(transporte.getTelefone());

        }
        if(REQUEST_PERMISSIONS_CODE_LIGAR_CELULAR == requestCode){
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.CALL_PHONE)
                        && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            call(transporte.getCelular());

        }


        if(REQUEST_PERMISSIONS_CODE_LIGAR_CELULAR == requestCode){
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.CALL_PHONE)
                        && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            sms(transporte.getCelular());

        }

        if(REQUEST_PERMISSIONS_CODE_CAMERA == requestCode){
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.CAMERA)
                        && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            takepicture();

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

        }
        if (requestCode == REQUEST_IMAGE_SIGNATURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d("qrcode", "contents: " + contents);
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("qrcode", "RESULT_CANCELED");
            }
        }
    }
}
