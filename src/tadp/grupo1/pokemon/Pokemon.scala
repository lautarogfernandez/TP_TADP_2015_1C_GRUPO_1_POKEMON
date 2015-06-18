package tadp.grupo1.pokemon

import tadp.grupo1.pokemon.estado._
import tadp.grupo1.pokemon.genero._
import tadp.grupo1.pokemon.condicion_evolucion._
import Actividad._
import tadp.grupo1.pokemon.tipo._

/**
 * @author usuario
 */
case class Pokemon(val especie: Especie, val genero: Genero, val pesoBase: Int, val energiaMaximaBase: Int, val energia: Int, val fuerzaBase: Int,
                   val velocidadBase: Int, val ataques: List[AtaquePokemon],
                   val estado: Estado = new EstadoNormal, val experiencia: Long = 0) { //Hay que controlar que cumpla el peso de la especie  
  
   def nivel()={
    especie.queNivelSoy(experiencia)
   }   
  
  def pesoAdquirido()={
    especie.incrementoPeso*(nivel-1)
  }
  
  def fuerzaAdquirida()={
    especie.incrementoFuerza*(nivel-1)
  }
  
  def velocidadAdquirida()={
    especie.incrementoVelocidad*(nivel-1)
  }
  
  def energiaMaximaAdquirida()={
    especie.incrementoEnergiaMaxima*(nivel-1)
  }
  
  def peso()={
    pesoBase+pesoAdquirido()
  }  
  
  def energiaMaxima()={
    energiaMaximaBase+energiaMaximaAdquirida()
  }  
  
  def velocidad()={
    velocidadBase+velocidadAdquirida()
  }  
  
  def fuerza()={
    fuerzaBase+fuerzaAdquirida()
  }    
    
  def ganarExperiencia(cantidad: Int): Pokemon = {

    val pokemonConMasExperiencia = copy(experiencia = experiencia + cantidad)

    especie.evolucioname(pokemonConMasExperiencia)
    
  }

  def cambiarEstado(nuevoEstado: Estado): Pokemon = {
    copy(estado = nuevoEstado)
  }

//  def subiNivel(incrementoPeso: Int, incrementoEnergiaMaxima: Int, incrementoFuerza: Int, incrementoVelocidad: Int): Pokemon = {
//    //var nuevoPokemon = copy(nivel = nivel + 1)
//
//    nuevoPokemon = especie.evolucioname(nuevoPokemon)
//    nuevoPokemon = especie.subirNivel(nuevoPokemon)
//    nuevoPokemon
//  }

  def intercambiar(): Unit = {
    especie.condicionEvolucion.intercambio(this)
  }

  def evolucionar(): Pokemon = {
    val nuevaEspecie = especie.evolucion
    copy(especie = nuevaEspecie.get)
  }

  def cambiarPeso(variacion: Int) = {
    copy(pesoBase = pesoBase + variacion)
  }

  def perderEnergia(cantidad: Int) = {
    copy(energia = this.energia - cantidad)
  }

  def ganarVelocidad(cantidad: Int) = {
    copy(velocidadBase = velocidadBase + cantidad)
  }

  def subirAtributo(energiaASubir: Int = 0, fuerzaASubir: Int = 0, velocidadASubir: Int = 0): Pokemon = {
    copy(energia = energia + energiaASubir, fuerzaBase = fuerzaBase + fuerzaASubir, velocidadBase = velocidadBase + velocidadASubir)
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
      case _: UsarPiedraLunar if piedra.tipo == Lunar                     => evolucionar()
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
    this.copy(ataques = this.ataques.map(ataque => ataque.aumentarPAMaximo(2))) // TODO es correcto 
  }
  
  def descansa() : Pokemon = {
    val ataquesConPARecargados = ataques.map(ataque => ataque.reestablecerPA)
    val pokemonConAtaquesRecargados = copy(ataques = ataquesConPARecargados)
    pokemonConAtaquesRecargados.estado match {
      case _: EstadoNormal if ((energiaMaxima * 0.5) > energia) => pokemonConAtaquesRecargados.cambiarEstado(new Dormido)
      case _ => pokemonConAtaquesRecargados
    }
  }
  
  def fingirIntercambio() : Pokemon = {
    especie.sufriIntercambio(this)
  }
  
  def bajarPA(ataqueGenerico : AtaqueGenerico) = {
    
    val nuevoAtaque = dameAtaque(ataqueGenerico).bajaPA()
    val nuevaListaConAtaqueModicado = ataques.filterNot { _.es(ataqueGenerico) }.::(nuevoAtaque )
    copy( ataques = nuevaListaConAtaqueModicado ) 
  
  }

  def cambiarAtaque(ataqueViejo : AtaqueGenerico, ataqueNuevo : AtaquePokemon) = {
    
    val nuevaListaConAtaqueModicado = ataques.filterNot { _.es(ataqueViejo) }.::(ataqueNuevo)
    copy( ataques = nuevaListaConAtaqueModicado ) 
  
  }
  
  def realizarActividad(actividad: Actividad): Pokemon = {
    val pokemonDespuesDeRealizarActivdad = estado match {
      case _ : KO => throw new NoPuedeRealizarActividadPorKO
      case dormido: Dormido => copy(estado = dormido.ignorasteActividad)  
      case _ => actividad(this)
      /* match {
        case realizarAtaque: RealizarAtaque  => realizarAtaque(this)       
        case levantarPesas: LevantarPesas => levantarPesas(this)        
        case nadar: Nadar => nadar(this)          
        case aprenderAtaque: AprenderAtaque => aprenderAtaque(this)        
        case usarPiedra: Actividad.UsarPiedra => usarPiedra(this)
        case _: UsarPocion                    => usaPocion()
        case _: UsarAntidoto                  => usaAntidoto()
        case _: UsarEther                     => usaEther()
        case _: ComerHierro                   => comeHierro()
        case _: ComerCalcio                   => comeCalcio()
        case _: ComerZinc                     => comeZinc()
        case _: Descansar                     => descansa()
        case _: FingirIntercambio             => fingirIntercambio()
      }
*/
    }
    
    if (! pokemonDespuesDeRealizarActivdad.estadoValido()) throw new EstadoInvalido() // Por que hacer un if en vez de un match al principio??
    
    pokemonDespuesDeRealizarActivdad
  }

}