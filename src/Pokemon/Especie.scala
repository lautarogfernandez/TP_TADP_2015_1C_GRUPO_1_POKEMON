package Pokemon

/**
 * @author usuario
 */

import Pokemon.CondicionEvolucion.Condicion

class Especie(val resistenciaEvolutiva: Int, val pesoMaximo: Int, val tipoPrincipal: Tipo, val tipoSecundario: Tipo, val evolucion: Especie, val incrementoPeso:Int, val incrementoEnergiaMaxima:Int, val incrementoFuerza:Int, val incrementoVelocidad:Int, val condicion: Condicion) {//lo de evolucion puede cambiar porque algunas no tienen y otras tienen varias
  
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
  
  def evolucionar(pokemon: Pokemon):Unit={
    
  }
  
}