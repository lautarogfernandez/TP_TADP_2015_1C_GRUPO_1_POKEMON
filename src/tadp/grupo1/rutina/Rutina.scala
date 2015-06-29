package tadp.grupo1.rutina

import Actividad.Actividad
import tadp.grupo1.pokemon.Pokemon
import scala.util.Try
import scala.util.Failure
import scala.util.Success

/**
 * @author Alejandro
 */
case class Rutina(nombre: String, listaActividades: List[Actividad]) {

  def realizarRutina(pokemon: Pokemon): Pokemon = {

    def pokemonDespuesDeRutina = listaActividades.foldLeft(Try(Try(pokemon))) {
//      (pokemonAnterior, actividadActual) => pokemonAnterior.flatMap( poke => poke.realizarActividad(actividadActual))
      (pokemonAnterior, actividadActual) => pokemonAnterior.map( poke => poke.realizarActividad(actividadActual))
    }
    
    pokemonDespuesDeRutina match {
      case Failure(exception) => throw exception
      case Success(pokemonDespuesDeRutina) => pokemonDespuesDeRutina
    }

//    listaActividades.foldLeft(pokemon) {
//      (pokemonAnterior, actividadActual) => pokemonAnterior.realizarActividad(actividadActual)
//    }
  }
}