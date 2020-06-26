package ufpb.luis.vitor.advinha.control.service;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {
    private static String URL = "https://educapi-v2.herokuapp.com/";
    private  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();




    public ContextService contextService() { return retrofit.create(ContextService.class);}
    //public ChallengeService challengeService(){ return retrofit.create(ChallengeService.class);}
}
