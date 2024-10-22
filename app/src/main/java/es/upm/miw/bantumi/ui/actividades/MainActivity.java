package es.upm.miw.bantumi.ui.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.upm.miw.bantumi.datos.databaseStorage.Score;
import es.upm.miw.bantumi.datos.databaseStorage.ScoreDatabase;
import es.upm.miw.bantumi.datos.fileStorage.FilePersistence;
import es.upm.miw.bantumi.ui.fragmentos.FinalAlertDialog;
import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.dominio.logica.JuegoBantumi;
import es.upm.miw.bantumi.ui.fragmentos.RestartAlertDialog;
import es.upm.miw.bantumi.ui.viewmodel.BantumiViewModel;

public class MainActivity extends AppCompatActivity {

    protected static final String LOG_TAG = "MiW";
    public JuegoBantumi juegoBantumi;
    private BantumiViewModel bantumiVM;
    int numInicialSemillas;
    private ScoreDatabase db;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        numInicialSemillas = getResources().getInteger(R.integer.intNumInicialSemillas);
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        crearObservadores();
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String playerName1 = sharedPrefs.getString("playerName1", "Jugador 1");
        String playerName2 = sharedPrefs.getString("playerName2", "Jugador 2");

        TextView tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvPlayer1.setText(playerName1);
        TextView tvPlayer2 = findViewById(R.id.tvPlayer2);
        tvPlayer2.setText(playerName2);
    }
    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FilePersistence filePersistence = new FilePersistence();
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, BantumiPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
            case R.id.opcReiniciarPartida:
                new RestartAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
                return true;
            case R.id.opcGuardarPartida:
                FileOutputStream fosSave;
                try {
                    fosSave = openFileOutput("SavedGames.txt", Context.MODE_PRIVATE);
                    filePersistence.saveGame(juegoBantumi.serializa(), fosSave);
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.txtDialogoGuardar),
                            Snackbar.LENGTH_LONG
                    ).show();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
                    e.printStackTrace();
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.txtDialogoGuardarError),
                            Snackbar.LENGTH_LONG
                    ).show();
                }
                return true;
            case R.id.opcBorrarPartida:
                FileOutputStream fosDelete;
                try {
                    fosDelete = openFileOutput("SavedGames.txt", Context.MODE_PRIVATE);
                    fosDelete.close();
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.txtDialogoBorrar),
                            Snackbar.LENGTH_LONG
                    ).show();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
                    e.printStackTrace();
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.txtDialogoBorrarError),
                            Snackbar.LENGTH_LONG
                    ).show();
                }
                return true;
            case R.id.opcRecuperarPartida:
                BufferedReader finCheck;
                BufferedReader finRead;
                try {
                    finCheck = new BufferedReader(
                            new InputStreamReader(openFileInput("SavedGames.txt")));
                    if(filePersistence.checkSavedGames(finCheck)) {
                        finRead = new BufferedReader(
                                new InputStreamReader(openFileInput("SavedGames.txt")));
                        String savedGame = filePersistence.loadGame(finRead);
                        juegoBantumi.deserializa(savedGame);
                        Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.txtDialogoCargar),
                                Snackbar.LENGTH_LONG
                        ).show();
                    }else{
                        Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.txtDialogoFicheroVacio),
                                Snackbar.LENGTH_LONG
                        ).show();
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
                    e.printStackTrace();
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.txtDialogoCargarError),
                            Snackbar.LENGTH_LONG
                    ).show();
                }
                return true;
            case R.id.opcMejoresResultados:
                startActivity(new Intent(this, BestScores.class));
                return true;
            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    /**
     * Acción que se ejecuta al pulsar sobre cualquier hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                Log.i(LOG_TAG, "* Juega Jugador");
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                Log.i(LOG_TAG, "* Juega Computador");
                juegoBantumi.juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        String playerName1 = sharedPrefs.getString("playerName1","Jugador 1");
        String playerName2 = sharedPrefs.getString("playerName2","Jugador 2");

        String texto = (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas)
                ? "Gana " + playerName1
                : "Gana " + playerName2;
        if (juegoBantumi.getSemillas(6) == 6 * numInicialSemillas) {
            texto = "¡¡¡ EMPATE !!!";
        }

        db = ScoreDatabase.getInstancia(this);
        Score score = new Score(
                playerName1,
                playerName2,
                Calendar.getInstance().getTime().toString(),
                juegoBantumi.getSemillas(6),
                juegoBantumi.getSemillas(13)
        );
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            db.scoreDao().insert(score);
        });
        executorService.shutdown();

        // terminar
        new FinalAlertDialog(texto).show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

}