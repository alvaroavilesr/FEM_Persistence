package es.upm.miw.bantumi.datos.databaseStorage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class Score {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "player_name")
    public String playerName;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "seeds_p1")
    public int seedsP1;

    @ColumnInfo(name = "seeds_p2")
    public int seedsP2;

    public Score(String playerName, String date, int seedsP1, int seedsP2) {
        this.playerName = playerName;
        this.date = date;
        this.seedsP1 = seedsP1;
        this.seedsP2 = seedsP2;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSeedsP1() {
        return seedsP1;
    }

    public void setSeedsP1(int seedsP1) {
        this.seedsP1 = seedsP1;
    }

    public int getSeedsP2() {
        return seedsP2;
    }

    public void setSeedsP2(int seedsP2) {
        this.seedsP2 = seedsP2;
    }
}
