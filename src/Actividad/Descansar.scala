package Actividad

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
object Descansar extends Actividad{
  
  override def apply(pokemon: Pokemon) = {
    pokemon.descansa()
  }
  
}