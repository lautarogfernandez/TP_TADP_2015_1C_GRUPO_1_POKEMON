package Actividad

import tadp.grupo1.pokemon.Pokemon
import tadp.grupo1.pokemon.EstadoInvalido
import tadp.grupo1.pokemon.estado.KO
import tadp.grupo1.pokemon.estado.Dormido
import tadp.grupo1.pokemon.NoPuedeRealizarActividadPorKO

/**
 * @author usuario
 */
trait Actividad extends Function1[Pokemon, Pokemon]  {
    
  def applyActividad(pokemon : Pokemon) : Pokemon
  
  def apply(pokemon : Pokemon) = {
  
    val pokemonDespuesDeRealizarActivdad : Pokemon = pokemon.estado match {
      case _: KO            => throw new NoPuedeRealizarActividadPorKO
      case dormido: Dormido => pokemon.copy(estado = dormido.ignorasteActividad)
      case _                => applyActividad(pokemon)
    }

    if (!pokemonDespuesDeRealizarActivdad.estadoValido()){ 
      throw new EstadoInvalido()  // TODO ver de mejorar este if
    }
    
    pokemonDespuesDeRealizarActivdad
  
  }
}