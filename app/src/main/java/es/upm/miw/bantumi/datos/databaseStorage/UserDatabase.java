package es.upm.miw.bantumi.datos.databaseStorage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDatabase instancia;

    // Método para obtener una instancia de la base de datos
    public static synchronized UserDatabase getInstancia(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "mi_base_de_datos")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instancia;
    }
}
