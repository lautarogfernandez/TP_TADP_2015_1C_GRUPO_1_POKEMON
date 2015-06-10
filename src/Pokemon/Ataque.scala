package Pokemon

import Pokemon.Tipo._

/**
 * @author usuario
 */

class AtaqueGenerico (val tipo:Tipo, var puntosAtaqueMaximo:Int, val efectoSecundario:Unit) {//ver el tipo de efecto secundario(le pongo Unit porque si)
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon)={
    
  }
  
}


class AtaquePokemon (val ataqueGenerico:AtaqueGenerico){
  
  var puntosAtaque=ataqueGenerico.puntosAtaqueMaximo
  
  def bajaPA(){
    puntosAtaque-=1
  }
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon)={
    ataqueGenerico.aplicarEfectoSecundarioA(pokemon)
  }  
  
  def es(unAtaque:AtaqueGenerico)={
    ataqueGenerico==unAtaque
  }
}


//class Ataque (val tipo:Tipo, var puntosAtaqueMaximo:Int){//, val efectoSecundario:Unit) {//ver el tipo de efecto secundario(le pongo Unit asi salta el warning)
//  
//  var puntosAtaque=puntosAtaqueMaximo
//  val efectoSecundario=0
//  
//  def bajaPA(){
//    puntosAtaque-=1
//  }
//  
//  def aplicarEfectoSecundarioA(pokemon:Pokemon)={
//    
//  }
//  
//  def es(unAtaque:Ataque)={
//    this==unAtaque
//  }
//  
//}