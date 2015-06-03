package Pokemon

import Pokemon.Estado.Normal
import Pokemon.Genero.Genero

/**
 * @author usuario
 */
class Pokemon(var especie: Especie, val genero: Genero, var energia: Int, var peso: Int, var fuerza: Int, var velocidad: Int) {//creo que se debe controlar que cumpla el peso de la especie  
  var nivel=1
  var experiencia=0   
  var energiaMaxima= energia
  var estado= new Normal
  
  
  def evolucionar():Unit={
    
  }
    
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
    especie.evolucionar(this)
  }
  
}