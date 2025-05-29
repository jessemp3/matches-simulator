package com.sporthela.matches_simulator.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;
import com.sporthela.matches_simulator.R;
import com.sporthela.matches_simulator.databinding.ActivityMainBinding;
import com.sporthela.matches_simulator.date.MatchesApi;
import com.sporthela.matches_simulator.domain.Match;
import com.sporthela.matches_simulator.ui.adapter.MatchesAdapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesApi;
    private MatchesAdapter matchesAdapter = new MatchesAdapter(Collections.emptyList());

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
        binding.fabSimulate.setOnClickListener(view -> {
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    if (matchesAdapter != null && matchesAdapter.getMatches() != null) {
                        Random random = new Random();
                        List<Match> matches = matchesAdapter.getMatches();

                        // Log para debug
//                        Log.d("MainActivity", "Simulando " + matches.size() + " partidas");

                        // Percorre TODAS as partidas e sorteia os placares
                        for (int i = 0; i < matches.size(); i++) {
                            Match match = matches.get(i);

                            // Gera placar aleatório entre 0 e 5 para cada time
                            int homeScore = random.nextInt(6);
                            int awayScore = random.nextInt(6);

                            match.getHomeTeam().setScore(homeScore);
                            match.getAwayTeam().setScore(awayScore);

                            // Log para debug
//                            Log.d("MainActivity", "Partida " + i + ": " +
//                                    match.getHomeTeam().getName() + " " + homeScore +
//                                    " x " + awayScore + " " + match.getAwayTeam().getName());
//                        }

                            // MUITO IMPORTANTE: Notifica que TODOS os dados mudaram
                            matchesAdapter.notifyDataSetChanged();

                            // Mostra mensagem de sucesso
                            Snackbar.make(binding.fabSimulate, "Partidas simuladas com sucesso!", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("MainActivity", "Adapter ou lista de partidas é null");
                    }
                }
            });
        });
    }

    private void setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener(this::findMatchesFromApi);
    }

    private void setupMatchesList() {

        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));

        binding.rvMatches.setAdapter(matchesAdapter);

        findMatchesFromApi();
    }

    //doing: listar as partidas , consumindo nossa api;
    private void findMatchesFromApi() {
        binding.srlMatches.setRefreshing(true);
        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(@NonNull Call<List<Match>> call, @NonNull Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    List<Match> matches = response.body();
//                    Log.i("Simulator", "Deu tudo certo! Voltaram partidas = " + matches.size());

                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                } else {
                    showErrorMessage();
                }
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Match>> call, @NonNull Throwable t) {
                showErrorMessage();
                binding.srlMatches.setRefreshing(false);
            }
        });
    }

    ;

    private void showErrorMessage() {
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }
}


