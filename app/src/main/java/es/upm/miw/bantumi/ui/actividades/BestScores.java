package es.upm.miw.bantumi.ui.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.datos.databaseStorage.Score;
import es.upm.miw.bantumi.datos.databaseStorage.ScoreDao;
import es.upm.miw.bantumi.datos.databaseStorage.ScoreDatabase;
import es.upm.miw.bantumi.ui.adaptadores.ScoreAdapter;
import es.upm.miw.bantumi.ui.fragmentos.DeleteScoresAlertDialog;

public class BestScores extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout noDataLayout;
    private ScoreAdapter scoreAdapter;
    private ScoreDao scoreDao;
    private Button buttonDeleteAllScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_scores);

        // Establece las inserciones de recortes de pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewTopScores);
        noDataLayout = findViewById(R.id.noDataLayout);  // Referencia al layout "no hay datos"
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreAdapter = new ScoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(scoreAdapter);

        scoreDao = ScoreDatabase.getInstancia(this).scoreDao();
        loadTopScores();

        buttonDeleteAllScores = findViewById(R.id.buttonDeleteAllScores);
        buttonDeleteAllScores.setOnClickListener(v -> {
            new DeleteScoresAlertDialog().show(getSupportFragmentManager(), "DELETE_DIALOG");
        });
    }

    private void loadTopScores() {
        new Thread(() -> {
            List<Score> topScores = scoreDao.getTop10Scores();

            runOnUiThread(() -> {
                if (topScores.isEmpty()) {
                    noDataLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    noDataLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    buttonDeleteAllScores.setVisibility(View.VISIBLE);
                    scoreAdapter.setScores(topScores);
                }
            });
        }).start();
    }

    public void deleteAllScores() {
        new Thread(() -> {
            scoreDao.deleteAllScores();
            runOnUiThread(() -> {
                noDataLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                buttonDeleteAllScores.setVisibility(View.GONE);
                scoreAdapter.setScores(new ArrayList<>());
            });
        }).start();
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.txtDialogoBorrarPuntuaciones),
                Snackbar.LENGTH_LONG
        ).show();
    }
}
