package tadp.grupo1.pokemon

/**
 * @author usuario
 */

import tadp.grupo1.pokemon.condicion_evolucion._
import tadp.grupo1.pokemon.tipo._

case class NoHayEvolucion() {}

case class Especie(val resistenciaEvolutiva: Int, val pesoMaximo: Int, val tipoPrincipal: Tipo,
                   val incrementoPeso: Int, val incrementoEnergiaMaxima: Int,
                   val incrementoFuerza: Int, val incrementoVelocidad: Int,
                   val tipoSecundario: Tipo,
                   val condicionEvolucion: Condicion = new NoEvoluciona,
                   val evolucion: Option[Especie] = None) {

  def experienciaNecesariaParaNivel(nivel: Int): Long = {
    nivel match {
      case 1 => 0
      case _ => 2 * experienciaNecesariaParaNivel(nivel - 1) + resistenciaEvolutiva
    }
  }

  def queNivelSoy(experiencia: Long, nivel: Int = 1): Int = {
    val experienciaNecesaria = experienciaNecesariaParaNivel(nivel + 1)
    nivel match {
      case 100 => 100
      case nivel if (experiencia < experienciaNecesariaParaNivel(nivel + 1)) => nivel
      case nivel if (experiencia >= experienciaNecesariaParaNivel(nivel + 1)) => queNivelSoy(experiencia, nivel + 1)
    }

  }

//  def subirNivel(pokemon: Pokemon): Pokemon = {
//    var nuevoPokemon = pokemon
//
//    if ((pokemon.experiencia >= experienciaNecesariaParaNivel(pokemon.nivel + 1)) && (pokemon.nivel != 100))
//      nuevoPokemon = pokemon.subiNivel(incrementoPeso, incrementoEnergiaMaxima, incrementoFuerza, incrementoVelocidad)
//
//    nuevoPokemon
//  }

  def evolucioname(pokemon: Pokemon): Pokemon = { //evolucionaSiPuedo
    condicionEvolucion.nivelParaEvolucionar(pokemon)
  }

  def esDelTipoPrincipal(tipo: Tipo): Boolean = {
    tipoPrincipal == tipo
  }

  def esDelTipoSecundario(tipo: Tipo): Boolean = {
    tipoSecundario == tipo
  }

  def esAfin(tipo: Tipo): Boolean = { //TODO: hacer bien esto del pattern matching
    tipo match {
      case Normal                         => true
      case _ if esDelTipoPrincipal(tipo)  => true
      case _ if esDelTipoSecundario(tipo) => true
      case _                              => false
    }
  }

  def sufriIntercambio(pokemon: Pokemon): Pokemon = {
    condicionEvolucion.intercambio(pokemon)
  }
}
