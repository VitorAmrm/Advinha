package ufpb.luis.vitor.advinha.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.control.service.RetrofitInitializer;
import ufpb.luis.vitor.advinha.model.ContextDTO;
import ufpb.luis.vitor.advinha.utils.SaveContextGlobal;


public class ContextChoose extends AppCompatActivity {

    private RecyclerView rv;
    private GridLayoutManager layoutManager;
    private Toolbar toolbar;
    private MediaPlayer media;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.context_choose_activity);

        pegarContextoId(this, Long.valueOf(2));

        rv = findViewById(R.id.recycle_view);


        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        }else {
            layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        }

        fillRecycleView(SaveContextGlobal.getInstance().getLista());



        toolbar = findViewById(R.id.context_choose_toolbar);

        toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.settings_menu:
                    Intent tet = new Intent(this, SettingsActivity.class);
                    startActivity(tet);
                    break;
                case R.id.addContext:
                    dialogar();
                    break;
            }
            return true;
        });

        media = MediaPlayer.create(this,R.raw.audio_seleciona_tema);
        media.start();

    }

    public void fillRecycleView(LinkedList<ContextDTO> listaTemas) {
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new RecycleViewAdapter(this, listaTemas));


        }

    public void pegarContextoId(Context context,Long id) {
        Call<ContextDTO> call = new RetrofitInitializer().contextService().pegarContextId(id);
        call.enqueue(new Callback<ContextDTO>() {
            @Override
            public void onResponse(Call<ContextDTO> call, Response<ContextDTO> response) {
                if(response.isSuccessful()) {
                    boolean contextoIgual = false;
                    for(ContextDTO c : SaveContextGlobal.getInstance().getLista()){
                        if(c.getId().equals(response.body().getId())){
                            contextoIgual = true;
                            break;
                        }
                    }
                    if(!contextoIgual) {
                        SaveContextGlobal.getInstance().add(response.body());
                        fillRecycleView(SaveContextGlobal.getInstance().getLista());
                    }
                }else{
                    Toast.makeText(context, "ALGO OCORREU ERRADO, TENTE NOVAMENTE"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContextDTO> call, Throwable t) {
                Toast.makeText(context,"FALHA NA CONEXÃO, TENTE NOVAMENTE",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void dialogar () {
        View view = getLayoutInflater().inflate(R.layout.alert_fragment,null);
        EditText edt = view.findViewById(R.id.edtFragment);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog alerta = builder.create();

        view.findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            Long id = Long.valueOf(edt.getText().toString());
            pegarContextoId(this,id);
            alerta.dismiss();

        });
        alerta.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillRecycleView(SaveContextGlobal.getInstance().getLista());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fillRecycleView(SaveContextGlobal.getInstance().getLista());
    }
}
