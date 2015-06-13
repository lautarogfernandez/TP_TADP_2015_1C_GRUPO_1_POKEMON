package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Electrico extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Agua => true
      case _:Volador => true
      case _ => false
    }
  }
  
}