package Actividad

import tadp.grupo1.pokemon.tipo.Agua
import tadp.grupo1.pokemon.NoPuedeRealizarActividad
import tadp.grupo1.pokemon.Pokemon
import tadp.grupo1.pokemon.estado.KO

/**
 * @author usuario
 */
case class Nadar(val minutos: Int) extends Actividad {
  
  override def apply(p: Pokemon) = {
    (this, p.especie.tipoPrincipal, p.especie.tipoSecundario) match {
      case (Nadar(minutos),_,_) if (p.energia < minutos) => throw new NoPuedeRealizarActividad
      case (Nadar(minutos),Agua,ts) =>
          p.ganarExperiencia(200 * minutos)
            .ganarVelocidad(minutos / 60)
            .perderEnergia(minutos)
      case (_, tp, ts) if ((tp.pierdeContra(Agua)) || ts.pierdeContra(Agua)) => p.copy(estado = new KO)
      case _ =>
          p.ganarExperiencia(200 * minutos).perderEnergia(minutos)
    }
  }
}