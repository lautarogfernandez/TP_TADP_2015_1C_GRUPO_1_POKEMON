package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Bicho extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Planta => true
      case _:Psiquico => true
      case _ => false
    }
  }
  
}