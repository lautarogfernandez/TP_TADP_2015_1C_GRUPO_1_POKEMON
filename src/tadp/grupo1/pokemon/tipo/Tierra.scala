package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Tierra extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Fuego => true
      case Electrico => true
      case Veneno => true
      case Roca => true
      case _ => false
    }
  }
  
}