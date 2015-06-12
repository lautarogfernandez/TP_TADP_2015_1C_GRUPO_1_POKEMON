package tadp.grupo1.pokemon.condicion_evolucion

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.Piedra
import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
abstract class Condicion {//seria un trait
  
  def usarPiedra(pokemon:Pokemon, piedra:Piedra): Pokemon ={
    pokemon
  }
  
  def intercambiar(pokemon:Pokemon): Pokemon ={
    pokemon
  }
  
  def nivelParaEvolucionar(pokemon:Pokemon) : Pokemon = {
    pokemon
  }
  
}