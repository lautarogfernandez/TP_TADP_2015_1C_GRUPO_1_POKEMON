package tadp.grupo1.pokemon

import tadp.grupo1.pokemon.estado._
import tadp.grupo1.pokemon.genero._
import tadp.grupo1.pokemon.condicion_evolucion._
import Actividad._
import tadp.grupo1.pokemon.tipo._

/**
 * @author usuario
 */
case class Pokemon(val especie: Especie, val genero: Genero, val peso: Int, val energiaMaxima: Int, val energia: Int, val fuerza: Int,
                   val velocidad: Int, val ataques: List[AtaquePokemon], val nivel: Int = 1,
                   val estado: Estado = new EstadoNormal, val experiencia: Long = 0) { //Hay que controlar que cumpla el peso de la especie  

  def ganarExperiencia(cantidad: Int): Pokemon = {
    val nuevaExperiencia = experiencia + cantidad
    var nuevoPokemon = copy(experiencia = nuevaExperiencia)
    nuevoPokemon = especie.subirNivel(nuevoPokemon)
    nuevoPokemon
  }

  def cambiarEstado(nuevoEstado: Estado): Pokemon = {
    copy(estado = nuevoEstado)
  }

  def subiNivel(incrementoPeso: Int, incrementoEnergiaMaxima: Int, incrementoFuerza: Int, incrementoVelocidad: Int): Pokemon = {
    var nuevoPokemon = copy(nivel = nivel + 1, peso = peso + incrementoPeso, energiaMaxima = energiaMaxima + incrementoEnergiaMaxima,
      fuerza = fuerza + incrementoFuerza, velocidad = velocidad + incrementoVelocidad)

    nuevoPokemon = especie.evolucioname(nuevoPokemon)
    nuevoPokemon = especie.subirNivel(nuevoPokemon)
    nuevoPokemon
  }

  def intercambiar(): Unit = {
    especie.condicionEvolucion.intercambio(this)
  }

  def evolucionar(): Pokemon = {
    val nuevaEspecie = especie.evolucion
    copy(especie = nuevaEspecie)
  }

  /*-------------------------------------------------------------------------- Hasta aca es el punto 1*/
  /*CREO QUE VA EN EL POKEMON */

  def cambiarPeso(variacion: Int) = {
    copy(peso = peso + variacion)
  }

  def perderEnergia(cantidad: Int) = {
    copy(energia = this.energia - cantidad)
  }

  def ganarVelocidad(cantidad: Int) = {
    copy(velocidad = velocidad + cantidad)
  }

  def subirAtributo(energiaASubir: Int = 0, fuerzaASubir: Int = 0, velocidadASubir: Int = 0): Pokemon = {
    copy(energia = energia + energiaASubir, fuerza = fuerza + fuerzaASubir, velocidad = velocidad + velocidadASubir)
  }

  def tieneAtaque(ataque: AtaqueGenerico) = {
    ataques.exists { attack => attack.ataqueGenerico == ataque }
  }

  def dameAtaque(ataque: AtaqueGenerico): AtaquePokemon = {
    ataques.find { attack => attack.es(ataque) }.get
  }

  def leQuedanAtaquesDe(attack: AtaqueGenerico): Boolean = {
    dameAtaque(attack).puntosAtaque > 0
  }

  def estadoValido(): Boolean = {
    (peso <= especie.pesoMaximo) && (peso > 0) //creo que hay mas, pero no se me ocurren    
  }

  def aprendeAtaque(ataque: AtaqueGenerico): Pokemon = {
    val nuevaListAtaques = ataques.::(new AtaquePokemon(ataque, ataque.puntosAtaqueMaximo, ataque.puntosAtaqueMaximo))
    copy(ataques = nuevaListAtaques)
  }

  def usaPiedra(piedra: Piedra) : Pokemon = {
    especie.condicionEvolucion match {
      case _: UsarPiedraLunar if piedra.tipo == new Lunar                     => evolucionar()
      case _: UsarPiedraParaEvolucion if piedra.tipo == especie.tipoPrincipal => evolucionar()
      case _ => var pokemonCapazEnvenenado = this 
        if (piedra.tipo.leGanaA(especie.tipoPrincipal) || piedra.tipo.leGanaA(especie.tipoSecundario)){
          pokemonCapazEnvenenado = copy(estado = new Envenenado)
        }
        pokemonCapazEnvenenado
      }
  }

  def usaPocion() : Pokemon = {
    copy(energia = math.min(energia + 50, energiaMaxima))
  }

  def usaAntidoto() : Pokemon = {
    estado match {
      case _: Envenenado => cambiarEstado(new EstadoNormal)
      case _             => this
    }
  }

  def usaEther() : Pokemon = {
    estado match {
      case _: KO => this // TODO no habria que controlar el KO antes de entrar a la actividad en si ?? porque con el KO creo que no puede hacer nada
      case _     => cambiarEstado(new EstadoNormal)
    }
  }

  def comeHierro() : Pokemon = {
    subirAtributo(fuerzaASubir = 5)
  }

  def comeCalcio() : Pokemon = {
    subirAtributo(velocidadASubir = 5)
  }

  def comeZinc() : Pokemon = {
    ataques.map(ataque => ataque.aumentarPAMaximo(2)) // TODO es correcto 
    this
  }
  
  def descansa() : Pokemon = {
    ataques.map(ataque => ataque.reestablecerPA)
    estado match {
      case _: EstadoNormal if ((energiaMaxima * 0.5) > energia) => cambiarEstado(new Dormido)
      case _ => this
    }
  }
  def fingirIntercambio() : Pokemon = {
    especie.sufriIntercambio(this)
  }
  
  def bajarPA(ataque : AtaqueGenerico) = {
    
    val nuevoAtaque = dameAtaque(ataque).bajaPA()
    val nuevaListaConAtaqueModicado = ataques.filterNot { _.es(ataque) }.::(nuevoAtaque )
    copy( ataques = nuevaListaConAtaqueModicado ) 
  
  }

  def realizarActividad(actividad: Actividad): Pokemon = {
    val pokemonDespuesDeRealizarActivdad = estado match {
      case pokemonKO : KO => throw new NoPuedeRealizarActividadPorKO
      case dormido: Dormido => copy(estado = dormido.ignorasteActividad)  
      case _ => actividad match {

        case realizarAtaque: RealizarAtaque  => realizarAtaque(this)
       
        case levantarPesas: LevantarPesas => especie.tipoPrincipal match {
          case _: Fantasma => throw new NoPuedeLevantarPesas
          case _ => if (levantarPesas.cantidadKilos <= fuerza * 10)
            estado match {
              case _: Paralizado => copy(estado = new KO)
              case _ => especie.tipoPrincipal match {
                case _: Lucha => ganarExperiencia(levantarPesas.cantidadKilos * 2)
                case _ => especie.tipoSecundario match {
                  case _: Lucha => ganarExperiencia(levantarPesas.cantidadKilos * 2)
                  case _        => ganarExperiencia(levantarPesas.cantidadKilos)
                }
              }
            }
          else
            copy(estado = new Paralizado)
        }
        
        case nadar: Nadar => nadar(this)
          
        case aprenderAtaque: AprenderAtaque => aprenderAtaque.ataque match {
          case attack if (especie.esAfin(attack.tipo)) => aprendeAtaque(attack)
          case _                                       => copy(estado = new KO)
        }
        case usarPiedra: Actividad.UsarPiedra => usaPiedra(usarPiedra.piedra)
        case _: UsarPocion                    => usaPocion()
        case _: UsarAntidoto                  => usaAntidoto()
        case _: UsarEther                     => usaEther()
        case _: ComerHierro                   => comeHierro()
        case _: ComerCalcio                   => comeCalcio()
        case _: ComerZinc                     => comeZinc()
        case _: Descansar                     => descansa()
        case _: FingirIntercambio             => fingirIntercambio()

        //case otras activiidades
      }

    }
    
    if (! pokemonDespuesDeRealizarActivdad.estadoValido()) throw new EstadoInvalido() // Por que hacer un if en vez de un match al principio??
    
    pokemonDespuesDeRealizarActivdad
  }

}