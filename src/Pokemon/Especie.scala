package Pokemon

/**
 * @author usuario
 */

import Pokemon.CondicionEvolucion._
import Pokemon.Tipo._

class Especie(val resistenciaEvolutiva: Int, val pesoMaximo: Int, val tipoPrincipal: Tipo, val tipoSecundario: Tipo, val evolucion: List[Especie], val incrementoPeso:Int, val incrementoEnergiaMaxima:Int, val incrementoFuerza:Int, val incrementoVelocidad:Int, val condicionEvolucion: Condicion, val evoluciones: List[Especie]) {//lo de evolucion puede cambiar porque algunas no tienen y otras tienen varias
  
  def energiaNecesariaParaNivel(nivel:Int):Int={
    nivel match{
      case 1 => 0
      case _ => 2*energiaNecesariaParaNivel(nivel-1)+resistenciaEvolutiva
    }
  }
  
  def subirNivel(pokemon: Pokemon):Unit={
    if (pokemon.experiencia>=energiaNecesariaParaNivel(pokemon.nivel))
      pokemon.subiNivel(incrementoPeso, incrementoEnergiaMaxima, incrementoFuerza, incrementoVelocidad)
  }
  
  def evolucion():Especie={
    evoluciones match{
      case Nil => this
      case especie::_ =>especie
    }
  }
  
  def evolucionPara(tipo:Tipo):Especie={
    if(evoluciones.exists { especie => especie.tipoPrincipal==tipo })
      evoluciones.filter { unaEspecie => unaEspecie.tipoPrincipal==tipo }.head
    else
      this
  }
  
  def evolucion(piedra:Piedra):Especie={
    piedra.tipo match{
      case lunar:Lunar => this//aca va algo pero no se que va todavia
      case tipo:Tipo => evolucionPara(tipo)
    }
  }
  
}