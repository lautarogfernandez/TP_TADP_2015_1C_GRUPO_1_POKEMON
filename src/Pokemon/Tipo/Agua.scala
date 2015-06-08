package Pokemon.Tipo


/**
 * @author usuario
 */
class Agua extends Tipo  {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Fuego => true
      case _:Tierra => true
      case _:Roca => true
      case _ => false
    }
  }
  
}