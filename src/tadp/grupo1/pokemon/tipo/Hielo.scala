package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Hielo extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Planta => true
      case Tierra => true
      case Volador => true
      case Dragon => true
      case _ => false
    }
  }
  
}