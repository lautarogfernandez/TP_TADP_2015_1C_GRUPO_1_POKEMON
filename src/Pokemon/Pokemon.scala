package Pokemon

import Pokemon.Estado._
import Pokemon.Genero._
import Pokemon.CondicionEvolucion._

/**
 * @author usuario
 */
class Pokemon(var especie: Especie, val genero: Genero, var peso: Int, var energiaMaxima: Int, var fuerza: Int,
    var velocidad: Int,var ataques:List[Ataque]) {//creo que se debe controlar que cumpla el peso de la especie  
  var nivel=1
  var experiencia:Long=0   
  var energia=energiaMaxima
  var estado:Estado= new Normal  
  
      
  def ganarExperiencia(cantidad: Int): Unit ={
    experiencia+=cantidad
    especie.subirNivel(this)   
  }
  
  def subiNivel(incrementoPeso:Int, incrementoEnergiaMaxima:Int, incrementoFuerza:Int, incrementoVelocidad:Int):Unit={
    nivel+=1
    peso+=incrementoPeso
    energiaMaxima+=incrementoEnergiaMaxima
    fuerza+=incrementoFuerza
    velocidad+=incrementoVelocidad
    especie.evolucioname(this)
    especie.subirNivel(this)
  }
  
  def intercambiar():Unit={
    especie.condicionEvolucion.intercambiar(this)
  }
  
  def usarPiedra(piedra:Piedra):Unit={
    especie.condicionEvolucion.usarPiedra(this,piedra:Piedra)
  }
  
  def evolucionar(nuevaEspecie:Especie):Unit={
    especie=nuevaEspecie
  }
  
}