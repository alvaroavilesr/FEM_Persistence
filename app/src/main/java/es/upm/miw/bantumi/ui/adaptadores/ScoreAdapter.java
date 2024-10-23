package es.upm.miw.bantumi.ui.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upm.miw.bantumi.datos.databaseStorage.Score;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private final List<Score> scoreList;

    public ScoreAdapter(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public void setScores(List<Score> scores) {
        this.scoreList.clear();
        this.scoreList.addAll(scores);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scoreList.get(position);
        holder.playerNames.setText(score.playerName1 + " vs " + score.playerName2);
        holder.scoreDetails.setText("Seeds: " + score.seedsP1 + " - " + score.seedsP2 + " on " + score.date);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView playerNames;
        TextView scoreDetails;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            playerNames = itemView.findViewById(android.R.id.text1);
            scoreDetails = itemView.findViewById(android.R.id.text2);
        }
    }
}