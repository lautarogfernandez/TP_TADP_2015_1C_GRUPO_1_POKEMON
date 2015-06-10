package Pokemon

/**
 * @author usuario
 */

import Pokemon.CondicionEvolucion._
import Pokemon.Tipo._

case class Especie(val resistenciaEvolutiva: Int, val pesoMaximo: Int, val tipoPrincipal: Tipo,
    val incrementoPeso:Int, val incrementoEnergiaMaxima:Int,
    val incrementoFuerza:Int, val incrementoVelocidad:Int,
    val tipoSecundario: Tipo,//Option[Tipo] = None,
    val condicionEvolucion: Condicion=new NoEvoluciona,//Option[Condicion] = None,
    val evolucion: Especie=null//val evolucion: Option[Especie]=None
     
    ) {//lo de evolucion puede cambiar porque algunas no tienen y otras tienen varias
  
  def experienciaNecesariaParaNivel(nivel:Int):Int={
    nivel match{
      case 1 => 0      
      case _ => 2*experienciaNecesariaParaNivel(nivel-1)+resistenciaEvolutiva
    }
  }
  
  def subirNivel(pokemon: Pokemon):Unit={
    if ((pokemon.experiencia >= experienciaNecesariaParaNivel(pokemon.nivel+1)) && (pokemon.nivel != 100))
      pokemon.subiNivel(incrementoPeso, incrementoEnergiaMaxima, incrementoFuerza, incrementoVelocidad)      
  }
  
  def evolucioname(pokemon:Pokemon)={//evolucionaSiPuedo
    condicionEvolucion.nivelParaEvolucionar(pokemon)
  }
  
  def esDelTipoPrincipal(tipo:Tipo):Boolean={
    tipoPrincipal==tipo
  }
  
  def esDelTipoSecundario(tipo:Tipo):Boolean={
    tipoSecundario==tipo
  }
  
  def esAfin(tipo:Tipo):Boolean={
    tipo match{
      case _:Normal => true
      case _ if esDelTipoPrincipal(tipo) => true
      case _ if esDelTipoSecundario(tipo) => true
      case _ => false
    }
  }  
  
}