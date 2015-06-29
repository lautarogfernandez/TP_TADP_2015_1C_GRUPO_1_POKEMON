package tadp.grupo1.pokemon

import tadp.grupo1.pokemon.estado._
import tadp.grupo1.pokemon.genero._
import tadp.grupo1.pokemon.condicion_evolucion._
import Actividad._
import tadp.grupo1.pokemon.tipo._
import scala.util.Try

/**
 * @author usuario
 */
case class Pokemon(val especie: Especie, val genero: Genero, val pesoBase: Int, val energiaMaximaBase: Int, val energia: Int, val fuerzaBase: Int,
                   val velocidadBase: Int, val ataques: List[AtaquePokemon],
                   val estado: Estado = new EstadoNormal, val experiencia: BigInt = 0) { //Hay que controlar que cumpla el peso de la especie  

  def nivel() = {
    especie.queNivelSoy(experiencia)
  }

  def pesoAdquirido() = {
    especie.incrementoPeso * (nivel - 1)
  }

  def fuerzaAdquirida() = {
    especie.incrementoFuerza * (nivel - 1)
  }

  def velocidadAdquirida() = {
    especie.incrementoVelocidad * (nivel - 1)
  }

  def energiaMaximaAdquirida() = {
    especie.incrementoEnergiaMaxima * (nivel - 1)
  }

  def peso() = {
    pesoBase + pesoAdquirido()
  }

  def energiaMaxima() = {
    energiaMaximaBase + energiaMaximaAdquirida()
  }

  def velocidad() = {
    velocidadBase + velocidadAdquirida()
  }

  def fuerza() = {
    fuerzaBase + fuerzaAdquirida()
  }

  def ganarExperiencia(cantidad: BigInt): Pokemon = {

    val pokemonConMasExperiencia = copy(experiencia = experiencia + cantidad)

    especie.evolucioname(pokemonConMasExperiencia)

  }

  def cambiarEstado(nuevoEstado: Estado): Pokemon = {
    copy(estado = nuevoEstado)
  }

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

  // TODO tirar exception si no se encuentra el ataque
  def dameAtaque(ataque: AtaqueGenerico): AtaquePokemon = {
    ataques.find { attack => attack.es(ataque) }.get
  }

  def leQuedanAtaquesDe(attack: AtaqueGenerico): Boolean = {
    dameAtaque(attack).puntosAtaque > 0
  }

  def estadoValido(): Boolean = {
    (peso <= especie.pesoMaximo) && (peso > 0) //creo que hay mas, pero no se me ocurren    
  }

  def bajarPA(ataqueGenerico: AtaqueGenerico) = {

    val nuevoAtaque = dameAtaque(ataqueGenerico).bajaPA()
    val nuevaListaConAtaqueModicado = ataques.filterNot { _.es(ataqueGenerico) }.::(nuevoAtaque)
    copy(ataques = nuevaListaConAtaqueModicado)

  }

  def cambiarAtaque(ataqueViejo: AtaqueGenerico, ataqueNuevo: AtaquePokemon) = {

    val nuevaListaConAtaqueModicado = ataques.filterNot { _.es(ataqueViejo) }.::(ataqueNuevo)
    copy(ataques = nuevaListaConAtaqueModicado)

  }

  def realizarActividad(actividad: Actividad): Try[Pokemon] = {
    actividad(this)
  }

}