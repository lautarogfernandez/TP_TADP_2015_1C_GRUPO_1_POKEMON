package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Psiquico extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Lucha => true
      case _:Volador => true
      case _ => false
    }
  }
  
}