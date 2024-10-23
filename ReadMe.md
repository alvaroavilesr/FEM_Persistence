# Práctica 1 FEM: Persistencia de datos

Práctica de la asignatura FEM del MIW. En esta práctica se deben añadir al juego base Bantumi que se nos proporciona, las características descritas en el enunciado.

Este fichero trata de abarcar las distintas funcionalidades desarrolladas, explicando los cambios que se han realizado sobre el código.

## Información general💡

- **Autor**: Alvaro Aviles Redondo
- **Correo**: alvaro.avilesr@alumnos.upm.es
- **GitHub de la práctica**: https://github.com/alvaroavilesr/FEM_Persistence.git
- **Enunciado de la práctica**: https://moodle.upm.es/titulaciones/oficiales/mod/assign/view.php?id=29029

## Actividades obligatorias 📝

### 1. Reiniciar partida 🔄

- **¿Qué se debe hacer?**
Al pulsar la opción "Reiniciar" se mostrará un diálogo de confirmación. En caso de respuesta afirmativa se procederá a reiniciar la partida actual. 

- **¿Cómo se ha hecho?**
Dentro de la actividad principal, en el método "onOptionsItemSelected", para el caso del botón "opcReiniciarPartida", se lanza un nuevo fragmento, "RestartAlertDialog". En este fragmento se muestra un dialogo donde en caso de pulsar OK, se ejecuta el método ya implementado "inicializar", de la clase "JuegoBantumi".

### 2. Guardar partida 💾

- **¿Qué se debe hacer?**
Esta opción permite guardar la situación actual del tablero. Sólo es necesario guardar una única partida y se empleará el sistema de ficheros del dispositivo.

- **¿Cómo se ha hecho?**
Dentro de la actividad principal, en el método "onOptionsItemSelected", para el caso del botón "opcGuardarPartida", se llama al método "saveGame()", el cual mediante el uso de "FileOutputStream", escribe en un fichero el estado actual de la partida. Para ello se ha tenido que implementar el método "serializa()" de la clase "JuegoBantumi", donde se obtiene el estado en forma de string.

### 3. Recuperar partida ↩️

- **¿Qué se debe hacer?**
Si existe, recupera el estado de una partida guardada (si se hubiera modificado la partida actual, se solicitará confirmación).

- **¿Cómo se ha hecho?**
Dentro de la actividad principal, en el método "onOptionsItemSelected", para el caso del botón "opcRecuperarPartida", se llama al método "loadGame()", el cual comprueba si el fichero que contiene la partida guardada esta vacío o no. En caso de estar vacío lo notifica y en caso contrario lee el contenido con un BufferedReader. Luego se verifica que el estado de la partida no haya cambiado o sea el iniciar para deserializar la partida (método implementado en la clase "JuegoBantumi"). Si la partida ha cambiado se muestra un fragmento "LoadGameAlertDialog" que mostrara un dialogo de confirmación.

### 4. Guardar puntuación 💾

- **¿Qué se debe hacer?**
Al finalizar cada partida se deberá guardar la información necesaria para generar un listado de resultados. Dicho listado deberá incluir -al menos- el nombre del jugador (se obtendrá de los ajustes del juego), el día y hora de la partida y el número de semillas que quedaron en cada almacén. La información se deberá guardar en una base de datos.

- **¿Cómo se ha hecho?**
Para esta parte se han realizado múltiples cambios en la aplicación:
**1** - Se crea una clase entidad "Score", donde se define el objeto que será guardado en la base de datos Room.
**2** - Se crea una interfaz "ScoreDao", con el método insert.
**3** - Se crea la clase abstracta "ScoreDatabase" que será la encargada de instanciar la base de datos.
**4** - Se implementa la actividad de los ajustes "BantumiPrefs", donde se permite guardar al usuario el nombre de ambos jugadores en las preferencias (los valores por defecto son Jugador 1 y Jugador 2). Esta actividad será ejecutada al pulsar el botón correspondiente que ejecutará en el método "onOptionsItemSelected", para el caso del botón "opcAjustes".
**5** - Dentro de la actividad principal, en el método "finJuego()" se obtienen los valores de los nombres de los jugadores de las preferencias y se guarda en base datos un objeto de la clase "Score" con los datos de los nombres, semillas y fecha.
**6** - Para que se actualicen los valores de los nombres de los jugadores definidos en las preferencias, en la actividad principal se implementa el método "onResume()", que cargará los nuevos nombres y los podrá en los "TextView" correspondientes.

### 5. Mejores resultados📋

- **¿Qué se debe hacer?**
Esta opción mostrará el histórico con los diez mejores resultados obtenidos ordenados por el mayor número de semillas obtenido por cualquier jugador. Además, incluirá una opción -con confirmación- para eliminar todos los resultados guardados.

- **¿Cómo se ha hecho?**
Para esta parte se han realizado múltiples cambios en la aplicación:
**1** - Dentro de la actividad principal, en el método "onOptionsItemSelected", para el caso del botón "opcMejoresResultados", se lanza una nueva actividad "BestScores".
**2** - Se define como estará estructurada la nueva actividad en el fichero "best_scores.xml".
**3** - Se añaden a la interfaz "ScoreDao", con el método "getTop10Scores()" y el método "deleteAllScores()".
**4** - En la actividad "BestScores" se cargan todos los elementos del layout en el método "onCreate()" y se llama al metodo "loadTopScores()".
**5** - El método "loadTopScores()" mostrará la lista de los 10 mejores resultados ordenados por numero de semillas y un botón para borrar todas las puntuaciones o bien un mensaje en caso de no haber ninguna puntuación guardada en base de datos.

## Actividades opcionales 📝

### 1. Borrar partida🗑️

- **¿Qué se debe hacer?**
Se creara un nuevo botón en el menú de la actividad principal, que al pulsarlo, si hay una partida guardada, la borre.

- **¿Cómo se ha hecho?**
Se añade un nuevo "item" en el menú que para borrar la partida. Dentro de la actividad principal, en el método "onOptionsItemSelected", para el caso del botón "opcBorrarPartida", se llama al método "deleteGame()", el cual comprueba si hay alguna partida guardada y en caso de haberla, la borrar (abriendo para ello el fichero para escritura en modo privado).

### 2. Filtro semillas mínimas⚙️

- **¿Qué se debe hacer?**
En la pantalla de mejores resultados, se añadirá un filtro para poder indicar el numero mínimo de semillas para que de los 10 mejores resultados, solo se muestren los que cumplan con el filtro.

- **¿Cómo se ha hecho?**
Para esta parte se han realizado múltiples cambios en la aplicación:
**1** - Se añaden un "EditText" y un "Boton" al layout de la actividad "BestScores".
**2** - En la actividad "BestScores" se inicializan estos dos componente y se le asigna el listener al boton.
**3** - Se controlan las acciones para cuando el boton del filtro se pulse. En caso de que ninguno de los 10 mejores resultados satisfaga el filtro, se indicara con un mensaje. En caso contrario, se mostraran solo las puntuaciones que cumplan el filtro.