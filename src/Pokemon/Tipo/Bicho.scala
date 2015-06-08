package Pokemon.Tipo

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