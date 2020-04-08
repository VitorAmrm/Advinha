package ufpb.luis.vitor.advinha.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import ufpb.luis.vitor.advinha.R;


public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this,ContextChoose.class);
                startActivity(intent);
            }
        }, 6000);
    }

}
