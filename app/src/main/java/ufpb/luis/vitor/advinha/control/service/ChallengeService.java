package ufpb.luis.vitor.advinha.control.service;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.GET;
import ufpb.luis.vitor.advinha.model.ChallengeDTO;
public interface ChallengeService {

    @GET("challenges")
    Call<LinkedList<ChallengeDTO>> pegarChallenges();
}
