package es.upm.miw.bantumi.datos.databaseStorage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();

    private static ScoreDatabase instancia;

    // Método para obtener una instancia de la base de datos
    public static synchronized ScoreDatabase getInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(context.getApplicationContext(),
                            ScoreDatabase.class, "mi_base_de_datos")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instancia;
    }
}
