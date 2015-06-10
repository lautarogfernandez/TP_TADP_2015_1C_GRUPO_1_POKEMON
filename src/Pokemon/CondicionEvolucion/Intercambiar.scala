package Pokemon.CondicionEvolucion

import Pokemon._

/**
 * @author usuario
 */
class Intercambiar extends Condicion{
  
  override def intercambio(pokemon:Pokemon){
    pokemon.evolucionar()
  }
  
}