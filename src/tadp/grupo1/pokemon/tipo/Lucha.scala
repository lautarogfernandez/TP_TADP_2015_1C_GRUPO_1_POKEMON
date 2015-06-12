package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Lucha extends Tipo {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Normal => true
      case _:Hielo => true
      case _:Roca => true
      case _ => false
    }
  }
  
}