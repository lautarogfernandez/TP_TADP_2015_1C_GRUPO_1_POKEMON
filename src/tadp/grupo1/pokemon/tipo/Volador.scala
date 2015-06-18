package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Volador extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Planta => true
      case Lucha => true
      case Bicho => true
      case _ => false
    }
  }
  
}