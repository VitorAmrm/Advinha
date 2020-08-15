package ufpb.luis.vitor.advinha.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;


import com.squareup.picasso.Picasso;

import ufpb.luis.vitor.advinha.R;


public class WinnerScreen extends Activity {

    private Button btn_home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);

        btn_home = findViewById(R.id.btn_home);

        btn_home.setOnClickListener(v -> {
            Intent voltarHome = new Intent(this,ContextChoose.class);
            startActivity(voltarHome);
        });


    }
}
