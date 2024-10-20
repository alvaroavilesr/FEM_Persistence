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


