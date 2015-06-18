package Actividad

import tadp.grupo1.pokemon._
/**
 * @author usuario
 */
trait Consumible{}

object Pocion extends Consumible{}
object Antidoto extends Consumible{}
object Ether extends Consumible{}
object Hierro extends Consumible{}
object Calcio extends Consumible{}
object Zinc extends Consumible{}

class Usar(consumible:Consumible) extends Actividad{
  
  override def apply(pokemon: Pokemon) = {
    (consumible,pokemon) match {
      case (piedra:Piedra,poke) =>  poke.usaPiedra(piedra)
      case (Pocion,poke) =>  poke.usaPocion()
      case (Antidoto,poke) =>  poke.usaAntidoto()
      case (Ether,poke) =>  poke.usaEther()
      case (Hierro,poke) =>  poke.comeHierro()
      case (Calcio,poke) =>  poke.comeCalcio()
      case (Zinc,poke) =>  poke.comeZinc()
    }
  }  
  
}