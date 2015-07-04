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
import scala.util.{ Try, Failure, Success }

/**
 * @author Alejandro
 */
trait TestsPokemon {

  val piedraLunar = new Piedra(Lunar)
  val piedraAgua = new Piedra(Agua)

  val charizard = new Especie(350, 100, Fuego, 10, 10, 10, 10, Volador)
  val charmeleon = new Especie(350, 70, Fuego, 7, 7, 7, 7, Dragon, new SubirNivel(32), Some(charizard))
  val charmander = new Especie(350, 22, Fuego, 4, 4, 4, 4, Dragon, new SubirNivel(16), Some(charmeleon))
  val raticate = new Especie(350, 100, Normal, 10, 10, 10, 10, NoTiene)
  val rattata = new Especie(100, 22, Normal, 4, 4, 4, 4, NoTiene, new SubirNivel(20), Some(raticate))
  val jynx = new Especie(100, 22, Hielo, 4, 4, 4, 4, Psiquico)
  val machamp = new Especie(100, 70, Lucha, 4, 4, 4, 4, NoTiene)
  val machoke = new Especie(100, 70, Lucha, 4, 4, 4, 4, NoTiene, new Intercambiar, Some(machamp))
  val machop = new Especie(100, 70, Lucha, 4, 4, 4, 4, NoTiene, new SubirNivel(28), Some(machoke))
  val gengar = new Especie(100, 50, Fantasma, 4, 4, 4, 4, Veneno)
  val seeking = new Especie(1000, 50, Agua, 4, 4, 4, 4, NoTiene)
  val nidoqueen = new Especie(100, 50, Veneno, 7, 7, 7, 7, NoTiene)
  val nidorina = new Especie(100, 50, Veneno, 4, 4, 4, 4, NoTiene, new UsarPiedraLunar, Some(nidoqueen))
  val starmie = new Especie(100, 50, Agua, 7, 7, 7, 7, Psiquico)
  val staryu = new Especie(100, 50, Agua, 4, 4, 4, 4, NoTiene, new UsarPiedraParaEvolucion, Some(starmie))

  val mordida = new AtaqueGenerico(Normal, 30)
  val hipnosis = new AtaqueGenerico(Psiquico, 20, { pokemonAtacante => pokemonAtacante.cambiarEstado(new Dormido) })
  val dragonTail = new AtaqueGenerico(Dragon, 10)
  val maldicion = new AtaqueGenerico(Fantasma, 25)
  val corte = new AtaqueGenerico(Normal, 30)
  val reposo = new AtaqueGenerico(Normal, 30, { pokemonAtacante => pokemonAtacante.copy(energia = pokemonAtacante.energiaMaxima).cambiarEstado(new Dormido) })
  val enfocarse = new AtaqueGenerico(Normal, 30, { pokemonAtacante => pokemonAtacante.subirAtributo(velocidadASubir = 1) })
  val endurucerse = new AtaqueGenerico(Normal, 30, { pokemonAtacante => pokemonAtacante.subirAtributo(energiaASubir = 5).cambiarEstado(new Paralizado) })

  var sinAtaques: List[AtaquePokemon] = List()

  val morder = new RealizarAtaque(mordida)
  val hipnotizar = new RealizarAtaque(hipnosis)
  val colaDragonea = new RealizarAtaque(dragonTail)
  val endurecer = RealizarAtaque(endurucerse)
  val hacerPesas = new LevantarPesas(5)
  val nada = new Nadar(5)
  val nadaMas = new Nadar(125)
  val aprendeMordida = new AprenderAtaque(mordida)
  val aprendeMaldicion = new AprenderAtaque(maldicion)
  val aprendeHipnosis = new AprenderAtaque(hipnosis)
  val aprendeCorte = new AprenderAtaque(corte)
  val aprendeReposo = new AprenderAtaque(reposo)
  val aprendeDragonTail = new AprenderAtaque(dragonTail)
  val aprendeEndurecerse = new AprenderAtaque(endurucerse)
  val aprendeEnfocarse = new AprenderAtaque(enfocarse)
  val usaPiedraLunar = new Usar(piedraLunar)
  val usaPiedraAgua = new Usar(piedraAgua)
  val usarPocion = new Usar(Pocion)
  val usarAntidoto = new Usar(Antidoto)
  val usarEther = new Usar(Ether)
  val comeHierro = new Usar(Hierro)
  val comeZinc = new Usar(Zinc)
  val comeCalcio = new Usar(Calcio)
  val descansa = Descansar
  val teCambioPorOtro = FingirIntercambio

  var carlitos: Pokemon = null
  var carlita: Pokemon = null
  var pequeñoDragon: Pokemon = null
  var phantom: Pokemon = null
  var luchador: Pokemon = null
  var unPokemonDeFuego: Pokemon = null
  var unPokemonDeAgua: Pokemon = null
  var unPokemonQueEvolucionaConPiedraLunar: Pokemon = null
  var unPokemonQueEvolucionaConPiedraAgua: Pokemon = null
  var peleador: Pokemon = null

  @Before
  def setUp() {
    carlitos = new Pokemon(rattata, Macho, 10, 12, 12, 10, 10, sinAtaques)
    carlitos = aprendeMordida(carlitos)
    carlita = new Pokemon(jynx, Hembra, 11, 12, 12, 10, 10, sinAtaques)
    carlita = aprendeHipnosis(carlita)
    pequeñoDragon = new Pokemon(charmander, Macho, 10, 12, 12, 10, 10, sinAtaques)
    pequeñoDragon = aprendeDragonTail(pequeñoDragon)
    phantom = new Pokemon(gengar, Macho, 10, 12, 12, 10, 10, sinAtaques)
    luchador = new Pokemon(machop, Macho, 10, 12, 12, 10, 10, sinAtaques)
    unPokemonDeFuego = new Pokemon(charmander, Macho, 10, 12, 12, 10, 10, sinAtaques)
    unPokemonDeAgua = new Pokemon(seeking, Macho, 10, 1000, 1000, 10, 10, sinAtaques)
    unPokemonDeAgua = aprendeMordida(unPokemonDeAgua)
    unPokemonQueEvolucionaConPiedraLunar = Pokemon(nidorina, Hembra, 10, 20, 20, 10, 10, sinAtaques)
    unPokemonQueEvolucionaConPiedraAgua = Pokemon(staryu, Hembra, 10, 20, 20, 10, 10, sinAtaques)
    peleador = new Pokemon(machoke, Macho, 10, 20, 20, 10, 10, sinAtaques)
  }

  def assertEstado(estado1: Estado, estado2: Estado) = {
    assertEquals(true, estado1.getClass == estado2.getClass)
  }

  // Me parece que queda mejor sin el Try para que se asemeje mas a un assert de JUnit 

  //  implicit class improveTryPokemon(carlitosLuegoDeRutina: Try[Pokemon]) {
  //
  //    def assertTryConSuccess = {
  //      carlitosLuegoDeRutina match {
  //        case Success(pokemon)   => pokemon
  //        case Failure(exception) => fail("Se esperaba un pokemon pero se recibio esta excepcion " + exception.getMessage)
  //      }
  //    }
  //
  //    def assertTryConFailure(nombreDeLaException: String): Unit = {
  //      carlitosLuegoDeRutina match {
  //        case Success(_)        => fail("Deberia haber fallado con la Exception " + nombreDeLaException)
  //        case Failure(exeption) => throw exeption
  //      }
  //    }
  //
  //  }

  def assertTryConSuccess(carlitosLuegoDeRutina: Try[Pokemon]) = {
    carlitosLuegoDeRutina match {
      case Success(pokemon)   => pokemon
      case Failure(exception) => fail("Se esperaba un pokemon pero se recibio esta excepcion " + exception.getMessage)
    }
  }

  def assertTryConFailure(carlitosLuegoDeRutina: Try[Pokemon], nombreDeLaException: String): Unit = {
    carlitosLuegoDeRutina match {
      case Success(_)        => fail("Deberia haber fallado con la Exception " + nombreDeLaException)
      case Failure(exeption) => throw exeption
    }
  }

  implicit class ImproveTry[T](unTryConAlgoAdentro: Try[T]) {
    def obtenerObjetoAdentroDelTry: T = {
      unTryConAlgoAdentro match {
        case Success(algo)      => algo
        case Failure(exception) => throw exception
      }
    }
  }

  //  def obtenerObjetoAdentroDelTry[T](implicit unTryConAlgoAdentro: Try[T]): T = {
  //    unTryConAlgoAdentro match {
  //      case Success(algo)      => algo
  //      case Failure(exception) => throw exception
  //    }
  //  }

}