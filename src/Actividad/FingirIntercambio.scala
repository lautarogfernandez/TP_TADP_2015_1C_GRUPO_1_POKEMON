package Actividad

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
object FingirIntercambio extends Actividad{
  
  override def applyActividad(pokemon: Pokemon) = {

      pokemon.especie.sufriIntercambio(pokemon)  

  }  

}