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
  
  val charizard=new Especie(350,100,fuego,10,10,10,10,volador)
  val charmeleon=new Especie(350,70,fuego,7,7,7,7,dragon,new SubirNivel(32),charizard)
  val charmander=new Especie(350,22,fuego,4,4,4,4,dragon,new SubirNivel(16),charmeleon)
  val raticate=new Especie(350,100,normal,10,10,10,10,noTiene)
  val rattata=new Especie(100,22,normal,4,4,4,4,noTiene,new SubirNivel(20),raticate)
  val jynx=new Especie(100,22,hielo,4,4,4,4,psiquico)
  
  val mordida=new Ataque(normal,30)//no se que es lo de efecto secundario, debe ser una porcion de codigo que evalua el pokemon
  val hipnosis =new Ataque(psiquico,20)
  val dragonTail =new Ataque(dragon,10)
  
  var ataques1:List[Ataque]= List(mordida)
  var ataques2:List[Ataque]= List(hipnosis)
  var ataques3:List[Ataque]= List(dragonTail) 
  
  val morder=RealizarAtaque(mordida)
  val hipnotizar=RealizarAtaque(hipnosis)
  val colaDragonea=RealizarAtaque(dragonTail)  
  
  var carlitos:Pokemon=null
  var carlita:Pokemon=null
  var pequeñoDragon:Pokemon=null
  
  @Before
  def setUp(){    
    carlitos=new Pokemon(rattata,new Macho,10,12,10,10,ataques1)
    carlita=new Pokemon(jynx,new Hembra,10,12,10,10,ataques2)
    pequeñoDragon=new Pokemon(charmander,new Macho,10,12,10,10,ataques3)
  }
  
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
  def `pokemon realiza un ataque y sufre efecto secundario` = {//TODO    
    
    assertEquals(true,true)
  }     
  
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
  
}