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
  
  val charizard=new Especie(350,100,new Fuego,new Dragon,List(),10,10,10,10,new SubirNivel(180))
  val charmeleon=new Especie(350,70,new Fuego,new Dragon,List(charizard),7,7,7,7,new SubirNivel(32))
  val charmander=new Especie(350,22,new Fuego,new Dragon,List(charmeleon),4,4,4,4,new SubirNivel(16))
  var carlitos=new Pokemon(charmander,new Macho,10,12,10,10)
  
  @Test
  def `pokemon sube de nivel sin evolucionar` = {
    carlitos.ganarExperiencia(200)
    assertEquals(1,carlitos.nivel)
    assertEquals(12,carlitos.energiaMaxima)
    assertEquals(10,carlitos.peso)
    assertEquals(10,carlitos.fuerza)    
    assertEquals(10,carlitos.velocidad)
  }
  
  @Test
  def `pokemon sube de nivel y evoluciona` = {
    carlitos.ganarExperiencia(11468450)
    assertEquals(16,carlitos.nivel)  
    assertEquals(72,carlitos.energiaMaxima)
    assertEquals(70,carlitos.peso)
    assertEquals(70,carlitos.fuerza)    
    assertEquals(70,carlitos.velocidad)
    assertEquals(charmeleon,carlitos.especie)
  }  
 
  @Test
  def `pokemon sube de nivel, evoluciona y despues sube de nivel y evoluciona de nuevo, pero si sigue ganando experiencia, no evoluciona mas` = {
    carlitos.ganarExperiencia(11468450)
    assertEquals(charmeleon,carlitos.especie)
    carlitos.ganarExperiencia(999999999)
    carlitos.ganarExperiencia(999999999)  
    assertEquals(charizard,carlitos.especie)
    carlitos.ganarExperiencia(999999999)
    carlitos.ganarExperiencia(999999999)
    carlitos.ganarExperiencia(999999999)
    carlitos.ganarExperiencia(999999999)
    carlitos.ganarExperiencia(999999999)
    assertEquals(charizard,carlitos.especie)
  }
  
}