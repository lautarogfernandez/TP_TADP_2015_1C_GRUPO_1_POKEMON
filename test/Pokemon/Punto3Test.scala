package Pokemon

import org.junit.Assert._
import org.junit.Test
import org.junit.Ignore
import org.junit.Before
import tadp.grupo1.pokemon.AtaqueGenerico
import tadp.grupo1.pokemon.Especie
import tadp.grupo1.pokemon.Piedra
import tadp.grupo1.pokemon._
import tadp.grupo1.pokemon.condicion_evolucion._
import tadp.grupo1.pokemon.tipo._
import tadp.grupo1.pokemon.estado._
import tadp.grupo1.pokemon.genero._
import Actividad._
import tadp.grupo1.rutina.Rutina
import scala.util.{Try, Failure, Success}

/**
 * @author Alejandro
 */
class Punto3Test {
   
  val piedraLunar=new Piedra(Lunar)
  val piedraAgua=new Piedra(Agua)
  
  val charizard=new Especie(350,100,Fuego,10,10,10,10,Volador)
  val charmeleon=new Especie(350,70,Fuego,7,7,7,7,Dragon,new SubirNivel(32),Some(charizard))
  val charmander=new Especie(350,22,Fuego,4,4,4,4,Dragon,new SubirNivel(16),Some(charmeleon))
  val raticate=new Especie(350,100,Normal,10,10,10,10,NoTiene)
  val rattata=new Especie(100,22,Normal,4,4,4,4,NoTiene,new SubirNivel(20),Some(raticate))
  val jynx=new Especie(100,22,Hielo,4,4,4,4,Psiquico)
  val machamp=new Especie(100,70,Lucha,4,4,4,4,NoTiene)
  val machoke=new Especie(100,70,Lucha,4,4,4,4,NoTiene,new Intercambiar,Some(machamp)) 
  val machop=new Especie(100,70,Lucha,4,4,4,4,NoTiene,new SubirNivel(28),Some(machoke)) 
  val gengar=new Especie(100,50,Fantasma,4,4,4,4,Veneno)
  val seeking=new Especie(1000,50,Agua,4,4,4,4,NoTiene)
  val nidoqueen=new Especie(100,50,Veneno,7,7,7,7,NoTiene)
  val nidorina=new Especie(100,50,Veneno,4,4,4,4,NoTiene,new UsarPiedraLunar,Some(nidoqueen))
  val starmie=new Especie(100,50,Agua,7,7,7,7,Psiquico)
  val staryu=new Especie(100,50,Agua,4,4,4,4,NoTiene,new UsarPiedraParaEvolucion, Some(starmie))
  
  val mordida=new AtaqueGenerico(Normal,30)
  val hipnosis =new AtaqueGenerico(Psiquico,20, { pokemonAtacante => pokemonAtacante.cambiarEstado(new Dormido)})
  val dragonTail =new AtaqueGenerico(Dragon,10)
  val maldicion =new AtaqueGenerico(Fantasma,25)
  val corte = new AtaqueGenerico(Normal,30)
  val reposo = new AtaqueGenerico(Normal,30, { pokemonAtacante => pokemonAtacante.copy(energia = pokemonAtacante.energiaMaxima).cambiarEstado(new Dormido) })
  val enfocarse = new AtaqueGenerico(Normal,30, { pokemonAtacante => pokemonAtacante.subirAtributo(velocidadASubir = 1) } )
  val endurucerse = new AtaqueGenerico(Normal,30, { pokemonAtacante => pokemonAtacante.subirAtributo(energiaASubir = 5).cambiarEstado(new Paralizado) })
  
  var sinAtaques:List[AtaquePokemon]= List()
  
  val morder=new RealizarAtaque(mordida)
  val hipnotizar=new RealizarAtaque(hipnosis)
  val colaDragonea=new RealizarAtaque(dragonTail)  
  val hacerPesas=new LevantarPesas (5)
  val nada=new Nadar(5)
  val nadaMas=new Nadar(125)
  val aprendeMordida = new AprenderAtaque(mordida)
  val aprendeMaldicion=new AprenderAtaque(maldicion)
  val aprendeHipnosis = new AprenderAtaque(hipnosis)
  val aprendeCorte=new AprenderAtaque(corte)
  val aprendeReposo = new AprenderAtaque(reposo)
  val aprendeDragonTail=new AprenderAtaque(dragonTail)
  val aprendeEndurecerse = new AprenderAtaque(endurucerse)
  val aprendeEnfocarse = new AprenderAtaque(enfocarse)
  val usaPiedraLunar=new Usar(piedraLunar)
  val usaPiedraAgua=new Usar(piedraAgua)
  val usarPocion=new Usar(Pocion)
  val usarAntidoto=new Usar(Antidoto)
  val usarEther=new Usar(Ether)
  val comeHierro=new Usar(Hierro)
  val comeZinc=new Usar(Zinc)
  val comeCalcio=new Usar(Calcio)
  val descansa=Descansar
  val teCambioPorOtro=FingirIntercambio  
  
  var carlitos:Pokemon=null
  var carlita:Pokemon=null
  var pequeñoDragon:Pokemon=null
  var phantom:Pokemon=null
  var luchador:Pokemon=null
  var unPokemonDeFuego:Pokemon=null
  var unPokemonDeAgua:Pokemon=null
  var unPokemonQueEvolucionaConPiedraLunar:Pokemon=null
  var unPokemonQueEvolucionaConPiedraAgua:Pokemon=null
  var peleador:Pokemon=null  

  @Before
  def setUp(){    
    carlitos = new Pokemon(rattata, Macho,10,12,12,10,10,sinAtaques)
    carlitos = aprendeMordida(carlitos)
    carlita = new Pokemon(jynx, Hembra,11,12,12,10,10,sinAtaques)
    carlita = aprendeHipnosis(carlita)
    pequeñoDragon=new Pokemon(charmander, Macho,10,12,12,10,10,sinAtaques)
    pequeñoDragon = aprendeDragonTail(pequeñoDragon)
    phantom=new Pokemon(gengar, Macho,10,12,12,10,10,sinAtaques)
    luchador=new Pokemon(machop, Macho,10,12,12,10,10,sinAtaques)
    unPokemonDeFuego=new Pokemon(charmander, Macho,10,12,12,10,10,sinAtaques)
    unPokemonDeAgua=new Pokemon(seeking, Macho,10,1000,1000,10,10,sinAtaques)
    unPokemonDeAgua = aprendeMordida(unPokemonDeAgua)
    unPokemonQueEvolucionaConPiedraLunar= Pokemon(nidorina, Hembra,10,20,20,10,10,sinAtaques)
    unPokemonQueEvolucionaConPiedraAgua= Pokemon(staryu, Hembra,10,20,20,10,10,sinAtaques)
    peleador=new Pokemon(machoke, Macho,10,20,20,10,10,sinAtaques)
  }
  
  def assertEstado(estado1:Estado, estado2:Estado)={
    assertEquals(true,estado1.getClass==estado2.getClass)
  }

  @Test
  def `pokemon hace rutina con una sola actividad y se devuelve un nuevo pokemon que queda como deberia` : Unit = {
    val rutinaQuePuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(morder))
    val carlitosLuegoDeRutina = rutinaQuePuedeTerminarCarlitos.realizarRutina(carlitos)

    // Exp despues de la rutina
    val experienciaEsperada = carlitos.experiencia + BigInt(50)
    assertEquals(experienciaEsperada, carlitosLuegoDeRutina.get.experiencia)

    // Morder
    assertEquals(29, carlitosLuegoDeRutina.get.dameAtaque(mordida).puntosAtaque)
  }

  @Test
  def `pokemon hace rutina de varias actividdes que termina bien y se devuelve un pokemon que queda como deberia` : Unit = {
    
    val rutinaQuePuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(aprendeCorte, comeZinc, morder, comeHierro, hacerPesas))
    val carlitosLuegoDeRutina = rutinaQuePuedeTerminarCarlitos.realizarRutina(carlitos) 
    
    // Exp despues de la rutina
    val experienciaEsperada = carlitos.experiencia + BigInt(50) + BigInt(5)
    assertEquals(experienciaEsperada, carlitosLuegoDeRutina.get.experiencia)

    // Comer Zinc y AprenderCorte
    val valorEsperadoMordida = carlitos.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon + 2
    assertEquals(valorEsperadoMordida, carlitosLuegoDeRutina.get.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon)
    assertEquals(32, carlitosLuegoDeRutina.get.dameAtaque(corte).puntosAtaqueMaximoDelPokemon)   
    
    // Morder
    assertEquals(29, carlitosLuegoDeRutina.get.dameAtaque(mordida).puntosAtaque)
    
    // comeHierro
    assertEquals(carlitos.fuerza + 5, carlitosLuegoDeRutina.get.fuerza)
    
    // HacerPesas
    assertEstado(new EstadoNormal, carlitos.estado)
  }

  @Test(expected = classOf[NoTieneElAtaque])
  def `pokemon hace rutina en la que hay una sola actividad que no puede terminar y obtengo el try de la exception` : Unit = {
    val rutinaQueNoPuedeTerminarPhantom = Rutina("rutinaQueNoPuedeTerminarPhantom", List(aprendeCorte, morder, comeHierro))
    val carlitosLuegoDeRutina = rutinaQueNoPuedeTerminarPhantom.realizarRutina(phantom)

    assertTryConFailure(carlitosLuegoDeRutina, "NoTieneElAtaque")
  }

  def assertTryConSuccess(carlitosLuegoDeRutina :  Try[Pokemon]) = {
    carlitosLuegoDeRutina match {
      case Success(pokemon) => pokemon
      case Failure(exception) => fail("Se esperaba un pokemon pero se recibio esta excepcion " + exception.getMessage)
    }
  }

  def assertTryConFailure(carlitosLuegoDeRutina: Try[Pokemon], nombreDeLaException : String) : Unit = {
    carlitosLuegoDeRutina match {
      case Success(_) => fail("Deberia haber fallado con la Exception " + nombreDeLaException)
      case Failure(exeption) => throw exeption
    }
  }

  @Test(expected = classOf[NoPuedeLevantarPesas])
  def `pokemon hace rutina en la que hay varias actividades que no puede terminar y obtengo la exception de la primera actividad que no pudo terminar` : Unit = {
    val rutinaQueNoPuedeTerminarPhantom = Rutina("rutinaQueNoPuedeTerminarPhantom", List(aprendeCorte, hacerPesas, comeZinc, morder, comeHierro))
    val carlitosLuegoDeRutina = rutinaQueNoPuedeTerminarPhantom.realizarRutina(phantom)

    assertTryConFailure(carlitosLuegoDeRutina, "NoPuedeLevantarPesas")
  }
  
  @Test
  def `pokemon hace rutina vacia devuelve el mismo pokemon` : Unit = {
    val rutinaVacia= Rutina("rutinaVacia", List())
    val carlitosDespeusDeRutina = rutinaVacia.realizarRutina(carlitos)

    carlitosDespeusDeRutina match {
      case Success(pokemonDespuesRutina) => assertEquals(carlitos, pokemonDespuesRutina)
      case Failure(_) => fail("Deberia haber devuelto un Success con el mismo pokemon original")
    }
  }

}