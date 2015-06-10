package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Roca extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Fuego => true
      case _:Hielo => true
      case _:Volador => true
      case _:Bicho => true
      case _ => false
    }
  }
  
}