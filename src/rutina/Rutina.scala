package rutina

import Actividad.Actividad
import tadp.grupo1.pokemon.Pokemon

/**
 * @author Alejandro
 */
case class Rutina(listaActividades: List[Actividad]) {

  def realizarRutina(pokemon: Pokemon): Pokemon = {

    listaActividades.foldLeft(pokemon: Pokemon) {
      (pokemonAnterior, actividadActual) => pokemon.realizarActividad(actividadActual)
    }
  }
}