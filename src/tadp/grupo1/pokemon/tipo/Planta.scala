package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Planta extends Tipo  {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Agua => true
      case _:Tierra => true
      case _:Roca => true
      case _ => false
    }
  }
  
}