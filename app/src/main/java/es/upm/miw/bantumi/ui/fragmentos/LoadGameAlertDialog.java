package es.upm.miw.bantumi.ui.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.ui.actividades.MainActivity;

public class LoadGameAlertDialog extends DialogFragment {
    private final String game;

    public LoadGameAlertDialog(String game) {
        this.game = game;
    }
    @NonNull
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setMessage(R.string.txtDialogoPreguntaCargar)
                .setPositiveButton(
                        getString(android.R.string.ok),
                        (dialog, which) -> {
                            main.juegoBantumi.deserializa(game);
                            Snackbar.make(
                                    main.findViewById(android.R.id.content),
                                    getString(R.string.txtDialogoCargar),
                                    Snackbar.LENGTH_LONG
                            ).show();
                        }
                )
                .setNegativeButton(
                        getString(android.R.string.cancel),
                        null
                );

        return builder.create();
    }
}
