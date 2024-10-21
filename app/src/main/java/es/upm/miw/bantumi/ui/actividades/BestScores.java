package es.upm.miw.bantumi.ui.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.datos.databaseStorage.Score;
import es.upm.miw.bantumi.datos.databaseStorage.ScoreDao;
import es.upm.miw.bantumi.datos.databaseStorage.ScoreDatabase;
import es.upm.miw.bantumi.ui.adaptadores.ScoreAdapter;

public class BestScores extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textViewNoData;
    private ScoreAdapter scoreAdapter;
    private ScoreDao scoreDao;

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
        textViewNoData = findViewById(R.id.textViewNoData); // Referencia al TextView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el adaptador y lo configura en el RecyclerView
        scoreAdapter = new ScoreAdapter(new ArrayList<>());
        recyclerView.setAdapter(scoreAdapter);

        // Obtiene el DAO y recupera los 10 mejores puntajes
        scoreDao = ScoreDatabase.getInstancia(this).scoreDao();
        loadTopScores();
    }

    private void loadTopScores() {
        // Ejecuta la consulta en un hilo aparte para evitar bloquear la UI
        new Thread(() -> {
            List<Score> topScores = scoreDao.getTop10Scores();

            // Actualiza la interfaz en el hilo principal
            runOnUiThread(() -> {
                if (topScores.isEmpty()) {
                    // Mostrar mensaje de "no hay datos"
                    textViewNoData.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    // Mostrar el RecyclerView con los datos
                    textViewNoData.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    scoreAdapter.setScores(topScores);
                }
            });
        }).start();
    }
}
