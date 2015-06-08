package Pokemon

import Pokemon.Tipo._

/**
 * @author usuario
 */
class Ataque (val tipo:Tipo, var puntosAtaqueMaximo:Int){//, val efectoSecundario:Unit) {//ver el tipo de efecto secundario(le pongo Unit asi salta el warning)
  
  var puntosAtaque=puntosAtaqueMaximo
  val efectoSecundario=0
  
  def bajaPA(){
    puntosAtaque-=1
  }
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon)={
    
  }
  
  def es(unAtaque:Ataque)={
    this==unAtaque
  }
  
}