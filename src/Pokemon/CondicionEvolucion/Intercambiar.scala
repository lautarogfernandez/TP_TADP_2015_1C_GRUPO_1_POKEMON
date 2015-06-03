package Pokemon.CondicionEvolucion

import Pokemon._

/**
 * @author usuario
 */
class Intercambiar extends Condicion{
  
  def intercambiar(pokemon:Pokemon){
    pokemon.evolucionar(pokemon.especie.evolucion())
  }
  
}