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
class Punto3Test extends TestsPokemon{
   
  @Test
  def `pokemon hace rutina con una sola actividad y se devuelve un nuevo pokemon que queda como deberia` : Unit = {
    val rutinaQuePuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(morder))
    val carlitosLuegoDeRutina = rutinaQuePuedeTerminarCarlitos.realizarRutina(carlitos)

    // Exp despues de la rutina
    val experienciaEsperada = carlitos.experiencia + BigInt(50)
    assertEquals(experienciaEsperada, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).experiencia)

    // Morder
    assertEquals(29, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).dameAtaque(mordida).puntosAtaque)
  }

  @Test
  def `pokemon hace rutina de varias actividdes que termina bien y se devuelve un pokemon que queda como deberia` : Unit = {
    
    val rutinaQuePuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(aprendeCorte, comeZinc, morder, comeHierro, hacerPesas))
    val carlitosLuegoDeRutina = rutinaQuePuedeTerminarCarlitos.realizarRutina(carlitos) 
    
    // Exp despues de la rutina
    val experienciaEsperada = carlitos.experiencia + BigInt(50) + BigInt(5)
    assertEquals(experienciaEsperada, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).experiencia)

    // Comer Zinc y AprenderCorte
    val valorEsperadoMordida = carlitos.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon + 2
    assertEquals(valorEsperadoMordida, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).dameAtaque(mordida).puntosAtaqueMaximoDelPokemon)
    assertEquals(32, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).dameAtaque(corte).puntosAtaqueMaximoDelPokemon)   
    
    // Morder
    assertEquals(29, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).dameAtaque(mordida).puntosAtaque)
    
    // comeHierro
    assertEquals(carlitos.fuerza + 5, obtenerObjetoAdentroDelTry(carlitosLuegoDeRutina).fuerza)
    
    // HacerPesas
    assertEstado(new EstadoNormal, carlitos.estado)
  }

  @Test(expected = classOf[NoTieneElAtaque])
  def `pokemon hace rutina en la que hay una sola actividad que no puede terminar y obtengo el try de la exception` : Unit = {
    val rutinaQueNoPuedeTerminarPhantom = Rutina("rutinaQueNoPuedeTerminarPhantom", List(aprendeCorte, morder, comeHierro))
    val carlitosLuegoDeRutina = rutinaQueNoPuedeTerminarPhantom.realizarRutina(phantom)

    assertTryConFailure(carlitosLuegoDeRutina, "NoTieneElAtaque")
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