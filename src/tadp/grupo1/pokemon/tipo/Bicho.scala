package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Bicho extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Planta => true
      case Psiquico => true
      case _ => false
    }
  }
  
}