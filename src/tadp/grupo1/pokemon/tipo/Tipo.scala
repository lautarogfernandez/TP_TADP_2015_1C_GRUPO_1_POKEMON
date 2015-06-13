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
    tipo.getClass()==this.getClass() // TODO no hace falta hacer esto. Porque al definir al tipo como Case class ya te redefine el equals por los atributos
  }  
  
}