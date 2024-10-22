---- FEM Practica Persistencia ----

Autor: Alvaro Aviles Redondo
Correo: Alvaro.avilesr@alumnos.upm.es
Link repositorio GitHub: https://github.com/alvaroavilesr/FEM_Persistence.git

-------------------------
ACTIVIDADES OBLOGATORIAS
-------------------------

1. Reiniciar partida

Para realizar esta parte, he añadido otro caso al switch del options menu (método onOptionsItemSelected, clase MainActivity), donde creo un nuevo fragmento (RestartAlertDialog), que es el encargado de o bien reiniciar si se elige esa opción o bien no hacer nada y cerrarse.

2. Guardar partida

Para esta parte, he creado otra opción en el switch del options menu, donde controlo cuando se da al botón guardar.

Para ello primero he tenido que implementar el método serializa de la clase JuegoBantumi, donde recuperamos el estado actual de la partida como un String.

Luego creo la clase FilePersistence, que pasándole ya la conexión al fichero creada en la clase MainActivity y el estado de la partida como string, lo guarda.

**Nota: he intentado guardar el nombre del fichero en el XML de Strings, pero me lo guardaba como int, por eso esta directamente indicado en el código a pesar de no ser una buena practica.

3. Cargar partida

Se ha añadido una nueva opción en el switch de menu, donde se controla la funcionalidad de cargar partida. Para ello se crea un BufferedReader para leer el fichero de juegos guardados. Primero comprobamos que haya alguna partida guardada, y si la hay leemos la partida y la deserializamos para poner el estado de la partida actual al leído.

4. Guardar puntuación

Para esta parte, se han usado varios mecanismos:

- Se ha implementado el uso de la base de datos room, para ello todo el código esta en la carpeta datos/databaseStorage.
- Cuando se termina una partida, se guarda en base de datos un objeto score, donde se insertan los nombres de los jugadores obteniéndolos de las preferencias.
- Para el uso de las preferencias, el usuario podrá definir los valores por defecto en Ajustes (si no los valores por defecto definidos en las preferencias son jugador 1 y 2)
- Se ha implementado el método onResume(para actualizar el valor de los nombres al salir de la actividad principal en caso de que cambien las preferencias)

5. Mejores resultados

Para la parte de mejores resultados se han implementeado los siguientes cambios:

- Se han creado los métodos en el DAO getTop10Scores() y deleteAllScores().
- Se ha creado una actividad para manejar y gestionar los mejores resultados.
- Para mostrar los mejores resultados, si no hay ninguno se mostrará un mensaje indicándolo. En caso de que si haya, se mostraran los 10 (o menos si hay menos en base de datos), así como un botón para borrar todas las puntuaciones.
Este botón primero lanzará un fragmento para confirmar si verdaderamente se desean borrar estas puntuaciones.

-----------------------
ACTIVIDADES OPCIONALES
-----------------------

1. Borrar partida guardada

Se ha añadido un botón en el menu para eliminar la partida que se hubiera guardado. Para ello se abre el fichero para escritura en modo privado. Se controla si el fichero esta vacio previamente o no.

2. Filtrar en los mejores resultados por numero de semillas

3. Filtrar en los mejores resultados por nombre de jugador