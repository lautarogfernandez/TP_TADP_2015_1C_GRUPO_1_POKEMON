package tadp.grupo1.pokemon.condicion_evolucion

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
class SubirNivel (val nivelNecesarioParaEvolucionar:Int) extends Condicion{
  
  override def nivelParaEvolucionar(pokemon:Pokemon) : Pokemon = {
    var nuevoPokemon = pokemon
    
    if (pokemon.nivel==nivelNecesarioParaEvolucionar)
      nuevoPokemon = pokemon.evolucionar()
    
    nuevoPokemon
  }
  
}