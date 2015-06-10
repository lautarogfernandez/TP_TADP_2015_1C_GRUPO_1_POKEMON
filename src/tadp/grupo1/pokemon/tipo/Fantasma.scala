package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Fantasma extends Tipo {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Fantasma => true
      case _:Psiquico => true
      case _ => false
    }
  }
  
}