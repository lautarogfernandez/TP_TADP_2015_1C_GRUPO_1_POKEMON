package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Electrico extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Agua => true
      case Volador => true
      case _ => false
    }
  }
  
}