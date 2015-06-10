package tadp.grupo1.pokemon.condicion_evolucion

import Pokemon._
import tadp.grupo1.pokemon.Piedra
import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
abstract class Condicion {//seria un trait
  
  def usarPiedra(pokemon:Pokemon, piedra:Piedra)={
    
  }
  
  def intercambiar(pokemon:Pokemon)={
    
  }
  
  def nivelParaEvolucionar(pokemon:Pokemon)={
    
  }
  
}