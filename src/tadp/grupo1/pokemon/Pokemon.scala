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

  def ataque(ataque: AtaqueGenerico): AtaquePokemon = {
    ataques.find { attack => attack.es(ataque) }.get
  }

  def leQuedanAtaquesDe(attack: AtaqueGenerico): Boolean = {
    ataque(attack).puntosAtaque > 0
  }

  def estadoValido(): Boolean = {
    (peso <= especie.pesoMaximo) && (peso > 0) //creo que hay mas, pero no se me ocurren    
  }

  def aprendeAtaque(ataque: AtaqueGenerico): Pokemon = {
    val nuevaListAtaques = ataques.::(new AtaquePokemon(ataque))
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

  def realizarActividad(actividad: Actividad): Pokemon = {
    val pokemonDespuesDeRealizarActivdad = estado match {
      case dormido: Dormido if dormido.acividadesQuePuedeIgnorar > 0 => this//TODO acividadesQuePuedeIgnorar debe ser val pero no se como seria 
      case _ => actividad match {

        case realizarAtaque: RealizarAtaque => if (this.tieneAtaque(realizarAtaque.ataque))
          if (this.leQuedanAtaquesDe(realizarAtaque.ataque)) {
            ataque(realizarAtaque.ataque).bajaPA
            
            val pokemonDespuesDeGanarExpPorAtacar : Pokemon = realizarAtaque.ataque.tipo match {
              case _: Dragon => ganarExperiencia(80)
              case _ =>
                if (especie.esDelTipoPrincipal(realizarAtaque.ataque.tipo))
                  ganarExperiencia(50)
                else if (especie.esDelTipoSecundario(realizarAtaque.ataque.tipo))
                  genero match {
                    case hembra: Hembra => ganarExperiencia(40)
                    case macho: Macho   => ganarExperiencia(20)
                  }
                else this
                  
            }
            ataque(realizarAtaque.ataque).aplicarEfectoSecundarioA(pokemonDespuesDeGanarExpPorAtacar)
            
          } else
            throw new NoTieneMasPA //hacer error como en el de micro                                                    
        else
          throw new NoTieneElAtaque //hacer error como en el de micro

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
        case nadar: Nadar => 
          var pokemonDespuesDeNadar = this
          if ((energia - nadar.minutos) < 0) // TODO no se si hace falta porque controla el estado al final   
            new NoPuedeRealizarActividad // TODO Y si devolvemos a this en vez de tirar exception?? se necesita para el punto 4??
          else{
            especie.tipoPrincipal match {
              case _: Agua =>
                pokemonDespuesDeNadar = ganarExperiencia(200 * nadar.minutos)
                pokemonDespuesDeNadar = pokemonDespuesDeNadar.ganarVelocidad(nadar.minutos / 60)
                pokemonDespuesDeNadar = pokemonDespuesDeNadar.perderEnergia(nadar.minutos)
              case _ if ((especie.tipoPrincipal.pierdeContra(new Agua)) || especie.tipoSecundario.pierdeContra(new Agua)) => pokemonDespuesDeNadar = copy(estado = new KO)
              case _ =>
                pokemonDespuesDeNadar = ganarExperiencia(200 * nadar.minutos)
                pokemonDespuesDeNadar = pokemonDespuesDeNadar.perderEnergia(nadar.minutos)
            }
          }
          
          pokemonDespuesDeNadar
          
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