package Pokemon.CondicionEvolucion

import Pokemon._

/**
 * @author usuario
 */
class SubirNivel (val nivelNecesarioParaEvolucionar:Int) extends Condicion{
  
  override def nivelParaEvolucionar(pokemon:Pokemon) {
    if (pokemon.nivel==nivelNecesarioParaEvolucionar)
      pokemon.evolucionar(pokemon.especie.evolucion())
  }
  
}