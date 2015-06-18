package tadp.grupo1.pokemon.condicion_evolucion

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.genero._

/**
 * @author usuario
 */
trait Condicion {
  
  def usarPiedra(pokemon:Pokemon, piedra:Piedra): Pokemon ={
    pokemon
  }
  
  def intercambio(pokemon:Pokemon): Pokemon ={
    pokemon.genero match{
      case Macho => pokemon.cambiarPeso(1)
      case Hembra => pokemon.cambiarPeso(-10)
    }
  }
  
  def nivelParaEvolucionar(pokemon:Pokemon) : Pokemon = {
    pokemon
  }
  
}


class Intercambiar extends Condicion{
  
  override def intercambio(pokemon:Pokemon) : Pokemon = {
    pokemon.evolucionar()
  }
  
}


class SubirNivel (val nivelNecesarioParaEvolucionar:Int) extends Condicion{
  
  override def nivelParaEvolucionar(pokemon:Pokemon) : Pokemon = {
    var nuevoPokemon = pokemon    
    if (pokemon.nivel >= nivelNecesarioParaEvolucionar)
      nuevoPokemon = pokemon.evolucionar()    
    nuevoPokemon
  }
  
}


class UsarPiedraParaEvolucion extends Condicion{}


class UsarPiedraLunar extends Condicion{}


class NoEvoluciona extends Condicion{}