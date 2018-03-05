package br.com.andersonsoares.madeiramadeirateste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import br.com.andersonsoares.activityutil.LocationActivity;

public class AssinaturaActivity extends LocationActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private com.github.gcacace.signaturepad.views.SignaturePad signaturepad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura);
        this.signaturepad = (SignaturePad) findViewById(R.id.signature_pad);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signaturepad.setBackgroundColor(Color.parseColor("#ffffff"));
        signaturepad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                signed = true;
            }

            @Override
            public void onClear() {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }

        if (id == R.id.limpar) {
            signaturepad.clear();
            return true;
        }

        if (id == R.id.salvar) {
            if(getCurrentLocation() == null){
                Toast.makeText(this, "Não foi possível identificar sua localização!", Toast.LENGTH_SHORT).show();
                return true;
            }

            if(signed){
                final ProgressDialog progressDialog  = new ProgressDialog(this);
                progressDialog.setMessage("Aguarde...");
                progressDialog.show();
                new AsyncTask<String,String,Bitmap>(){
                    @Override
                    protected Bitmap doInBackground(String... strings) {
                        Bitmap bitmap = signaturepad.getTransparentSignatureBitmap(true);
                       // BitmapFactory.
                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap s) {
                        super.onPostExecute(s);
                        progressDialog.dismiss();
                        Intent intent = new Intent();

                        //intent.putExtra("data",s);
                        setResult(RESULT_OK,intent);
                        finish();


                    }
                }.execute();

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    boolean signed;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assinatura, menu);
        return true;
    }
}
