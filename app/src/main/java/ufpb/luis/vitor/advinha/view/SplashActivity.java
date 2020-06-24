package ufpb.luis.vitor.advinha.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ufpb.luis.vitor.advinha.Components.TypeWriter;
import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.utils.ConfigurationLogger;


public class SplashActivity extends Activity {
    Context context = this;
    private static final int STORAGE_CODE = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        TypeWriter tx = findViewById(R.id.bounceText);
        tx.setCharacterDelay(300);
        tx.animateText(getString(R.string.loading));


         if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_CODE);
         }


        File start = new File(this.getApplicationContext().getFilesDir(),"config.json");


        boolean c = start.exists();
        if(!c){
            try {
            start.createNewFile();
                ConfigurationLogger log = new ConfigurationLogger(true,false,this);
                try {
                    log.writeConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
            catch (IOException e) {
            e.printStackTrace();
        }
        }


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this,ContextChoose.class);
                startActivity(intent);
            }
        }, 4000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case STORAGE_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
            default:
                Toast.makeText(this,"Permissão Negada",Toast.LENGTH_SHORT).show();
        }

    }

}

