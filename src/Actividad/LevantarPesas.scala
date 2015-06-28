package Actividad

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.tipo._
import tadp.grupo1.pokemon.estado._

/**
 * @author usuario
 */
case class LevantarPesas (val cantidadKilos: Int) extends Actividad {
  
  override def applyActividad(pokemon: Pokemon) = {
    (this, pokemon,pokemon.estado,pokemon.especie.tipoPrincipal, pokemon.especie.tipoSecundario,pokemon.fuerza) match {
      case (_,_,_,Fantasma,_,_) => throw new NoPuedeLevantarPesas
      case (_,poke,_:Paralizado,_,_,_) => poke.cambiarEstado(new KO)
      case (LevantarPesas(cantidadKilos),poke,_,_,_,fuerza) if (cantidadKilos>=fuerza*10) => poke.cambiarEstado(new Paralizado)
      case (LevantarPesas(cantidadKilos),poke,_,Lucha,_,_) => poke.ganarExperiencia(2*cantidadKilos)
      case (LevantarPesas(cantidadKilos),poke,_,_,Lucha,_) => poke.ganarExperiencia(2*cantidadKilos)
      case (LevantarPesas(cantidadKilos),poke,_,_,_,_) => poke.ganarExperiencia(cantidadKilos)
    }
  }    
  
}