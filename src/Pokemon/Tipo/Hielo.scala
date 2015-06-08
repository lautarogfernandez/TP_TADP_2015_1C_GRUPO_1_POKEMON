package Pokemon.Tipo

/**
 * @author usuario
 */
class Hielo extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Planta => true
      case _:Tierra => true
      case _:Volador => true
      case _:Dragon => true
      case _ => false
    }
  }
  
}