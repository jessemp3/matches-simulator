package com.sporthela.matches_simulator.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;
import com.sporthela.matches_simulator.R;
import com.sporthela.matches_simulator.databinding.ActivityMainBinding;
import com.sporthela.matches_simulator.date.MatchesApi;
import com.sporthela.matches_simulator.domain.Match;
import com.sporthela.matches_simulator.ui.adapter.MatchesAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesApi;
    private RecyclerView.Adapter matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        binding.firstText.setText("testando com a classe em java");
        setContentView(binding.getRoot());

        setUpHttpCliente();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();
    }

    private void setUpHttpCliente() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jessemp3.github.io/matches-simulator-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matchesApi = retrofit.create(MatchesApi.class);
    }//configs do retrofit

    private void setupFloatingActionButton() {
//        todo: criar evento de click e simulação de partida
    }

    private void setupMatchesRefresh() {
//       todo: atualizar as partidas na ação do swipe
    }

    private void setupMatchesList() {
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));


        //doing: listar as partidas , consumindo nossa api;
        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    List<Match> matches = response.body();
//                    Log.i("Simulator", "Deu tudo certo! Voltaram partidas = " + matches.size());

                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                } else {
                    showErrorMessage();
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }
}


