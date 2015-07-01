package tadp.grupo1.rutina

import tadp.grupo1.pokemon.Pokemon
import scala.util.Try

/**
 * @author Alejandro
 */

class NingunaRutinaPudoSerCompletada extends RuntimeException

object AnalizadorDeRutinas {
  
  def analizarRutinasSegunCriterio(pokemon : Pokemon, rutinas : List[Rutina], criterio : (Pokemon, Pokemon) => Boolean) : String = {
    
    rutinas.map(rutina => (rutina, Try(rutina.realizarRutina(pokemon))) )
           .filter(tuplaRutinaPokemonResultante => tuplaRutinaPokemonResultante._2.isSuccess)
           .sortWith( (tupla1, tupla2) => criterio(tupla1._2.get, tupla2._2.get))
           .headOption
           .getOrElse(throw new NingunaRutinaPudoSerCompletada)
           ._1
           .nombre
    
  }
  
}