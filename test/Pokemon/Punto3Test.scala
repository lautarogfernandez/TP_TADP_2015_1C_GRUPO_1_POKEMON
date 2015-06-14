package Pokemon

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore
import org.junit.Before
import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.condicion_evolucion._
import tadp.grupo1.pokemon.estado._
import tadp.grupo1.pokemon.genero._
import tadp.grupo1.pokemon.tipo._
import rutina.Rutina
import Actividad._


/**
 * @author Alejandro
 */
class Punto3Test {
  
  val fuego = new Fuego
  
  val charizard = new Especie(350,100,new Fuego,10,10,10,10,new Volador)//Some(new Dragon))
  val charmeleon = new Especie(350,70,new Fuego,7,7,7,7,new Dragon,new SubirNivel(32),charizard)
  val charmander = new Especie(350,22,new Fuego,4,4,4,4,new Dragon,new SubirNivel(16),charmeleon)
 
  var carlitos : Pokemon=null
 
  val comeZinc = new ComerZinc()
  val comeCalcio =new ComerCalcio()
  val descansa = new Descansar()
  val teCambioPorOtro = new FingirIntercambio()  
  
 // val listaActividadesRutina1 = new List[Actividad]
  val lista
 // val rutina1 = new Rutina();
  
 // @Before
  def setUp() = {
    var ataques:List[AtaquePokemon]= List()
    carlitos= new Pokemon(charmander,new Macho,10,12,12,10,10,ataques)
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
}