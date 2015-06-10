package Pokemon.CondicionEvolucion

import Pokemon._
import Pokemon.Genero._

/**
 * @author usuario
 */
abstract class Condicion {//seria un trait
  
  def usarPiedra(pokemon:Pokemon, piedra:Piedra)={
    
  }
  
  def intercambio(pokemon:Pokemon)={
    pokemon.genero match{
      case _:Macho => pokemon.cambiarPeso(1)
      case _:Hembra => pokemon.cambiarPeso(-10)
    }
  }
  
  def nivelParaEvolucionar(pokemon:Pokemon)={
    
  }
  
}