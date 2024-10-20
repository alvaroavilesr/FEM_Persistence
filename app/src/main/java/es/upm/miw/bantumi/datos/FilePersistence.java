package es.upm.miw.bantumi.datos;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilePersistence {
    public void saveGame(String estadoPartida, FileOutputStream fos) throws IOException {
        fos.write(estadoPartida.getBytes());
        fos.write('\n');
        fos.close();
    }

    public boolean checkSavedGames(BufferedReader fin) throws IOException {
        boolean hasContent = false;
        String linea = fin.readLine();
        while (linea != null) {
            hasContent = true;
            linea = fin.readLine();
        }
        return hasContent;
    }
    public String loadGame(BufferedReader fin) throws IOException {
        String contenidoFichero = fin.readLine();
        fin.close();
        return contenidoFichero;
    }
}
