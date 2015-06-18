package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Fantasma extends Tipo {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Fantasma => true
      case Psiquico => true
      case _ => false
    }
  }
  
}