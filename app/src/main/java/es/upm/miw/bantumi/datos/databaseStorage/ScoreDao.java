package es.upm.miw.bantumi.datos.databaseStorage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Score score);

    @Query("SELECT *, CASE WHEN seeds_p1 > seeds_p2 THEN seeds_p1 ELSE seeds_p2 END as max_seeds " +
            "FROM scores " +
            "ORDER BY max_seeds DESC " +
            "LIMIT 10")
    List<Score> getTop10Scores();
}
