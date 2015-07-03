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
import Actividad.AprenderAtaque

/**
 * @author usuario
 */
class Punto2Test extends TestsPokemon{  
  
//////////////////////////////////////////////////////APRENDER ATAQUE//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon aprende un ataque afin distinto de normal` = {
    phantom = phantom.realizarActividad(aprendeMaldicion)
    assertEquals(25,phantom.dameAtaque(maldicion).puntosAtaque)
    assertEstado(new EstadoNormal,phantom.estado)
  }  
  
  @Test
  def `pokemon aprende un ataque normal` = {
    phantom = phantom.realizarActividad(aprendeCorte)
    assertEquals(30,phantom.dameAtaque(corte).puntosAtaque)
    assertEstado(new EstadoNormal,phantom.estado)
  }  
  
  @Test
  def `pokemon intenta aprender un ataque no afin y queda KO` = {
    luchador = luchador.realizarActividad(aprendeMaldicion)
    assertEquals(0,luchador.ataques.size)
    assertEstado(new KO,luchador.estado)
  }    
  
//////////////////////////////////////////////////////REALIZAR ATAQUE//////////////////////////////////////////////////////////////////////////////////////////////////  
  @Test
  def `pokemon macho realiza un ataque de su tipo principal que puede hacer y gana experiencia` = {
    carlitos = carlitos.realizarActividad(morder)
    assertEquals(BigInt(50),carlitos.experiencia)
    assertEquals(29,carlitos.dameAtaque(mordida).puntosAtaque)
  }
  
  @Test
  def `pokemon hembra gana realiza un ataque que no es de su tipo principal  que puede hacer  y gana experiencia` = {
    carlita = carlita.realizarActividad(hipnotizar)
    assertEquals(BigInt(40),carlita.experiencia)
    assertEquals(19,carlita.dameAtaque(hipnosis).puntosAtaque)
  }
  
  @Test
  def `pokemon macho realiza un ataque que no es de su tipo principal ni secundario pero es ataque normal y gana experiencia` = {
    val unPokeDeAguaDespuesDeMorder = unPokemonDeAgua.realizarActividad(morder)
    assertEquals(BigInt(20), unPokeDeAguaDespuesDeMorder.experiencia)
    assertEquals(29, unPokeDeAguaDespuesDeMorder.dameAtaque(mordida).puntosAtaque)
  }
  
  @Test(expected = classOf[NoTieneElAtaque])
  def `pokemon realiza un ataque que no puede hacer y tira error` : Unit = {
    
    carlitos.realizarActividad(hipnotizar)

  }
  
  @Test(expected = classOf[NoTieneMasPA])
  def `pokemon realiza un ataque que puede hacer, pero no tiene mas PA y tira error` : Unit = {   

    val ataqueMordidaSinPA = carlitos.dameAtaque(mordida).copy(puntosAtaque = 0)
    carlitos.cambiarAtaque(mordida, ataqueMordidaSinPA).realizarActividad(morder)
    
  }  
  
  @Test
  def `pokemon realiza un ataque de tipo dragon` = {   
    pequeñoDragon = pequeñoDragon.realizarActividad(colaDragonea)
    assertEquals(BigInt(80),pequeñoDragon.experiencia)
    assertEquals(9,pequeñoDragon.dameAtaque(dragonTail).puntosAtaque)
  }    
  
  @Test
  def `pokemon realiza un ataque y sufre efecto secundario` = {   
    val carlitaDormida = carlita.realizarActividad(hipnotizar)
    assertEquals(new Dormido, carlitaDormida.estado)
  }    
  
  @Test
  def `pokemon realiza un ataque que no tiene efecto secundario, sigue manteniendo el estado anterior` = {   
    val carlitosDespuesDeMorder = carlitos.realizarActividad(morder)
    assertEquals(carlitosDespuesDeMorder.estado, carlitos.estado)
  }  
  
  @Test
  def `pokemon realiza ataque Reposar y aumenta su energia al maximo pero lo deja dormido` = {   
    assertEstado(carlitos.estado, new EstadoNormal)
    
    val reposar = new RealizarAtaque(reposo)
    
    val carlitosReposado = carlitos.copy(energia = carlitos.energia - 10).realizarActividad(aprendeReposo).realizarActividad(reposar)
    
    assertEquals(carlitosReposado.estado, new Dormido)
    assertEquals(carlitosReposado.energiaMaxima, carlitosReposado.energia)
  }    
    
  @Test
  def `pokemon realiza ataque Enfocarse sube su velocidad en un punto` = {   
    val enfocar = new RealizarAtaque(enfocarse)
    val carlitosEnfocado = aprendeEnfocarse(carlitos).realizarActividad(enfocar)
    assertEquals(carlitos.velocidad + 1, carlitosEnfocado.velocidad)
  }    
      
  @Test
  def `pokemon realiza ataque endurecerse, sube su energia 5 puntos pero queda paralizado` = {   
    val endurecer = RealizarAtaque(endurucerse)
    val carlitosEnfocado = aprendeEndurecerse(carlitos).realizarActividad(endurecer)
    assertEquals(carlitos.energia + 5, carlitosEnfocado.energia)
    assertEstado(carlitosEnfocado.estado, new Paralizado)
  }    
  
//////////////////////////////////////////////////////LEVANTAR PESAS//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test(expected = classOf[NoPuedeLevantarPesas])
  def `pokemon fantasma quiere levantar pesas y tira error` : Unit = {   
    phantom.realizarActividad(hacerPesas)
  }
  
  @Test
  def `pokemon no luchador levantar pesas y gana experiencia simple` = {   
    carlitos = carlitos.realizarActividad(hacerPesas)
    assertEquals(BigInt(5),carlitos.experiencia)
    assertEstado(new EstadoNormal,carlitos.estado)
  }  
  
  @Test
  def `pokemon paralizado que quiere levantar pesas, queda KO y no gana experiencia` = {   
    carlitos = carlitos.cambiarEstado(new Paralizado)
    carlitos = carlitos.realizarActividad(hacerPesas)    
    assertEquals(BigInt(0),carlitos.experiencia)
    assertEstado(new KO,carlitos.estado)
  }
  
  @Test
  def `pokemon tipo lucha quiere levantar pesas gana el doble de experiencia` = {
    luchador = luchador.realizarActividad(hacerPesas)    
    assertEquals(BigInt(10),luchador.experiencia)
    assertEstado(new EstadoNormal,luchador.estado)
  }  
  
////////////////////////////////////////////////////NADAR////////////////////////////////////////////////////////////////////////////////////////////////// 
  @Test
  def `pokemon de un tipo que pierde contra uno de agua quiere nadar y no gana experiencia y queda KO` = {
    unPokemonDeFuego = unPokemonDeFuego.realizarActividad(nada)    
    assertEquals(BigInt(0), unPokemonDeFuego.experiencia)
    assertEstado(new KO, unPokemonDeFuego.estado)
  }  

  @Test
  def `pokemon que es de un tipo que no pierde contra uno de agua nadar y gana experiencia` = {
    carlitos = carlitos.realizarActividad(nada)    
    assertEquals(BigInt(1000),carlitos.experiencia)
    assertEstado(new EstadoNormal,carlitos.estado)
  }    
  
  @Test
  def `pokemon de agua nada y gana experiencia y le aumenta la velocidad` = {
    val velocidadQueTendriaQueTener=unPokemonDeAgua.velocidad+4*4+2
    unPokemonDeAgua = unPokemonDeAgua.realizarActividad(nadaMas)    
    assertEquals(BigInt(25000), unPokemonDeAgua.experiencia)
    assertEquals(velocidadQueTendriaQueTener, unPokemonDeAgua.velocidad)
    assertEquals(5,unPokemonDeAgua.nivel)
    assertEstado(new EstadoNormal,unPokemonDeAgua.estado)
  }    
  
//////////////////////////////////////////////////USAR IEDRA//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `se usa una piedra lunar en un pokemon que evoluciona con ella y evoluciona` = {
    unPokemonQueEvolucionaConPiedraLunar = unPokemonQueEvolucionaConPiedraLunar.realizarActividad(usaPiedraLunar) 
    assertEquals(nidoqueen,unPokemonQueEvolucionaConPiedraLunar.especie)
    assertEstado(new EstadoNormal,unPokemonQueEvolucionaConPiedraLunar.estado)
  }    
  
  @Test
  def `se usa una piedra del tipo del pokemon y evoluciona` = {
    unPokemonQueEvolucionaConPiedraAgua = unPokemonQueEvolucionaConPiedraAgua.realizarActividad(usaPiedraAgua) 
    assertEquals(starmie,unPokemonQueEvolucionaConPiedraAgua.especie)
    assertEstado(new EstadoNormal,unPokemonQueEvolucionaConPiedraAgua.estado)
  }    
  
  @Test
  def `se usa una piedra del tipo que no corresponde al tipo pokemon pero no queda envenenado` = {
    unPokemonQueEvolucionaConPiedraAgua = unPokemonQueEvolucionaConPiedraAgua.realizarActividad(usaPiedraLunar) 
    assertEquals(staryu,unPokemonQueEvolucionaConPiedraAgua.especie)
    assertEstado(new EstadoNormal,unPokemonQueEvolucionaConPiedraAgua.estado)
  } 
  
  @Test
  def `se usa una piedra del tipo que no corresponde al pokemon y queda envenenado` = {
    pequeñoDragon = pequeñoDragon.realizarActividad(usaPiedraAgua) 
    assertEquals(charmander,pequeñoDragon.especie)
    assertEstado(new Envenenado,pequeñoDragon.estado)
  }  

////////////////////////////////////////////////USAR POCION//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon usa pocion y se cura hasta su energia maxima` = {
    luchador = luchador.subirAtributo(energiaASubir=1)
    luchador = luchador.realizarActividad(usarPocion) 
    assertEquals(luchador.energiaMaxima,luchador.energia)
  }   

  @Test
  def `pokemon usa pocion y se cura hasta menos de su energia maxima` = {
    unPokemonDeAgua = unPokemonDeAgua.copy(energia = 900)
    unPokemonDeAgua = unPokemonDeAgua.realizarActividad(usarPocion) 
    assertEquals(950, unPokemonDeAgua.energia)
  }   
  
////////////////////////////////////////////////USAR ANTIDOTO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon paralizado usa antidoto y su estado pasa a ser normal` = {
    luchador = luchador.cambiarEstado(new Envenenado)
    luchador = luchador.realizarActividad(usarAntidoto) 
    assertEstado(new EstadoNormal,luchador.estado)
  }   

  @Test
  def `pokemon no paralizado usa antidoto y no cambia el estado` = {
    luchador = luchador.realizarActividad(usarAntidoto) 
    assertEstado(new EstadoNormal,luchador.estado)
  }   
  
////////////////////////////////////////////////USAR ETHER//////////////////////////////////////////////////////////////////////////////////////////////////
  //@Test(expected )
  def `pokemon KO usa ether y no cambia su estado` = {
    luchador = luchador.cambiarEstado(new KO)
    val nuevoLuchador = luchador.realizarActividad(usarEther) 
    assertEstado(new KO, nuevoLuchador.estado)
  }   

  @Test
  def `pokemon no KO usa ether y cambia su estado a normal` = {
    luchador = luchador.cambiarEstado(new Envenenado)
    val nuevoLuchador = luchador.realizarActividad(usarEther) 
    assertEstado(new EstadoNormal, nuevoLuchador.estado)
  }      
  
////////////////////////////////////////////////COMER HIERRO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon come hierro y aumenta fuerza` = {
    val valorEsperado=luchador.fuerza+5
    val nuevoLuchador = luchador.realizarActividad(comeHierro) 
    assertEquals(valorEsperado, nuevoLuchador.fuerza)
  }    
  
////////////////////////////////////////////////COMER CALCIO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon come calcio y aumenta velocidad` = {
    val valorEsperado=luchador.velocidad+5
    val nuevoLuchador = luchador.realizarActividad(comeCalcio) 
    assertEquals(valorEsperado, nuevoLuchador.velocidad)
  }     
  
////////////////////////////////////////////////COMER ZINC//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon come zinc y aumenta los PA maximos de todos sus ataques` = {
    carlitos = aprendeCorte(carlitos)
    val valorEsperado1=carlitos.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon+2
    val valorEsperado2=carlitos.dameAtaque(corte).puntosAtaqueMaximoDelPokemon+2
    val carlitosDespuesDeComerZinc = carlitos.realizarActividad(comeZinc) 
    assertEquals(valorEsperado1, carlitosDespuesDeComerZinc.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon)
    assertEquals(valorEsperado2, carlitosDespuesDeComerZinc.dameAtaque(corte).puntosAtaqueMaximoDelPokemon)        
    val valorEsperado3 = carlitosDespuesDeComerZinc.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon+2
    val valorEsperado4 = carlitosDespuesDeComerZinc.dameAtaque(corte).puntosAtaqueMaximoDelPokemon+2
    val carlitosDespuesDeComerZinc2Veces = carlitosDespuesDeComerZinc.realizarActividad(comeZinc) 
    assertEquals(valorEsperado3, carlitosDespuesDeComerZinc2Veces.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon)
    assertEquals(valorEsperado4, carlitosDespuesDeComerZinc2Veces.dameAtaque(corte).puntosAtaqueMaximoDelPokemon)
  }    
  
////////////////////////////////////////////////DESCANSAR//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon con estado normal y energia menor al 50% descansa y sube todos los PA y cambia su estado a dormido` = {
    val valorEsperado1=carlitos.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon
    val ataqueMordidaSinPA = carlitos.dameAtaque(mordida).copy(puntosAtaque = 0)
    carlitos = carlitos.cambiarAtaque(mordida, ataqueMordidaSinPA).copy(energia=4)
    
    assertEquals(carlitos.dameAtaque(mordida).puntosAtaque, 0)
    val carlitosDescanzado = carlitos.realizarActividad(descansa) 
    assertEquals(valorEsperado1, carlitosDescanzado.dameAtaque(mordida).puntosAtaque)
    assertEstado(new Dormido, carlitosDescanzado.estado) 
  }    
  
  @Test
  def `pokemon descansa y solo sube todos los PA` = {
    val valorEsperado1 = carlitos.dameAtaque(mordida).puntosAtaqueMaximoDelPokemon
    val ataqueMordidaSinPA = carlitos.dameAtaque(mordida).copy(puntosAtaque = 0)
    carlitos = carlitos.cambiarAtaque(mordida, ataqueMordidaSinPA)
    
    assertEquals(carlitos.dameAtaque(mordida).puntosAtaque, 0)
    val carlitosDescanzado = carlitos.realizarActividad(descansa) 
    assertEquals(valorEsperado1, carlitosDescanzado.dameAtaque(mordida).puntosAtaque)
    assertEstado(new EstadoNormal, carlitosDescanzado.estado) 
  }    
  
////////////////////////////////////////////////FINGIR INTERCAMBIO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon cuya condicion de evolucion es el intercambio, es inercambiado y evoluciona` = {
    val valorEsperado = peleador.peso
    val peleadorCambiado = peleador.realizarActividad(teCambioPorOtro) 
    assertEquals(machamp, peleadorCambiado.especie)
    assertEquals(valorEsperado, peleadorCambiado.peso)
  }    
  
  @Test
  def `pokemon macho que no evoluciona por intercambio, es cambiado y varia su peso` = {
    val valorEsperado = pequeñoDragon.peso+1
    val pequeñoDragonCambiado = pequeñoDragon.realizarActividad(teCambioPorOtro) 
    assertEquals(charmander, pequeñoDragonCambiado.especie)
    assertEquals(valorEsperado, pequeñoDragonCambiado.peso)
  }   
  
  @Test
  def `pokemon hembra que no evoluciona por intercambio, es cambiado y varia su peso` = {
    val valorEsperado=carlita.peso-10
    val carlitaIntercambiada = carlita.realizarActividad(teCambioPorOtro) 
    assertEquals(jynx, carlitaIntercambiada.especie)
    assertEquals(valorEsperado, carlitaIntercambiada.peso)
  }    
  
  @Test(expected = classOf[EstadoInvalido])
  def `pokemon hembra que no evoluciona por intercambio, es cambiado y varia su peso pero queda en estado invalido y lanza error` : Unit = {
    carlita = carlita.cambiarPeso(-19)
    carlita.realizarActividad(teCambioPorOtro)    
  }  

////////////////////////////////////////////////ESTADO KO//////////////////////////////////////////////////////////////////////////////////////////////////

  @Test(expected = classOf[NoPuedeRealizarActividadPorKO])
  def `pokemon no realiza actividad si esta en estado KO` : Unit = {
    val endurecer = RealizarAtaque(endurucerse)
    val carlitosKO = hacerPesas(endurecer(aprendeEndurecerse(carlitos)))
    morder(carlitosKO)
  }  
  
}