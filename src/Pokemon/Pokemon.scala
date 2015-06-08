package Pokemon

import Pokemon.Estado._
import Pokemon.Genero._
import Pokemon.CondicionEvolucion._
import Actividad._
import Pokemon.Tipo.Dragon

/**
 * @author usuario
 */
class Pokemon(var especie: Especie, val genero: Genero, var peso: Int, var energiaMaxima: Int, var fuerza: Int,
    var velocidad: Int,var ataques:List[Ataque]) {//Hay que controlar que cumpla el peso de la especie  
  
  var nivel=1
  var experiencia:Long=0   
  var energia=energiaMaxima
  var estado:Estado= new Normal  
  
      
  def ganarExperiencia(cantidad: Int): Unit ={
    experiencia+=cantidad
    especie.subirNivel(this)   
  }
  
  def subiNivel(incrementoPeso:Int, incrementoEnergiaMaxima:Int, incrementoFuerza:Int, incrementoVelocidad:Int):Unit={
    nivel+=1
    peso+=incrementoPeso
    energiaMaxima+=incrementoEnergiaMaxima
    fuerza+=incrementoFuerza
    velocidad+=incrementoVelocidad
    especie.evolucioname(this)
    especie.subirNivel(this)
  }
  
  def intercambiar():Unit={
    especie.condicionEvolucion.intercambiar(this)
  }
  
  def usarPiedra(piedra:Piedra):Unit={
    especie.condicionEvolucion.usarPiedra(this,piedra:Piedra)
  }
  
  def evolucionar(nuevaEspecie:Especie):Unit={
    especie=nuevaEspecie
  } 
  
  /*-------------------------------------------------------------------------- Hasta aca es el punto 1*/  
  /*CREO QUE VA EN EL POKEMON */ 
  
  def tieneAtaque(ataque:Ataque)={
    ataques.contains(ataque)//???    comparar dos ataques???  podria ser con un string y un any
  }  
  
  def ataque(ataque:Ataque):Ataque={
    ataques.find { attack => attack==ataque }.get
  }
  
  def leQuedanAtaquesDe(attack:Ataque):Boolean={
    ataque(attack).puntosAtaque>0  
  }
  
  def estadoValido():Boolean={
    (peso>especie.pesoMaximo)&&(peso<0)//creo que hay mas, pero no se me ocurren    
  } 
  
  def realizarActividad(actividad:Actividad)={
    estado match {
      case dormido:Dormido if dormido.acividadesQuePuedeIgnorar>0 =>  //acividadesQuePuedeIgnorar debe ser val pero no se como seria 
      case _ => actividad match {
          case realizarAtaque:RealizarAtaque => if ((this.tieneAtaque(realizarAtaque.ataque)) || (this.leQuedanAtaquesDe(realizarAtaque.ataque)))
                                                  ataque(realizarAtaque.ataque).bajaPA
                                                  realizarAtaque.ataque.tipo match{
                                                    case _:Dragon => ganarExperiencia(80)
                                                    case _ =>          
                                                      if (especie.esDelTipoPrincipal(realizarAtaque.ataque.tipo))
                                                        ganarExperiencia(50)
                                                      else
                                                        if(especie.esDelTipoSecundario(realizarAtaque.ataque.tipo)) 
                                                          genero match{
                                                            case hembra:Hembra => ganarExperiencia(40) 
                                                            case macho:Macho => ganarExperiencia(20)
                                                          }
                                                  }
                                                  ataque(realizarAtaque.ataque).aplicarEfectoSecundarioA(this)
                                                else
                                                  new NoPuedeRealizarActividad//tirar error como en el de micro  
          //case otras activiidades
        } 
            
    }
    if (!estadoValido()) new EstadoInvalido
  }   
  
}