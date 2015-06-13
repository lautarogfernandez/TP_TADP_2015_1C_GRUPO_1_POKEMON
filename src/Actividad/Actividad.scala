package Actividad

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
abstract class Actividad extends Function1[Pokemon, Pokemon]  {
  
  def apply(pokemon : Pokemon) = { pokemon  } // TODO hecho aca para no tener que definirlo en todas las subclases 
  
}