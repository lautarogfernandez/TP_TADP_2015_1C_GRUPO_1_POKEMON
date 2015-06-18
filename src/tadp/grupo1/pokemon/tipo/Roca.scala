package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Roca extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Fuego => true
      case Hielo => true
      case Volador => true
      case Bicho => true
      case _ => false
    }
  }
  
}