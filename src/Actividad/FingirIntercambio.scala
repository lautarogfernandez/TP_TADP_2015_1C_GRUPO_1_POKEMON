package Actividad

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
object FingirIntercambio extends Actividad{
  
  override def apply(pokemon: Pokemon) = {
    pokemon.fingirIntercambio()
  }  
  
}