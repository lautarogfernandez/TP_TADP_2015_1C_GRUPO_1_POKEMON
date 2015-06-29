package tadp.grupo1.rutina

import tadp.grupo1.pokemon.Pokemon
import scala.util.Try

/**
 * @author Alejandro
 */

class NingunaRutinaPudoSerCompletada extends RuntimeException

object AnalizadorDeRutinas {
  
  def analizarRutinasSegunCriterio(pokemon : Pokemon, rutinas : List[Rutina], criterio : (Pokemon, Pokemon) => Boolean) : Try[String] = {
    
    rutinas.map(rutina => (rutina.nombre, rutina.realizarRutina(pokemon)) )
           .filter(tuplaRutinaPokemonResultante => tuplaRutinaPokemonResultante._2.isSuccess)
           .sortWith( (tupla1, tupla2) => criterio(tupla1._2.get, tupla2._2.get))
           .headOption match{
              case Some(tupla1) => Try(tupla1._1)
              case None => Try(throw new NingunaRutinaPudoSerCompletada)
            }
    
    
    
//    getOrElse(throw new NingunaRutinaPudoSerCompletada)
//           ._1
//           .nombre
  }
  
}