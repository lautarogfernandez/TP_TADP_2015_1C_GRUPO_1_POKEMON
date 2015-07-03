package Pokemon

import org.junit.Assert.assertEquals
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
import tadp.grupo1.rutina.AnalizadorDeRutinas
import tadp.grupo1.rutina.NingunaRutinaPudoSerCompletada
import scala.util.Try
import scala.util.Success
import scala.util.Failure

/**
 * @author Alejandro
 */
class Punto4Test extends TestsPokemon{

  // Rutinas
  val rutinaQuePuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(aprendeCorte, hacerPesas, comeZinc, morder, comeHierro))
  val rutinaQueNoPuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(aprendeCorte, nada, comeZinc, morder, comeHierro))
  val rutinaQueNoPuedeTerminarPhantom = Rutina("rutinaQueNoPuedeTerminarPhantom", List(aprendeCorte, hacerPesas, comeZinc, morder, comeHierro))
  val rutinaVacia = Rutina("rutinaVacia", List())
  val rutinaParaSerCampeonDeNado = Rutina("rutinaParaSerCampeonDeNado", List(nadaMas, nada, nadaMas, nada, nadaMas, nadaMas, nadaMas))
  val rutinaKO = Rutina("rutinaKO" , List(aprendeEndurecerse, endurecer, hacerPesas, nada))
     
  // Criterios
  def mayorNivelPosible = { (pokemon1 : Pokemon, pokemon2 : Pokemon)  => pokemon1.nivel > pokemon2.nivel }
  def mayorCantidadEnergiaPosible = { (pokemon1 : Pokemon, pokemon2 : Pokemon)  => pokemon1.energia > pokemon2.energia }
  def menorPesoPosible = { (pokemon1 : Pokemon, pokemon2 : Pokemon)  => pokemon1.peso > pokemon2.peso }

  @Test
  def `usando criterio por nivel devuelve el nombre de la rutina que deja mayor nivel` : Unit = {
    
    val listaDeRutinas = List(rutinaVacia, rutinaParaSerCampeonDeNado, rutinaQuePuedeTerminarCarlitos, rutinaQueNoPuedeTerminarCarlitos)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, listaDeRutinas, mayorNivelPosible)
    
    val nivelDespeusDeRutinaVacia = obtenerObjetoAdentroDelTry(rutinaVacia.realizarRutina(unPokemonDeAgua)).nivel()
    val nivelDespeusDeRutinaParaSerCampeonDeNado = obtenerObjetoAdentroDelTry(rutinaParaSerCampeonDeNado.realizarRutina(unPokemonDeAgua)).nivel() // TODO ver si esta bien que llegue a nivel 9
    val nivelDespeusDeRutinaQuePuedeTerminarCarlitos = obtenerObjetoAdentroDelTry(rutinaQuePuedeTerminarCarlitos.realizarRutina(unPokemonDeAgua)).nivel()
    val nivelDespeusDeRutinaQueNoPuedeTerminarCarlitos = obtenerObjetoAdentroDelTry(rutinaQueNoPuedeTerminarCarlitos.realizarRutina(unPokemonDeAgua)).nivel()
    
    assertEquals(rutinaParaSerCampeonDeNado.nombre, obtenerObjetoAdentroDelTry(nombreRutinaQueDaMayorNivel))    
  }
  
  @Test(expected = classOf[NingunaRutinaPudoSerCompletada])
  def `pokemon no puede terminar ninguna de las rutinas debo obtener la exception que me dice porque no pudo terminar ningun` : Unit = {
    
    val phantomPasadoPorAgua = rutinaParaSerCampeonDeNado.realizarRutina(phantom)
    val phantomNoPuedeLevantarPesa = rutinaQueNoPuedeTerminarPhantom.realizarRutina(phantom)
    val phantomKO = rutinaKO.realizarRutina(phantom)
    
    val listaDeRutinas = List(rutinaParaSerCampeonDeNado, rutinaQueNoPuedeTerminarPhantom, rutinaKO)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(phantom, listaDeRutinas, mayorNivelPosible)
    obtenerObjetoAdentroDelTry(nombreRutinaQueDaMayorNivel)
  }
  
  @Test(expected = classOf[NingunaRutinaPudoSerCompletada])
  def `no hay ninguna rutina dentro de la lista de rutinas a analizar y devuelvo NingunaRutinaPudoSerCompletada` : Unit = {
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, List(), mayorNivelPosible)
    obtenerObjetoAdentroDelTry(nombreRutinaQueDaMayorNivel)
  }
  
}