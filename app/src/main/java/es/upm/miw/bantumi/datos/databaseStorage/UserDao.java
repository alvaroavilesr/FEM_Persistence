package es.upm.miw.bantumi.datos.databaseStorage;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDao {

    @Insert
    void insert(Score score);

}
