package ufpb.luis.vitor.advinha.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.control.service.RetrofitInitializer;
import ufpb.luis.vitor.advinha.model.ChallengeDTO;
import ufpb.luis.vitor.advinha.model.ContextDTO;

public class ContextChoose extends AppCompatActivity {

    private RecyclerView rv;
    private GridLayoutManager layoutManager;
    private LinkedList<ContextDTO> listaContextos = new LinkedList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.context_choose_activity);
        rv = findViewById(R.id.recycle_view);
        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        pegarTodosContextos(this);
        pegarTodosChallenges(this);


    }


    public void fillRecycleView(LinkedList<ContextDTO> listaTemas) {
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new RecycleViewAdapter(this, listaTemas));
    }

    public void fillContextWithChallenges(LinkedList<ChallengeDTO> listaChallenge) {

        for (ChallengeDTO c : listaChallenge) {
            if ((c.getId() >= 13) && (c.getId() <= 21)) {
                listaContextos.get(4).addToList(c);

            } else if ((c.getId() >= 22) && (c.getId() <= 30)) {
                listaContextos.get(0).addToList(c);

            } else if ((c.getId() >= 31) && (c.getId() <= 35)) {
                listaContextos.get(1).addToList(c);

            } else if ((c.getId() >= 36) && (c.getId() <= 41)) {
                listaContextos.get(2).addToList(c);

            } else if ((c.getId() >= 42) && (c.getId() <= 45)) {
                listaContextos.get(3).addToList(c);

            }
        }

    }


    public void pegarTodosContextos(Context context) {
        Call call = new RetrofitInitializer().contextService().pegarContextos();
        call.enqueue(new Callback<LinkedList<ContextDTO>>() {

            @Override
            public void onResponse(Call<LinkedList<ContextDTO>> call, Response<LinkedList<ContextDTO>> response) {
                if (response.code() == 200) {
                    listaContextos = response.body();
                    fillRecycleView(listaContextos);
                    Toast.makeText(context, "Sucesso", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(context, "Algo deu Errado", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<LinkedList<ContextDTO>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    public void pegarTodosChallenges(Context context) {
        Call call = new RetrofitInitializer().challengeService().pegarChallenges();
        call.enqueue(new Callback<LinkedList<ChallengeDTO>>() {

            @Override
            public void onResponse(Call<LinkedList<ChallengeDTO>> call, Response<LinkedList<ChallengeDTO>> response) {

                if (response.code() == 200) {
                    System.out.println(response.body());
                    fillContextWithChallenges(response.body());
                    Toast.makeText(context, "Sucesso", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(context, "Algo deu Errado", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<LinkedList<ChallengeDTO>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        pegarTodosContextos(this);
        pegarTodosChallenges(this);

    }
}
