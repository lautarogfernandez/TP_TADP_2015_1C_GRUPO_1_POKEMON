package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Planta extends Tipo  {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Agua => true
      case Tierra => true
      case Roca => true
      case _ => false
    }
  }
  
}