package ufpb.luis.vitor.advinha.control.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import retrofit2.http.Query;
import ufpb.luis.vitor.advinha.model.ContextDTO;

public interface ContextService {


    @GET("contexts")
    Call<LinkedList<ContextDTO>> pegarContextos();

    @GET("contexts/{id}")
    Call<ContextDTO> pegarContextId(@Path("id")Long id);

    @GET("contexts/{id}")
    Call<ufpb.luis.vitor.advinha.model.Context>  pegarChallengesDoContexto(@Path("id") Long id);

    @GET("contexts")
    Call<JsonArray> pegarContextosPorPagina(@Query("page") int page);

}
