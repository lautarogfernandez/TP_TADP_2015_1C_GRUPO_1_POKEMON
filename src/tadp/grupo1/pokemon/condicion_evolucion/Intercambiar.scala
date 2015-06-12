package tadp.grupo1.pokemon.condicion_evolucion

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
class Intercambiar extends Condicion{
  
  override def intercambiar(pokemon:Pokemon) : Pokemon = {
    pokemon.evolucionar()
  }
  
}