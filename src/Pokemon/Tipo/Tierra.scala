package Pokemon.Tipo

/**
 * @author usuario
 */
class Tierra extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Fuego => true
      case _:Electrico => true
      case _:Veneno => true
      case _:Roca => true
      case _ => false
    }
  }
  
}