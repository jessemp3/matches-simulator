package com.sporthela.matches_simulator.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sporthela.matches_simulator.databinding.MatchItemBinding;
import com.sporthela.matches_simulator.domain.Match;
import com.sporthela.matches_simulator.ui.DetailActivity;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Match match = matches.get(position);

        //adapta os dados da partida (recupera da api) para o nosso layout
        Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.ivHomeTeam);
        holder.binding.tvHomeTeamName.setText(match.getHomeTeam().getName());

        if(match.getHomeTeam().getScore() != null) {
            holder.binding.tvHomeTeamScore.setText(String.valueOf(match.getHomeTeam().getScore()));
        }

        if(match.getAwayTeam().getScore() != null) {
            holder.binding.tvAwayTeamScore.setText(String.valueOf(match.getAwayTeam().getScore()));
        }

        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivAwayTeam);
        holder.binding.tvAwayName.setText(match.getAwayTeam().getName());


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.Extras.MATCH,match);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}



