package es.upm.miw.bantumi.ui.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.ui.actividades.BestScores;

public class DeleteScoresAlertDialog extends DialogFragment {
    @NonNull
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final BestScores activity = (BestScores) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setMessage(R.string.txtDialogoPreguntaEliminar)
                .setPositiveButton(
                        getString(android.R.string.ok),
                        (dialog, which) -> activity.deleteAllScores()
                )
                .setNegativeButton(
                        getString(android.R.string.cancel),
                        null
                );

        return builder.create();
    }
}
