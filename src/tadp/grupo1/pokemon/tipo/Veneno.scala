package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Veneno extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Planta => true
      case _ => false
    }
  }
  
}