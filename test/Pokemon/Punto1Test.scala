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
  
  val charizard=new Especie(550,100, Fuego,10,10,10,10, Volador)
  val charmeleon=new Especie(450,70, Fuego,7,7,7,7, Dragon,new SubirNivel(32),Some(charizard))
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
  def `pokemon sube de nivel sin evolucionar caso border inferior` = {
    carlitos = carlitos.ganarExperiencia(charmander.experienciaNecesariaParaNivel(16) - 1)
    assertEquals(15,carlitos.nivel)
    assertEquals(charmander,carlitos.especie)
  }
  
  @Test
  def `ver si experienciaNecesariaParaNivel funca bien` = {
    
    val expNecesariaN1 : BigInt = 0
    val expNecesariaN2 = 2 * expNecesariaN1 + charmander.resistenciaEvolutiva
    val expNecesariaN3 = 2 * expNecesariaN2 + charmander.resistenciaEvolutiva
    val expNecesariaN4 = 2 * expNecesariaN3 + charmander.resistenciaEvolutiva
    
    assertEquals(expNecesariaN1, charmander.experienciaNecesariaParaNivel(1))
    assertEquals(expNecesariaN2, charmander.experienciaNecesariaParaNivel(2))
    assertEquals(expNecesariaN3, charmander.experienciaNecesariaParaNivel(3))
    assertEquals(expNecesariaN4, charmander.experienciaNecesariaParaNivel(4))
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
    assertEquals(16, carlitos.nivel)
    assertEquals(charmeleon,carlitos.especie)
   
    carlitos = carlitos.ganarExperiencia(charmeleon.experienciaNecesariaParaNivel(32) - charmeleon.experienciaNecesariaParaNivel(16))
    assertEquals(32, carlitos.nivel)
    assertEquals(charizard,carlitos.especie)
    
    carlitos = carlitos.ganarExperiencia(charmeleon.experienciaNecesariaParaNivel(80) - charmeleon.experienciaNecesariaParaNivel(32))
    assertEquals(80, carlitos.nivel)
    assertEquals(charizard,carlitos.especie)
  }
  
  @Test
  def `pokemon sube de nivel de nivel hasta un maximo de 100` = {
    carlitos = carlitos.ganarExperiencia(11468450)
    assertEquals(charmeleon,carlitos.especie)

    carlitos = carlitos.ganarExperiencia(charmeleon.experienciaNecesariaParaNivel(32) - charmeleon.experienciaNecesariaParaNivel(16))
    assertEquals(charizard,carlitos.especie)

    carlitos = carlitos.ganarExperiencia(charmeleon.experienciaNecesariaParaNivel(101))
    assertEquals(charizard,carlitos.especie)
    assertEquals(100,carlitos.nivel)    
  }
  
}