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
  val rutinaQuePuedeTerminarCarlitos2 = Rutina("rutinaQuePuedeTerminarCarlitos2", List(aprendeCorte, hacerPesas, comeZinc, morder, comeHierro))
  val rutinaQueNoPuedeTerminarCarlitos = Rutina("rutinaQuePuedeTerminarCarlitos", List(aprendeCorte, nada, comeZinc, morder, comeHierro))
  val rutinaQueNoPuedeTerminarPhantom = Rutina("rutinaQueNoPuedeTerminarPhantom", List(aprendeCorte, hacerPesas, comeZinc, morder, comeHierro))
  val rutinaVacia = Rutina("rutinaVacia", List())
  val rutinaParaNadarHastaElBorde1SolaActividad = Rutina("rutinaParaNadarHastaElBorde1SolaActividad", List(nadaMas))
  val rutinaParaNadarHastaElBorde1SolaActividad2 = Rutina("rutinaParaNadarHastaElBorde1SolaActividad2", List(nadaMas))
  val rutinaParaSerCampeonDeNado = Rutina("rutinaParaSerCampeonDeNado", List(nadaMas, nada, nadaMas, nada, nadaMas, nadaMas, nadaMas))
  val rutinaKO = Rutina("rutinaKO" , List(aprendeEndurecerse, endurecer, hacerPesas, nada))
     
  // Criterios
  def mayorNivelPosible = { (pokemon1 : Pokemon, pokemon2 : Pokemon)  => pokemon1.nivel > pokemon2.nivel }
  def mayorCantidadEnergiaPosible = { (pokemon1 : Pokemon, pokemon2 : Pokemon)  => pokemon1.energia > pokemon2.energia }
  def menorPesoPosible = { (pokemon1 : Pokemon, pokemon2 : Pokemon)  => pokemon1.peso < pokemon2.peso }

  @Test
  def `usando criterio por nivel y una sola rutina devuelve el nombre de la rutina` : Unit = {

    val listaDeRutinas = List(rutinaParaNadarHastaElBorde1SolaActividad)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, listaDeRutinas, mayorNivelPosible)

    assertEquals(5, rutinaParaNadarHastaElBorde1SolaActividad.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.nivel)

    assertEquals(rutinaParaNadarHastaElBorde1SolaActividad.nombre, nombreRutinaQueDaMayorNivel.obtenerObjetoAdentroDelTry)
  }

  @Test
  def `usando criterio por nivel y la rutina que hay no lo hace subir de nivel devuelve esa rutina igual` : Unit = {

    val listaDeRutinas = List(rutinaQuePuedeTerminarCarlitos)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(carlitos, listaDeRutinas, mayorNivelPosible)

    assertEquals(1, rutinaQuePuedeTerminarCarlitos.realizarRutina(carlitos).obtenerObjetoAdentroDelTry.nivel)

    assertEquals(rutinaQuePuedeTerminarCarlitos.nombre, nombreRutinaQueDaMayorNivel.obtenerObjetoAdentroDelTry)
  }

  @Test
  def `si hay 2 rutinas que dejan con el mismo nivel al Pokemon, devuelve el nombre de la primer rutina ingresada` : Unit = {

    val listaDeRutinas = List(rutinaParaNadarHastaElBorde1SolaActividad, rutinaVacia, rutinaParaNadarHastaElBorde1SolaActividad2)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, listaDeRutinas, mayorNivelPosible)

    assertEquals(5, rutinaParaNadarHastaElBorde1SolaActividad.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.nivel())

    assertEquals(rutinaParaNadarHastaElBorde1SolaActividad.nombre, nombreRutinaQueDaMayorNivel.obtenerObjetoAdentroDelTry)
  }


  @Test
  def `usando criterio por nivel devuelve el nombre de la rutina que deja mayor nivel` : Unit = {
    
    val listaDeRutinas = List(rutinaVacia, rutinaParaSerCampeonDeNado, rutinaQuePuedeTerminarCarlitos, rutinaQueNoPuedeTerminarCarlitos)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, listaDeRutinas, mayorNivelPosible)
    
    val nivelDespeusDeRutinaVacia = rutinaVacia.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.nivel
    val nivelDespeusDeRutinaParaSerCampeonDeNado = rutinaParaSerCampeonDeNado.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.nivel // TODO ver si esta bien que llegue a nivel 9
    val nivelDespeusDeRutinaQuePuedeTerminarCarlitos = rutinaQuePuedeTerminarCarlitos.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.nivel
    val nivelDespeusDeRutinaQueNoPuedeTerminarCarlitos = rutinaQueNoPuedeTerminarCarlitos.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.nivel
    
    assertEquals(rutinaParaSerCampeonDeNado.nombre, nombreRutinaQueDaMayorNivel.obtenerObjetoAdentroDelTry)    
  }

  @Test
  def `usando criterio mayorCantidadEnergiaPosible devuelve el nombre de la rutina que deja mayor energia` : Unit = {

    val listaDeRutinas = List(rutinaParaSerCampeonDeNado, rutinaParaNadarHastaElBorde1SolaActividad, rutinaQuePuedeTerminarCarlitos)
    val nombreRutinaQueDejaConMasEnergia = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, listaDeRutinas, mayorCantidadEnergiaPosible)

    assertEquals(365, rutinaParaSerCampeonDeNado.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.energia)
    assertEquals(875, rutinaParaNadarHastaElBorde1SolaActividad.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.energia)
    assertEquals(1000, rutinaQuePuedeTerminarCarlitos.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.energia)

    assertEquals(rutinaQuePuedeTerminarCarlitos.nombre, nombreRutinaQueDejaConMasEnergia.obtenerObjetoAdentroDelTry)
  }

  @Test
  def `usando criterio menorPesoPosible devuelve el nombre de la rutina que deja menor peso` : Unit = {
    
    val listaDeRutinas = List(rutinaParaSerCampeonDeNado, rutinaQuePuedeTerminarCarlitos, rutinaParaNadarHastaElBorde1SolaActividad)
    val nombreRutinaQueDejaConMenosPeso = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, listaDeRutinas, menorPesoPosible)

    assertEquals(38, rutinaParaSerCampeonDeNado.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.peso)
    assertEquals(26, rutinaParaNadarHastaElBorde1SolaActividad.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.peso)
    assertEquals(10, rutinaQuePuedeTerminarCarlitos.realizarRutina(unPokemonDeAgua).obtenerObjetoAdentroDelTry.peso)

    assertEquals(rutinaQuePuedeTerminarCarlitos.nombre, nombreRutinaQueDejaConMenosPeso.obtenerObjetoAdentroDelTry)
  }
  
  @Test(expected = classOf[NingunaRutinaPudoSerCompletada])
  def `pokemon no puede terminar ninguna de las rutinas debo obtener la exception que me dice porque no pudo terminar ningun` : Unit = {
    
    val phantomPasadoPorAgua = rutinaParaSerCampeonDeNado.realizarRutina(phantom)
    val phantomNoPuedeLevantarPesa = rutinaQueNoPuedeTerminarPhantom.realizarRutina(phantom)
    val phantomKO = rutinaKO.realizarRutina(phantom)
    
    val listaDeRutinas = List(rutinaParaSerCampeonDeNado, rutinaQueNoPuedeTerminarPhantom, rutinaKO)
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(phantom, listaDeRutinas, mayorNivelPosible)
    nombreRutinaQueDaMayorNivel.obtenerObjetoAdentroDelTry
  }
  
  @Test(expected = classOf[NingunaRutinaPudoSerCompletada])
  def `no hay ninguna rutina dentro de la lista de rutinas a analizar y devuelvo NingunaRutinaPudoSerCompletada` : Unit = {
    val nombreRutinaQueDaMayorNivel = AnalizadorDeRutinas.analizarRutinasSegunCriterio(unPokemonDeAgua, List(), mayorNivelPosible)
    nombreRutinaQueDaMayorNivel.obtenerObjetoAdentroDelTry
  }
  
}