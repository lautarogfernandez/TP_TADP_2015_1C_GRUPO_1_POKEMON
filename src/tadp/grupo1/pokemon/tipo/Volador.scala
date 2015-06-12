package tadp.grupo1.pokemon.tipo

/**
 * @author usuario
 */
class Volador extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case _:Planta => true
      case _:Lucha => true
      case _:Bicho => true
      case _ => false
    }
  }
  
}