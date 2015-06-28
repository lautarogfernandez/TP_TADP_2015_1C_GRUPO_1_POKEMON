package Actividad

import tadp.grupo1.pokemon.Pokemon
import tadp.grupo1.pokemon.estado.EstadoNormal
import tadp.grupo1.pokemon.estado.Dormido

/**
 * @author usuario
 */
object Descansar extends Actividad{
  
  override def applyActividad(pokemon: Pokemon) = {
    
    val ataquesConPARecargados = pokemon.ataques.map(ataque => ataque.reestablecerPA)
    val pokemonConAtaquesRecargados = pokemon.copy(ataques = ataquesConPARecargados)
    
    pokemonConAtaquesRecargados.estado match {
      case _: EstadoNormal if ((pokemonConAtaquesRecargados.energiaMaxima * 0.5) > pokemonConAtaquesRecargados.energia) => pokemonConAtaquesRecargados.cambiarEstado(new Dormido)
      case _ => pokemonConAtaquesRecargados
    }
 
  }
}