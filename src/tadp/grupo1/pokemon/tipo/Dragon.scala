package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
object Dragon extends Tipo {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Dragon => true
      case _ => false
    }
  }
  
}