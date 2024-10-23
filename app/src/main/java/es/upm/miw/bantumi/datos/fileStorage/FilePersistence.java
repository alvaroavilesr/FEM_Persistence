package es.upm.miw.bantumi.datos.fileStorage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilePersistence {
    public void saveGame(String gameState, FileOutputStream fos) throws IOException {
        fos.write(gameState.getBytes());
        fos.write('\n');
        fos.close();
    }

    public boolean checkSavedGames(BufferedReader fin) throws IOException {
        boolean hasContent = false;
        String line = fin.readLine();
        while (line != null) {
            hasContent = true;
            line = fin.readLine();
        }
        fin.close();
        return hasContent;
    }
    public String loadGame(BufferedReader fin) throws IOException {
        String fileContent = fin.readLine();
        fin.close();
        return fileContent;
    }
}
