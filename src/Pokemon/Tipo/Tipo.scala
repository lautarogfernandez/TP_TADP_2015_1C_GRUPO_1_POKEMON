package Pokemon.Tipo

/**
 * @author usuario
 */
abstract class Tipo {
  
  def leGanaA(tipo:Tipo):Boolean
  
  def pierdeContra(tipo:Tipo):Boolean={
    tipo.leGanaA(this)
  }
  
}