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
 
  val mordida=new AtaqueGenerico(normal,30)
  val hipnosis =new AtaqueGenerico(psiquico,20, { pokemonAtacante => pokemonAtacante.cambiarEstado(new Dormido)})
  val dragonTail =new AtaqueGenerico(dragon,10)
  val maldicion =new AtaqueGenerico(fantasma,25)
  val corte = new AtaqueGenerico(normal,30)
  val reposo = new AtaqueGenerico(normal,30, { pokemonAtacante => pokemonAtacante.copy(energia = pokemonAtacante.energiaMaxima).cambiarEstado(new Dormido) })
  val enfocarse = new AtaqueGenerico(normal,30, { pokemonAtacante => pokemonAtacante.subirAtributo(velocidadASubir = 1) } )
  val endurucerse = new AtaqueGenerico(normal,30, { pokemonAtacante => pokemonAtacante.subirAtributo(energiaASubir = 5).cambiarEstado(new Paralizado) })
  
  val morder=RealizarAtaque(mordida)
  val hipnotizar=RealizarAtaque(hipnosis)
  val colaDragonea=RealizarAtaque(dragonTail)  
  val hacerPesas=new LevantarPesas (5)
  val nada=new Nadar(5)
  val nadaMas=new Nadar(125)
  val aprendeMaldicion = new AprenderAtaque(maldicion)
  val aprendeCorte=new AprenderAtaque(corte)
  val usaPiedraLunar=new Actividad.UsarPiedra(piedraLunar)
  val usaPiedraAgua=new Actividad.UsarPiedra(piedraAgua)
  val usarPocion=new UsarPocion()
  val usarAntidoto=new UsarAntidoto()
  val usarEther=new UsarEther()
  val comeHierro=new ComerHierro()
  val comeZinc=new ComerZinc()
  val comeCalcio=new ComerCalcio()
  val descansa=new Descansar()
  val teCambioPorOtro=new FingirIntercambio()  
  
  val rutinaQuePuedeTerminarCarlitos = new List[Actividad]
  val rutinaQuePuedeTerminarCarlitos = new List[Actividad]
  
  @Before
  def setUp(){
    carlitos = new Pokemon(rattata,new Macho,10,12,12,10,10,sinAtaques)
    carlitos = carlitos.aprendeAtaque(mordida)
 
    rutinaQuePuedeTerminarCarlitos.:+
  }
  
  @Test
  def `pokemon hace rutina que termina bien y se devuelve un pokemon que queda como deberia` = {
    rutinaQuePuedeTerminarCarlitos
    carlitos.ganarExperiencia(200)
  }
  
  @Test
  def `pokemon hace rutina que no puede terminar y puedo obtener la exception que me dice porque no la pudo terminar` = {
    carlitos.ganarExperiencia(200)
  }

}