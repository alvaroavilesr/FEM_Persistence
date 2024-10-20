package es.upm.miw.bantumi.datos;

import java.io.FileOutputStream;
import java.io.IOException;

public class FilePersistence {
    public void saveGame(String estadoPartida, FileOutputStream fos) throws IOException {
        fos.write(estadoPartida.getBytes());
        fos.write('\n');
        fos.close();
    }
}
