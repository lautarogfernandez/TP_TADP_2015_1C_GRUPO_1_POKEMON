package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Dragon extends Tipo {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Dragon => true
      case _ => false
    }
  }
  
}