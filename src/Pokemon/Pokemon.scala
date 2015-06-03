package Pokemon

import Pokemon.Estado._
import Pokemon.Genero._
import Pokemon.CondicionEvolucion._

/**
 * @author usuario
 */
class Pokemon(var especie: Especie, val genero: Genero, var energiaMaxima: Int, var peso: Int, var fuerza: Int, var velocidad: Int) {//creo que se debe controlar que cumpla el peso de la especie  
  var nivel=1
  var experiencia=0   
  var energia=energiaMaxima
  var estado= new Normal
  
      
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
    especie.condicionEvolucion.nivelParaEvolucionar(this)
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