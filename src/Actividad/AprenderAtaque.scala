package Actividad

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.tipo._
import tadp.grupo1.pokemon.estado._
/**
 * @author usuario
 */
class AprenderAtaque (val ataque:AtaqueGenerico) extends Actividad{
  
  override def apply(pokemon: Pokemon) = {
    (ataque,ataque.tipo,pokemon,pokemon.especie) match {
      case (attack,tipoAtaque,poke,especie) if (especie.esAfin(tipoAtaque)) =>  poke.aprendeAtaque(attack)
      case (_,_,poke,_) => poke.cambiarEstado(new KO)
    }
  }   
  
}