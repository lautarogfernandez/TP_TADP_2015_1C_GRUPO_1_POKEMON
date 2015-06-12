package tadp.grupo1.pokemon.condicion_evolucion

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.Piedra
import tadp.grupo1.pokemon.Pokemon
import tadp.grupo1.pokemon.genero._

/**
 * @author usuario
 */
abstract class Condicion {//seria un trait
  
  def usarPiedra(pokemon:Pokemon, piedra:Piedra): Pokemon ={
    pokemon
  }
  
  def intercambio(pokemon:Pokemon): Pokemon ={
    pokemon.genero match{
      case _:Macho => pokemon.cambiarPeso(1)
      case _:Hembra => pokemon.cambiarPeso(-10)
    }
  }
  
  def nivelParaEvolucionar(pokemon:Pokemon) : Pokemon = {
    pokemon
  }
  
}