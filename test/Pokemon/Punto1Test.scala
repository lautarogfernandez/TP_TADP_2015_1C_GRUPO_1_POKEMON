package Pokemon

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore

import Pokemon._
import Pokemon.CondicionEvolucion._
import Pokemon.Estado._
import Pokemon.Genero._
import Pokemon.Tipo._

/**
 * @author usuario
 */
class Punto1Test {
  
  var charizard=new Especie(350,100,new Fuego,new Dragon,List(),10,10,10,10,new SubirNivel(180))
  var charmeleon=new Especie(350,70,new Fuego,new Dragon,List(charizard),7,7,7,7,new SubirNivel(32))
  var charmander=new Especie(350,22,new Fuego,new Dragon,List(charmeleon),4,4,4,4,new SubirNivel(16))
  var charmanderDeAsh=new Pokemon(charmander,new Macho,10,12,10,10)
  
  @Test
  def `pokemon sube de nivel sin evolucionar` = {
    val nivel=charmanderDeAsh.nivel
    charmanderDeAsh.ganarExperiencia(200)
    assertEquals(nivel,charmanderDeAsh.nivel)
  }
  
}