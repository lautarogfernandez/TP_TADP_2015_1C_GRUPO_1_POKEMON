package Actividad

import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.estado.EstadoNormal
import tadp.grupo1.pokemon.estado.Envenenado
import tadp.grupo1.pokemon.estado.KO
import tadp.grupo1.pokemon.tipo.Lunar
import tadp.grupo1.pokemon.condicion_evolucion.UsarPiedraLunar
import tadp.grupo1.pokemon.condicion_evolucion.UsarPiedraParaEvolucion
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
  
  override def applyActividad(pokemon: Pokemon) = {
    (consumible,pokemon) match {
      case (piedra:Piedra,poke) =>  usaPiedra(poke, piedra)
      case (Pocion,poke) =>  usaPocion(poke)
      case (Antidoto,poke) =>  usaAntidoto(poke)
      case (Ether,poke) =>  usaEther(poke)
      case (Hierro,poke) =>  comeHierro(poke)
      case (Calcio,poke) =>  comeCalcio(poke)
      case (Zinc,poke) =>  comeZinc(poke)
    }
  }  
  
    def usaPiedra(pokemon : Pokemon, piedra: Piedra): Pokemon = {
    pokemon.especie.condicionEvolucion match {
      case _: UsarPiedraLunar if piedra.tipo == Lunar                         => pokemon.evolucionar()
      case _: UsarPiedraParaEvolucion if piedra.tipo == pokemon.especie.tipoPrincipal => pokemon.evolucionar()
      case _ =>
        var pokemonCapazEnvenenado = pokemon
        if (piedra.tipo.leGanaA(pokemon.especie.tipoPrincipal) || piedra.tipo.leGanaA(pokemon.especie.tipoSecundario)) {
          pokemonCapazEnvenenado = pokemon.copy(estado = new Envenenado)
        }
        pokemonCapazEnvenenado
    }
  }

  def usaPocion(poke : Pokemon): Pokemon = {
    poke.copy(energia = math.min(poke.energia + 50, poke.energiaMaxima))
  }

  def usaAntidoto(poke : Pokemon): Pokemon = {
    poke.estado match {
      case _: Envenenado => poke.cambiarEstado(new EstadoNormal)
      case _             => poke
    }
  }

  def usaEther(poke : Pokemon): Pokemon = {
    poke.estado match {
      case _: KO => poke 
      case _     => poke.cambiarEstado(new EstadoNormal)
    }
  }

  def comeHierro(poke : Pokemon): Pokemon = {
    poke.subirAtributo(fuerzaASubir = 5)
  }

  def comeCalcio(poke : Pokemon): Pokemon = {
    poke.subirAtributo(velocidadASubir = 5)
  }

  def comeZinc(poke : Pokemon): Pokemon = {
    poke.copy(ataques = poke.ataques.map(ataque => ataque.aumentarPAMaximo(2))) // TODO es correcto 
  }
  
}