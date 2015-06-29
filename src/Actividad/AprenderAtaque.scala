package Actividad

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.tipo._
import tadp.grupo1.pokemon.estado._
/**
 * @author usuario
 */
case class AprenderAtaque (val ataque:AtaqueGenerico) extends Actividad{
  
  override def applyActividad(pokemon: Pokemon) = {
    (ataque,ataque.tipo,pokemon,pokemon.especie) match {
      case (attack,tipoAtaque,poke,especie) if (especie.esAfin(tipoAtaque)) =>  aprendeAtaque(poke, attack)
      case (_,_,poke,_) => poke.cambiarEstado(new KO)
    }
  }   
  
  def aprendeAtaque(poke : Pokemon, ataque: AtaqueGenerico): Pokemon = {
    val nuevaListAtaques = poke.ataques.::(new AtaquePokemon(ataque, ataque.puntosAtaqueMaximo, ataque.puntosAtaqueMaximo))
    poke.copy(ataques = nuevaListAtaques)
  }
}