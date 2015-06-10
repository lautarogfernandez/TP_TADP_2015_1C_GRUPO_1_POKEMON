package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
abstract class Tipo {
  
  def leGanaA(tipo:Tipo):Boolean
  
  def pierdeContra(tipo:Tipo):Boolean={
    tipo.leGanaA(this)
  }
  
def ==(tipo:Tipo):Boolean={//redefino el igual
    tipo.getClass()==this.getClass()
  }  
  
}