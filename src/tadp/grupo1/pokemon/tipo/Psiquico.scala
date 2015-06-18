package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Psiquico extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Lucha => true
      case Volador => true
      case _ => false
    }
  }
  
}