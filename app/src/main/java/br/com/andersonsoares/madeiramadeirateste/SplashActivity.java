package br.com.andersonsoares.madeiramadeirateste;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {


    private final static int REQUEST_PERMISSIONS_CODE = 128;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.CAMERA},
                    REQUEST_PERMISSIONS_CODE);
        } else {
            open();
        }


    }

    public void open() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE:
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        finishAffinity();
                        return;
                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.CALL_PHONE)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        finishAffinity();
                        return;
                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.SEND_SMS)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        finishAffinity();
                        return;
                    }else if (permissions[i].equalsIgnoreCase(Manifest.permission.CAMERA)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        finishAffinity();
                        return;
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        open();
    }
}
