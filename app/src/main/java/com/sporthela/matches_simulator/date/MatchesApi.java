package com.sporthela.matches_simulator.date;

import com.sporthela.matches_simulator.domain.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesApi {

    @GET("matches_api.json")//endpoint
    Call<List<Match>> getMatches();
}

