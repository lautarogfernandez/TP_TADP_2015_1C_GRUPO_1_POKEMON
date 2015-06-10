package tadp.grupo1.pokemon.condicion_evolucion

import Pokemon._
import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
class Intercambiar extends Condicion{
  
  override def intercambiar(pokemon:Pokemon){
    pokemon.evolucionar()
  }
  
}