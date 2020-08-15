package ufpb.luis.vitor.advinha.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.LinkedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.control.service.RetrofitInitializer;
import ufpb.luis.vitor.advinha.model.ContextDTO;
import ufpb.luis.vitor.advinha.model.Creator;
import ufpb.luis.vitor.advinha.utils.SaveContextGlobal;


public class ContextChoose extends AppCompatActivity {

    static RecyclerView rv;
    private Toolbar toolbar;
    private MediaPlayer media;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.context_choose_activity);

        pegarContextoId(this, 17L);

        rv = findViewById(R.id.recycle_view);


        fillRecycleView(this,SaveContextGlobal.getInstance().getLista(),rv);



        toolbar = findViewById(R.id.context_choose_toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.settings_menu:
                    Intent tet = new Intent(this, SettingsActivity.class);
                    startActivity(tet);
                    break;
                case R.id.addContext:
                    ShowMenu();
                    break;
            }
            return true;
        });

        media = MediaPlayer.create(this,R.raw.audio_seleciona_tema);
        media.start();

    }

    public static void fillRecycleView(Context context,LinkedList<ContextDTO> listaTemas,RecyclerView rv) {
        GridLayoutManager layoutManager;
        int orientation = context.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        }
        else {
            layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        }
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new RecycleViewAdapter(context, listaTemas));
    }

    public void fillRecycleViewImportUser(LinkedList<ContextDTO> listaTemas,RecyclerView rv) {
        LinearLayoutManager layoutImportUser = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rv.setLayoutManager(layoutImportUser);
        rv.setAdapter(new ImportViewAdapter(this, listaTemas));
    }


    private void pegarContextoId(Context context,Long id) {
        Call<ContextDTO> call = new RetrofitInitializer().contextService().pegarContextId(id);
        call.enqueue(new Callback<ContextDTO>() {
            @Override
            public void onResponse(Call<ContextDTO> call, Response<ContextDTO> response) {
                if(response.isSuccessful()) {
                    SaveContextGlobal.getInstance().add(response.body());
                    fillRecycleView(context,SaveContextGlobal.getInstance().getLista(),rv);
                }
                else if(response.code() == 404){
                    Toast.makeText(context, "Esse contexto não existe ou pode ter sido apagado", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Não foi possivel recuperar o contexto", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContextDTO> call, Throwable t) {
                Toast.makeText(context,"FALHA NA CONEXÃO, TENTE NOVAMENTE",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pegarContextoUser(Context context,Long Userid,RecyclerView rv){
        Call<LinkedList<ufpb.luis.vitor.advinha.model.ContextDTO>> call = new RetrofitInitializer().contextService().findByUser(Userid);
        call.enqueue(new Callback<LinkedList<ufpb.luis.vitor.advinha.model.ContextDTO>>() {
            @Override
            public void onResponse(Call<LinkedList<ufpb.luis.vitor.advinha.model.ContextDTO>> call, Response<LinkedList<ufpb.luis.vitor.advinha.model.ContextDTO>> response) {
                if(response.isSuccessful()){
                    fillRecycleViewImportUser(response.body(),rv);
                    //Toast.makeText(context,"Contextos importados com sucesso",Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(context, "Esse usuário não existe ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Os contextos não foram importados", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LinkedList<ufpb.luis.vitor.advinha.model.ContextDTO>> call, Throwable t) {
                Toast.makeText(context,"Não foi possivel estabelecer uma conexão ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogarPorId () {
        View view = getLayoutInflater().inflate(R.layout.alert_fragment,null);
        EditText edt = view.findViewById(R.id.edtFragment);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog alerta = builder.create();

        view.findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            if(edt.getText().toString().isEmpty()){
                Toast.makeText(this,"Digite o ID do contexto",Toast.LENGTH_SHORT).show();
            }
            else {
                pegarContextoId(this, Long.valueOf(edt.getText().toString()));
                alerta.dismiss();
            }
        });
        alerta.show();

    }

    private void dialogarPorUser(){
        View view = getLayoutInflater().inflate(R.layout.alert_fragment_import_user,null);
        EditText edt = view.findViewById(R.id.edtEmailFragment);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog alerta = builder.create();

        view.findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            alerta.dismiss();
            if(edt.getText().toString().isEmpty()){
                Toast.makeText(this,"Digite o email de um usuário",Toast.LENGTH_SHORT).show();
            }
            else {
                View pop = getLayoutInflater().inflate(R.layout.recyclerview_import_user, null);
                AlertDialog.Builder bolder = new AlertDialog.Builder(this);
                RecyclerView reciclagem = pop.findViewById(R.id.recycle_import_user);
                pegarContextoUser(this, pegarCreator(edt.getText().toString()), reciclagem);
                bolder.setView(pop);
                AlertDialog popup = bolder.create();
                popup.show();
            }

        });
        alerta.show();
    }

    private void ShowMenu() {
        View v = findViewById(R.id.addContext);
        PopupMenu pop = new PopupMenu(this, v);
        pop.getMenuInflater().inflate(R.menu.import_context_menu,pop.getMenu());
        pop.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.importById:
                    dialogarPorId();
                    break;
                case R.id.importByUser:
                    dialogarPorUser();
                    break;
            }

            return true;
        });
        pop.show();

    }

    private Long pegarCreator(String email){
        Long userId = 0L;
        for(Creator c : SaveContextGlobal.getInstance().getUsers()){
            if(email.equals(c.getEmail())){
                userId = c.getId();
                break;
            }
        }
        return userId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillRecycleView(this,SaveContextGlobal.getInstance().getLista(),rv);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fillRecycleView(this,SaveContextGlobal.getInstance().getLista(),rv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fillRecycleView(this,SaveContextGlobal.getInstance().getLista(),rv);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        fillRecycleView(this,SaveContextGlobal.getInstance().getLista(),rv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fillRecycleView(this,SaveContextGlobal.getInstance().getLista(),rv);
    }
}
