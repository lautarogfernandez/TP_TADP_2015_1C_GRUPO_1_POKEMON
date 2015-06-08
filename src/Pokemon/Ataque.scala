package Pokemon

import Pokemon.Tipo._

/**
 * @author usuario
 */
class Ataque (val tipo:Tipo, var puntosAtaque:Int, val efectoSecundario:Unit) {//ver el tipo de efecto secundario(le pongo Unit asi salta el warning)
  
  def bajaPA(){
    puntosAtaque-=1
  }
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon)={
    
  }
  
}