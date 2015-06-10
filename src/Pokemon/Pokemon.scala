package Pokemon

import Pokemon.Estado._
import Pokemon.Genero._
import Pokemon.CondicionEvolucion._
import Actividad._
import Pokemon.Tipo._

/**
 * @author usuario
 */
class Pokemon(var especie: Especie, val genero: Genero, var peso: Int, var energiaMaxima: Int, var fuerza: Int,
    var velocidad: Int,var ataques:List[AtaquePokemon]) {//Hay que controlar que cumpla el peso de la especie  
  
  var nivel=1
  var experiencia:Long=0   
  var energia=energiaMaxima
  var estado:Estado= new Estado.Normal  
  
      
  def ganarExperiencia(cantidad: Int): Unit ={
    experiencia+=cantidad
    especie.subirNivel(this)   
  }
  
  def subiNivel(incrementoPeso:Int, incrementoEnergiaMaxima:Int, incrementoFuerza:Int, incrementoVelocidad:Int):Unit={
    nivel+=1
    peso+=incrementoPeso
    energiaMaxima+=incrementoEnergiaMaxima
    fuerza+=incrementoFuerza
    ganarVelocidad(incrementoVelocidad)
    especie.evolucioname(this)
    especie.subirNivel(this)
  }
  
  def intercambiar():Unit={
    especie.condicionEvolucion.intercambiar(this)
  }
  
  def evolucionar():Unit={
    val nuevaEspecie=especie.evolucion
    especie=nuevaEspecie
  } 
  
  /*-------------------------------------------------------------------------- Hasta aca es el punto 1*/  
  /*CREO QUE VA EN EL POKEMON */ 
  
  def perderEnergia(cantidad:Int)={
    energia-=cantidad
  }
  
  def ganarVelocidad(cantidad:Int)={
    velocidad+=cantidad
  }
  
  def tieneAtaque(ataque:AtaqueGenerico)={
    ataques.exists { attack => attack.ataqueGenerico==ataque }
  }  
  
  def ataque(ataque:AtaqueGenerico):AtaquePokemon={
    ataques.find {attack => attack.es(ataque)}.get
  }
  
  def leQuedanAtaquesDe(attack:AtaqueGenerico):Boolean={
    ataque(attack).puntosAtaque>0  
  }
  
  def estadoValido():Boolean={
    (peso<=especie.pesoMaximo)&&(peso>0)//creo que hay mas, pero no se me ocurren    
  } 
  
  def aprendeAtaque(ataque:AtaqueGenerico){
    ataques=ataques.::(new AtaquePokemon(ataque))
  }
  
/*  def usaPiedra(piedra:Piedra)={
    especie.condicionEvolucion match{
      case _:UsarPiedraLunar if piedra.tipo==new Lunar => evolucionar()
      case _:CondicionEvolucion.UsarPiedra if piedra.tipo==especie.tipoPrincipal => evolucionar() 
      case _ if (piedra.tipo.leGanaA(especie.tipoPrincipal)||piedra.tipo.leGanaA(especie.tipoSecundario)) => estado=new Envenenado
    }
  }*/
  
  def usaPiedra(piedra:Piedra)={
    especie.condicionEvolucion match{
      case _:UsarPiedraLunar if piedra.tipo==new Lunar => evolucionar()
      case _:CondicionEvolucion.UsarPiedra if piedra.tipo==especie.tipoPrincipal => evolucionar() 
      case _ => if (piedra.tipo.leGanaA(especie.tipoPrincipal) || piedra.tipo.leGanaA(especie.tipoSecundario))
                  estado=new Envenenado
    }
  }  
  
  def realizarActividad(actividad:Actividad):Unit={
    estado match {
      case dormido:Dormido if dormido.acividadesQuePuedeIgnorar>0 =>  //acividadesQuePuedeIgnorar debe ser val pero no se como seria 
      case _ => actividad match {
          case realizarAtaque:RealizarAtaque => if (this.tieneAtaque(realizarAtaque.ataque))                                                  
                                                  if (this.leQuedanAtaquesDe(realizarAtaque.ataque))
                                                    {
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
                                                    }                                                  
                                                  else
                                                    throw new NoTieneMasPA//hacer error como en el de micro                                                    
                                                else
                                                  throw new NoTieneElAtaque//hacer error como en el de micro
          
          case levantarPesas:LevantarPesas => especie.tipoPrincipal match{
                                                case _:Fantasma => throw new NoPuedeLevantarPesas
                                                case _ => if (levantarPesas.cantidadKilos<=fuerza*10)            
                                                            estado match{
                                                              case _:Paralizado => estado=new KO
                                                              case _ => especie.tipoPrincipal match{
                                                                          case _:Lucha => ganarExperiencia(levantarPesas.cantidadKilos*2)   
                                                                          case _ => especie.tipoSecundario match{
                                                                                      case _:Lucha => ganarExperiencia(levantarPesas.cantidadKilos*2)
                                                                                      case _ => ganarExperiencia(levantarPesas.cantidadKilos)
                                                                                    } 
                                                                        }                                                  
                                                            }                                          
                                                          else
                                                            estado=new Paralizado
                                              }              
          case nadar:Nadar => if ((energia-nadar.minutos)<0)//no se si hace falta porque controla el estado al final   
                                new NoPuedeRealizarActividad
                              else
                                especie.tipoPrincipal match{
                                  case _:Agua => ganarExperiencia(200*nadar.minutos)
                                                 ganarVelocidad(nadar.minutos/60) 
                                                 perderEnergia(nadar.minutos)
                                  case _ if ((especie.tipoPrincipal.pierdeContra(new Agua))|| especie.tipoSecundario.pierdeContra(new Agua)) => estado=new KO
                                  case _ =>  ganarExperiencia(200*nadar.minutos)
                                             perderEnergia(nadar.minutos)
                                }
          case aprenderAtaque:AprenderAtaque => aprenderAtaque.ataque match{
                                                  case attack if (especie.esAfin(attack.tipo)) => aprendeAtaque(attack)
                                                  case _ => estado=new KO  
                                                }
          case usarPiedra:Actividad.UsarPiedra => usaPiedra(usarPiedra.piedra)
            
            
            
            //case otras activiidades
        } 
            
    }
    if (!estadoValido()) new EstadoInvalido
  }   
  
}