package tadp.grupo1.pokemon.condicion_evolucion

import Pokemon._
import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
class SubirNivel (val nivelNecesarioParaEvolucionar:Int) extends Condicion{
  
  override def nivelParaEvolucionar(pokemon:Pokemon) {
    if (pokemon.nivel==nivelNecesarioParaEvolucionar)
      pokemon.evolucionar()
  }
  
}