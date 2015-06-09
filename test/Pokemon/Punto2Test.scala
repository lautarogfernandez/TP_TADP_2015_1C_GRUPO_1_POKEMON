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
  
  val mordida=new Ataque(normal,30)//no se que es lo de efecto secundario, debe ser una porcion de codigo que evalua el pokemon
  val hipnosis =new Ataque(psiquico,20)
  val dragonTail =new Ataque(dragon,10)
  
  var ataques1:List[Ataque]= List(mordida)
  var ataques2:List[Ataque]= List(hipnosis)
  var ataques3:List[Ataque]= List(dragonTail)
  var sinAtaques:List[Ataque]= List()
  
  val morder=RealizarAtaque(mordida)
  val hipnotizar=RealizarAtaque(hipnosis)
  val colaDragonea=RealizarAtaque(dragonTail)  
  val hacerPesas=new LevantarPesas (5)
  val nada=new Nadar(5)
  val nadaMas=new Nadar(125)
  
  var carlitos:Pokemon=null
  var carlita:Pokemon=null
  var pequeñoDragon:Pokemon=null
  var phantom:Pokemon=null
  var luchador:Pokemon=null
  var unPokemonDeFuego:Pokemon=null
  var unPokemonDeAgua:Pokemon=null
  
  @Before
  def setUp(){    
    carlitos=new Pokemon(rattata,new Macho,10,12,10,10,ataques1)
    carlita=new Pokemon(jynx,new Hembra,10,12,10,10,ataques2)
    pequeñoDragon=new Pokemon(charmander,new Macho,10,12,10,10,ataques3)
    phantom=new Pokemon(gengar,new Macho,10,12,10,10,sinAtaques)
    luchador=new Pokemon(machop,new Macho,10,12,10,10,sinAtaques)
    unPokemonDeFuego=new Pokemon(charmander,new Macho,10,12,10,10,sinAtaques)
    unPokemonDeAgua=new Pokemon(seeking,new Macho,10,1000,10,10,sinAtaques)
  }
  
  def assertEstado(estado1:Estado, estado2:Estado)={
    assertEquals(true,estado1.getClass==estado2.getClass)
  }
  
//////////////////////////////////////////////////////REALIZAR ATAQUE//////////////////////////////////////////////////////////////////////////////////////////////////  
  @Test
  def `pokemon macho realiza un ataque de su tipo principal que puede hacer y gana experiencia` = {
    carlitos.realizarActividad(morder)
    assertEquals(50,carlitos.experiencia)
    assertEquals(29,mordida.puntosAtaque)
  }
  
  @Test
  def `pokemon hembra gana realiza un ataque que no es de su tipo principal  que puede hacer  y gana experiencia` = {
    carlita.realizarActividad(hipnotizar)
    assertEquals(40,carlita.experiencia)
    assertEquals(19,hipnosis.puntosAtaque)
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
    mordida.puntosAtaque=0
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
    assertEquals(9,dragonTail.puntosAtaque)
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
    assertEquals(25000*2,unPokemonDeAgua.experiencia)
    assertEquals(velocidadQueTendriaQueTener,unPokemonDeAgua.velocidad)
    assertEquals(5,unPokemonDeAgua.nivel)
    assertEstado(new Estado.Normal,unPokemonDeAgua.estado)
  }    
  
}