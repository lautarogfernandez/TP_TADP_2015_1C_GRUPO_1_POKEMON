package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Lucha extends Tipo {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Normal => true
      case Hielo => true
      case Roca => true
      case _ => false
    }
  }
  
}