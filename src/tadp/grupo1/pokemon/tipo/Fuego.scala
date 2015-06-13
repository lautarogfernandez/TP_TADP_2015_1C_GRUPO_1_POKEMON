package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Fuego extends Tipo  {
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Planta => true
      case _:Hielo => true
      case _:Bicho => true
      case _ => false
    }
  }
  
}