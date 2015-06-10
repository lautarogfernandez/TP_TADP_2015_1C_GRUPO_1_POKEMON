package Pokemon

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore
import org.junit.Before
import Pokemon._
import Pokemon.CondicionEvolucion._
import Pokemon.Estado._
import Pokemon.Genero._
import Pokemon.Tipo._
import Actividad._

/**
 * @author usuario
 */
class Punto2Test {
  
  val noTiene=new NoTiene
  val lunar=new Lunar
  val normal=new Tipo.Normal
  val hielo=new Hielo
  val psiquico=new Psiquico
  val fuego=new Fuego
  val dragon=new Dragon
  val volador=new Volador
  val lucha=new Lucha
  val fantasma=new Fantasma
  val veneno=new Veneno
  val agua=new Agua
  val planta=new Planta
  
  val piedraLunar=new Piedra(lunar)
  val piedraAgua=new Piedra(agua)
  
  val charizard=new Especie(350,100,fuego,10,10,10,10,volador)
  val charmeleon=new Especie(350,70,fuego,7,7,7,7,dragon,new SubirNivel(32),charizard)
  val charmander=new Especie(350,22,fuego,4,4,4,4,dragon,new SubirNivel(16),charmeleon)
  val raticate=new Especie(350,100,normal,10,10,10,10,noTiene)
  val rattata=new Especie(100,22,normal,4,4,4,4,noTiene,new SubirNivel(20),raticate)
  val jynx=new Especie(100,22,hielo,4,4,4,4,psiquico)
  val machamp=new Especie(100,70,lucha,4,4,4,4,noTiene)
  val machoke=new Especie(100,70,lucha,4,4,4,4,noTiene,new Intercambiar,machamp) 
  val machop=new Especie(100,70,lucha,4,4,4,4,noTiene,new SubirNivel(28),machoke) 
  val gengar=new Especie(100,50,fantasma,4,4,4,4,veneno)
  val seeking=new Especie(1000,50,agua,4,4,4,4,noTiene)
  val nidoqueen=new Especie(100,50,veneno,7,7,7,7,noTiene)
  val nidorina=new Especie(100,50,veneno,4,4,4,4,noTiene,new UsarPiedraLunar,nidoqueen)
  val starmie=new Especie(100,50,agua,7,7,7,7,psiquico)
  val staryu=new Especie(100,50,agua,4,4,4,4,noTiene,new CondicionEvolucion.UsarPiedra,starmie)
  
  val mordida=new AtaqueGenerico(normal,30,0)//no se que es lo de efecto secundario, debe ser una porcion de codigo que evalua el pokemon
  val hipnosis =new AtaqueGenerico(psiquico,20,0)
  val dragonTail =new AtaqueGenerico(dragon,10,0)
  val maldicion =new AtaqueGenerico(fantasma,25,0)
  val corte = new AtaqueGenerico(normal,30,0)
  
  var sinAtaques:List[AtaquePokemon]= List()
  
  val morder=RealizarAtaque(mordida)
  val hipnotizar=RealizarAtaque(hipnosis)
  val colaDragonea=RealizarAtaque(dragonTail)  
  val hacerPesas=new LevantarPesas (5)
  val nada=new Nadar(5)
  val nadaMas=new Nadar(125)
  val aprendeMaldicion=new AprenderAtaque(maldicion)
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
    carlitos=new Pokemon(rattata,new Macho,10,12,10,10,sinAtaques)
    carlitos.aprendeAtaque(mordida)
    carlita=new Pokemon(jynx,new Hembra,20,12,10,10,sinAtaques)
    carlita.aprendeAtaque(hipnosis)
    pequeñoDragon=new Pokemon(charmander,new Macho,10,12,10,10,sinAtaques)
    pequeñoDragon.aprendeAtaque(dragonTail)
    phantom=new Pokemon(gengar,new Macho,10,12,10,10,sinAtaques)
    luchador=new Pokemon(machop,new Macho,10,12,10,10,sinAtaques)
    unPokemonDeFuego=new Pokemon(charmander,new Macho,10,12,10,10,sinAtaques)
    unPokemonDeAgua=new Pokemon(seeking,new Macho,10,1000,10,10,sinAtaques)
    unPokemonQueEvolucionaConPiedraLunar=new Pokemon(nidorina,new Hembra,10,20,10,10,sinAtaques)
    unPokemonQueEvolucionaConPiedraAgua=new Pokemon(staryu,new Hembra,10,20,10,10,sinAtaques)
    peleador=new Pokemon(machoke,new Macho,10,20,10,10,sinAtaques)
  }
  
  def assertEstado(estado1:Estado, estado2:Estado)={
    assertEquals(true,estado1.getClass==estado2.getClass)
  }
  
//////////////////////////////////////////////////////APRENDER ATAQUE//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon aprende un ataque afin distinto de normal` = {
    phantom.realizarActividad(aprendeMaldicion)
    assertEquals(25,phantom.ataque(maldicion).puntosAtaque)
    assertEstado(new Estado.Normal,phantom.estado)
  }  
  
  @Test
  def `pokemon aprende un ataque normal` = {
    phantom.realizarActividad(aprendeCorte)
    assertEquals(30,phantom.ataque(corte).puntosAtaque)
    assertEstado(new Estado.Normal,phantom.estado)
  }  
  
  @Test
  def `pokemon intenta aprender un ataque no afin y queda KO` = {
    luchador.realizarActividad(aprendeMaldicion)
    assertEquals(0,luchador.ataques.size)
    assertEstado(new KO,luchador.estado)
  }    
  
//////////////////////////////////////////////////////REALIZAR ATAQUE//////////////////////////////////////////////////////////////////////////////////////////////////  
  @Test
  def `pokemon macho realiza un ataque de su tipo principal que puede hacer y gana experiencia` = {
    carlitos.realizarActividad(morder)
    assertEquals(50,carlitos.experiencia)
    assertEquals(29,carlitos.ataque(mordida).puntosAtaque)
  }
  
  @Test
  def `pokemon hembra gana realiza un ataque que no es de su tipo principal  que puede hacer  y gana experiencia` = {
    carlita.realizarActividad(hipnotizar)
    assertEquals(40,carlita.experiencia)
    assertEquals(19,carlita.ataque(hipnosis).puntosAtaque)
  }
  
  @Test
  def `pokemon realiza un ataque que no puede hacer y tira error` = {
    var tiroError=false
    try{carlitos.realizarActividad(hipnotizar)}
    catch{
        case e: NoTieneElAtaque => tiroError=true
    }
    assertEquals(true,tiroError)
  }
  
  @Test
  def `pokemon realiza un ataque que puede hacer, pero no tiene mas PA y tira error` = {   
    var tiroError=false
    carlitos.ataque(mordida).puntosAtaque=0
    try{carlitos.realizarActividad(morder)}
    catch{
        case e: NoTieneMasPA => tiroError=true
    }
    assertEquals(true,tiroError)
  }  
  
  @Test
  def `pokemon realiza un ataque de tipo dragon` = {   
    pequeñoDragon.realizarActividad(colaDragonea)
    assertEquals(80,pequeñoDragon.experiencia)
    assertEquals(9,pequeñoDragon.ataque(dragonTail).puntosAtaque)
  }    
  
  @Test
  def `pokemon realiza un ataque y sufre efecto secundario` = {//TODO: lo del efecto secundario    
    
  }    
  
//////////////////////////////////////////////////////LEVANTAR PESAS//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon fantasma quiere levantar pesas y tira error` = {   
    var tiroError=false    
    try{phantom.realizarActividad(hacerPesas)}
    catch{
        case e: NoPuedeLevantarPesas => tiroError=true
    }
    assertEquals(true,tiroError)
  }
  
  @Test
  def `pokemon no luchador levantar pesas y gana experiencia simple` = {   
    carlitos.realizarActividad(hacerPesas)
    assertEquals(5,carlitos.experiencia)
    assertEstado(new Estado.Normal,carlitos.estado)
  }  
  
  @Test
  def `pokemon paralizado que quiere levantar pesas, queda KO y no gana experiencia` = {   
    carlitos.estado=new Paralizado
    carlitos.realizarActividad(hacerPesas)    
    assertEquals(0,carlitos.experiencia)
    assertEstado(new KO,carlitos.estado)
  }
  
  @Test
  def `pokemon tipo lucha quiere levantar pesas gana el doble de experiencia` = {
    luchador.realizarActividad(hacerPesas)    
    assertEquals(10,luchador.experiencia)
    assertEstado(new Estado.Normal,luchador.estado)
  }  
  
////////////////////////////////////////////////////NADAR////////////////////////////////////////////////////////////////////////////////////////////////// 
  @Test
  def `pokemon de un tipo que pierde contra uno de agua quiere nadar y no gana experiencia y queda KO` = {
    unPokemonDeFuego.realizarActividad(nada)    
    assertEquals(0,unPokemonDeFuego.experiencia)
    assertEstado(new KO,unPokemonDeFuego.estado)
  }  

  @Test
  def `pokemon que es de un tipo que no pierde contra uno de agua nadar y gana experiencia` = {
    carlitos.realizarActividad(nada)    
    assertEquals(1000,carlitos.experiencia)
    assertEstado(new Estado.Normal,carlitos.estado)
  }    
  
  @Test
  def `pokemon de agua nada y gana experiencia y le aumenta la velocidad` = {
    val velocidadQueTendriaQueTener=unPokemonDeAgua.velocidad+4*4+2
    unPokemonDeAgua.realizarActividad(nadaMas)    
    assertEquals(25000,unPokemonDeAgua.experiencia)
    assertEquals(velocidadQueTendriaQueTener,unPokemonDeAgua.velocidad)
    assertEquals(5,unPokemonDeAgua.nivel)
    assertEstado(new Estado.Normal,unPokemonDeAgua.estado)
  }    
  
//////////////////////////////////////////////////USAR IEDRA//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `se usa una piedra lunar en un pokemon que evoluciona con ella y evoluciona` = {
    unPokemonQueEvolucionaConPiedraLunar.realizarActividad(usaPiedraLunar) 
    assertEquals(nidoqueen,unPokemonQueEvolucionaConPiedraLunar.especie)
    assertEstado(new Estado.Normal,unPokemonQueEvolucionaConPiedraLunar.estado)
  }    
  
  @Test
  def `se usa una piedra del tipo del pokemon y evoluciona` = {
    unPokemonQueEvolucionaConPiedraAgua.realizarActividad(usaPiedraAgua) 
    assertEquals(starmie,unPokemonQueEvolucionaConPiedraAgua.especie)
    assertEstado(new Estado.Normal,unPokemonQueEvolucionaConPiedraAgua.estado)
  }    
  
  @Test
  def `se usa una piedra del tipo que no corresponde al tipo pokemon pero no queda envenenado` = {
    unPokemonQueEvolucionaConPiedraAgua.realizarActividad(usaPiedraLunar) 
    assertEquals(staryu,unPokemonQueEvolucionaConPiedraAgua.especie)
    assertEstado(new Estado.Normal,unPokemonQueEvolucionaConPiedraAgua.estado)
  } 
  
  @Test
  def `se usa una piedra del tipo que no corresponde al pokemon y queda envenenado` = {
    pequeñoDragon.realizarActividad(usaPiedraAgua) 
    assertEquals(charmander,pequeñoDragon.especie)
    assertEstado(new Envenenado,pequeñoDragon.estado)
  }  

////////////////////////////////////////////////USAR POCION//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon usa pocion y se cura hasta su energia maxima` = {
    luchador.energia=1
    luchador.realizarActividad(usarPocion) 
    assertEquals(luchador.energiaMaxima,luchador.energia)
  }   

  @Test
  def `pokemon usa pocion y se cura hasta menos de su energia maxima` = {
    unPokemonDeAgua.energia=900
    unPokemonDeAgua.realizarActividad(usarPocion) 
    assertEquals(950,unPokemonDeAgua.energia)
  }   
  
////////////////////////////////////////////////USAR ANTIDOTO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon paralizado usa antidoto y su estado pasa a ser normal` = {
    luchador.estado=new Envenenado
    luchador.realizarActividad(usarAntidoto) 
    assertEstado(new Estado.Normal,luchador.estado)
  }   

  @Test
  def `pokemon no paralizado usa antidoto y no cambia el estado` = {
    luchador.realizarActividad(usarAntidoto) 
    assertEstado(new Estado.Normal,luchador.estado)
  }   
  
////////////////////////////////////////////////USAR ETHER//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon KO usa ether y no cambia su estado` = {
    luchador.estado=new KO
    luchador.realizarActividad(usarEther) 
    assertEstado(new KO,luchador.estado)
  }   

  @Test
  def `pokemon no KO usa ether y cambia su estado a normal` = {
    luchador.estado=new Envenenado
    luchador.realizarActividad(usarEther) 
    assertEstado(new Estado.Normal,luchador.estado)
  }      
  
////////////////////////////////////////////////COMER HIERRO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon come hierro y aumenta fuerza` = {
    val valorEsperado=luchador.fuerza+5
    luchador.realizarActividad(comeHierro) 
    assertEquals(valorEsperado,luchador.fuerza)
  }    
  
////////////////////////////////////////////////COMER CALCIO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon come calcio y aumenta velocidad` = {
    val valorEsperado=luchador.velocidad+5
    luchador.realizarActividad(comeCalcio) 
    assertEquals(valorEsperado,luchador.velocidad)
  }     
  
////////////////////////////////////////////////COMER ZINC//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon come zinc y aumenta los PA maximos de todos sus ataques` = {
    carlitos.aprendeAtaque(corte)
    val valorEsperado1=carlitos.ataque(mordida).puntosAtaqueMaximoDelPokemon+2
    val valorEsperado2=carlitos.ataque(corte).puntosAtaqueMaximoDelPokemon+2
    carlitos.realizarActividad(comeZinc) 
    assertEquals(valorEsperado1,carlitos.ataque(mordida).puntosAtaqueMaximoDelPokemon)
    assertEquals(valorEsperado2,carlitos.ataque(corte).puntosAtaqueMaximoDelPokemon)        
    val valorEsperado3=carlitos.ataque(mordida).puntosAtaqueMaximoDelPokemon+2
    val valorEsperado4=carlitos.ataque(corte).puntosAtaqueMaximoDelPokemon+2
    carlitos.realizarActividad(comeZinc) 
    assertEquals(valorEsperado3,carlitos.ataque(mordida).puntosAtaqueMaximoDelPokemon)
    assertEquals(valorEsperado4,carlitos.ataque(corte).puntosAtaqueMaximoDelPokemon)
  }    
  
////////////////////////////////////////////////DESCANSAR//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon con estado normal y energia menor al 50% descansa y sube todos los PA y cambia su estado a dormido` = {
    val valorEsperado1=carlitos.ataque(mordida).puntosAtaqueMaximoDelPokemon
    carlitos.ataque(mordida).puntosAtaque-=5
    carlitos.energia=4
    carlitos.realizarActividad(descansa) 
    assertEquals(valorEsperado1,carlitos.ataque(mordida).puntosAtaque)
    assertEstado(new Dormido,carlitos.estado) 
  }    
  
  @Test
  def `pokemon descansa y solo sube todos los PA` = {
    val valorEsperado1=carlitos.ataque(mordida).puntosAtaqueMaximoDelPokemon
    carlitos.ataque(mordida).puntosAtaque-=5
    carlitos.realizarActividad(descansa) 
    assertEquals(valorEsperado1,carlitos.ataque(mordida).puntosAtaque)
    assertEstado(new Estado.Normal,carlitos.estado) 
  }    
  
////////////////////////////////////////////////FINGIR INTERCAMBIO//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  def `pokemon cuya condicion de evolucion es el intercambio, es inercambiado y evoluciona` = {
    val valorEsperado=peleador.peso
    peleador.realizarActividad(teCambioPorOtro) 
    assertEquals(machamp,peleador.especie)
    assertEquals(valorEsperado,peleador.peso)
  }    
  
  @Test
  def `pokemon macho que no evoluciona por intercambio, es cambiado y varia su peso` = {
    val valorEsperado=pequeñoDragon.peso+1
    pequeñoDragon.realizarActividad(teCambioPorOtro) 
    assertEquals(charmander,pequeñoDragon.especie)
    assertEquals(valorEsperado,pequeñoDragon.peso)
  }   
  
  @Test
  def `pokemon hembra que no evoluciona por intercambio, es cambiado y varia su peso` = {
    val valorEsperado=carlita.peso-10
    carlita.realizarActividad(teCambioPorOtro) 
    assertEquals(jynx,carlita.especie)
    assertEquals(valorEsperado,carlita.peso)
  }    
  
  @Test
  def `pokemon hembra que no evoluciona por intercambio, es cambiado y varia su peso pero queda en estado invalido y lanza error` = {
    carlita.cambiarPeso(-19)
    var tiroError=false
    try{carlita.realizarActividad(teCambioPorOtro)}
    catch{
        case e: EstadoInvalido => tiroError=true
    }
    assertEquals(true,tiroError)    
  }  
  
}