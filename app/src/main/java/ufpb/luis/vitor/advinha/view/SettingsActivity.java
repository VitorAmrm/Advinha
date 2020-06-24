package ufpb.luis.vitor.advinha.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.utils.ConfigurationLogger;


public class SettingsActivity extends Activity {

    private boolean teclado_personalizado ;
    private boolean teclado_do_google;
    private Button button;
    private RadioGroup escolhas;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
        escolhas = findViewById(R.id.escolhas);
        button = findViewById(R.id.salvar_alterações);
        toolbar = findViewById(R.id.settings_screen_toolbar);

        carregarInformacao();

        escolhas.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.primeiro_radio:
                    escolhas.check(R.id.primeiro_radio);
                    teclado_personalizado = true;
                    teclado_do_google = false;
                    System.out.println("PRIMEIRO RADIO BUTTON LLLLLLLLLLLLLLLL");
                    break;
                case R.id.segundo_radio:
                    escolhas.check(R.id.segundo_radio);
                    teclado_personalizado = false;
                    teclado_do_google = true;
                    System.out.println("SEGUNDO RADIO BUTTON LLLLLLLLLLLLLLLL");
                    break;
            }
        });

        button.setOnClickListener(v -> {
            ConfigurationLogger logger = new ConfigurationLogger(teclado_personalizado, teclado_do_google, this);
            try {
                logger.writeConfig();
                Toast.makeText(this, "Salvo Com Sucesso", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(v -> {
            Intent voltar = new Intent(this, ContextChoose.class);
            startActivity(voltar);
        });
    }

    private void carregarInformacao (){
        if (new File(this.getFilesDir(), "config.json").exists()) {
            try {
                System.out.println("ENTROU-TRY-DE-CARREGAR-INFORMAÇÃO");
                ConfigurationLogger logger = new ConfigurationLogger(this);
                boolean[] opcoes = logger.readConfig();
                System.out.println(Arrays.toString(opcoes));
                checkRadioGroup(opcoes[0]);
                opcoes[0] = teclado_personalizado;
                opcoes[1] = teclado_do_google;
                Toast.makeText(this, "Lido Com Sucesso", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkRadioGroup(boolean tecladoPersonalizado){
        if(tecladoPersonalizado){
            escolhas.check(R.id.primeiro_radio);
        }else{
            escolhas.check(R.id.segundo_radio);
        }
    }
}
