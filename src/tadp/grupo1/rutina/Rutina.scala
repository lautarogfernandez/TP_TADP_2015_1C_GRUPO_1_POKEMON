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

  def realizarRutina(pokemon: Pokemon): Try[Pokemon] = {

    listaActividades.foldLeft(Try(pokemon)) {
      (pokemonAnterior, actividadActual) => pokemonAnterior.map(_.realizarActividad(actividadActual))
    }
    
//    listaActividades.foldLeft(pokemon) {
//      (pokemonAnterior, actividadActual) => pokemonAnterior.realizarActividad(actividadActual)
//    }
  }
}