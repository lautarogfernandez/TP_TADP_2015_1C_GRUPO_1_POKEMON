package Pokemon

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore
import org.junit.Before

import tadp.grupo1.pokemon.Especie;
import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.condicion_evolucion._
import tadp.grupo1.pokemon.estado._
import tadp.grupo1.pokemon.genero._
import tadp.grupo1.pokemon.tipo._

/**
 * @author usuario
 */
class Punto1Test {
  
  val charizard=new Especie(350,100, Fuego,10,10,10,10, Volador)
  val charmeleon=new Especie(350,70, Fuego,7,7,7,7, Dragon,new SubirNivel(32),Some(charizard))
  val charmander=new Especie(350,22, Fuego,4,4,4,4, Dragon,new SubirNivel(16),Some(charmeleon))
  
  var carlitos:Pokemon=null
  
  @Before
  def setUp(){
    var ataques:List[AtaquePokemon]= List()
    carlitos= new Pokemon(charmander,Macho,10,12,12,10,10,ataques)
  }
  
  @Test
  def `pokemon sube de nivel sin evolucionar` = {
    carlitos.ganarExperiencia(200)
    assertEquals(1,carlitos.nivel)
    assertEquals(12,carlitos.energiaMaxima)
    assertEquals(10,carlitos.peso)
    assertEquals(10,carlitos.fuerza)    
    assertEquals(10,carlitos.velocidad)
    assertEquals(charmander,carlitos.especie)
  }
  
  @Test
  def `pokemon sube de nivel y evoluciona` = {
    carlitos = carlitos.ganarExperiencia(11468450)
    assertEquals(16,carlitos.nivel)  
    assertEquals(117,carlitos.energiaMaxima)
    assertEquals(115,carlitos.peso)
    assertEquals(115,carlitos.fuerza)  
    assertEquals(115,carlitos.velocidad)
    assertEquals(charmeleon,carlitos.especie)
  }  
 
  @Test
  def `pokemon sube de nivel, evoluciona ` = {//y despues sube de nivel y evoluciona de nuevo, pero si sigue ganando experiencia, no evoluciona mas` = {
    carlitos = carlitos.ganarExperiencia(11468450)
    val nive1l = carlitos.nivel()
    assertEquals(charmeleon,carlitos.especie)
    carlitos = carlitos.ganarExperiencia(999999999)
    val nivel2 = carlitos.nivel()
    carlitos = carlitos.ganarExperiencia(999999999)
    val nivel3 = carlitos.nivel()
    carlitos = carlitos.ganarExperiencia(999999999)
    val nivel4 = carlitos.nivel()
    assertEquals(charizard,carlitos.especie)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    assertEquals(charizard,carlitos.especie)
  }
  
  @Test
  def `pokemon sube de nivel de nivel hasta un maximo de 100` = {
    carlitos = carlitos.ganarExperiencia(11468450)
    assertEquals(charmeleon,carlitos.especie)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    assertEquals(charizard,carlitos.especie)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    carlitos = carlitos.ganarExperiencia(999999999)
    assertEquals(charizard,carlitos.especie)
    assertEquals(100,carlitos.nivel)    
  }
  
}