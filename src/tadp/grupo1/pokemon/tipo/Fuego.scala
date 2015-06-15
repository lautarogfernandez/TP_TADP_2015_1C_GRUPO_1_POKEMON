package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Fuego extends Tipo  {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Planta => true
      case Hielo => true
      case Bicho => true
      case _ => false
    }
  }
  
}