package ufpb.luis.vitor.advinha.control.facade;

import android.content.Context;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import ufpb.luis.vitor.advinha.control.service.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ufpb.luis.vitor.advinha.model.Challenge;
import ufpb.luis.vitor.advinha.model.ContextDTO;

public class ServiceFacade {

    public LinkedList<ContextDTO> listaTmp;






    private static class ChallengeServiceCallback implements  Callback<ufpb.luis.vitor.advinha.model.Context> {

        LinkedList<Challenge> listinha;
        Context context;

        ChallengeServiceCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(@NotNull Call<ufpb.luis.vitor.advinha.model.Context> call, @NotNull Response<ufpb.luis.vitor.advinha.model.Context> response) {
            if (response.code() == 200) {
                this.listinha.addAll(response.body().getChallenges());
                Toast.makeText(context, "Contextos Recuperados com Sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao tentar recuperar os contextos", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<ufpb.luis.vitor.advinha.model.Context> call, @NotNull Throwable t) {
            Toast.makeText(context, "Erro ao se comunicar com o sistema, tentando reconectar", Toast.LENGTH_SHORT).show();
        }

        LinkedList<Challenge> getListinha() {
            return listinha;
        }
    }
}