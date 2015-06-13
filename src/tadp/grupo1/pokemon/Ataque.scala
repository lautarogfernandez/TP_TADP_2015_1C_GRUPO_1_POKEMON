package tadp.grupo1.pokemon

import tadp.grupo1.pokemon.tipo.Tipo

class AtaqueGenerico (val tipo:Tipo, var puntosAtaqueMaximo:Int, val efectoSecundario:Unit) {//ver el tipo de efecto secundario(le pongo Unit porque si)
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon) : Pokemon={
    pokemon // TODO Hacer lo del Efecto secundario
  }
  
}


class AtaquePokemon (val ataqueGenerico:AtaqueGenerico){
  
  var puntosAtaqueMaximoDelPokemon=ataqueGenerico.puntosAtaqueMaximo
  var puntosAtaque=puntosAtaqueMaximoDelPokemon
  
  def bajaPA(){
    puntosAtaque-=1
  }
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon) : Pokemon ={
    ataqueGenerico.aplicarEfectoSecundarioA(pokemon)
  }  
  
  def es(unAtaque:AtaqueGenerico)={
    ataqueGenerico==unAtaque
  }
  
  def aumentarPAMaximo(aumento:Int)={
    puntosAtaqueMaximoDelPokemon+=aumento
  }
  
  def reestablecerPA()={
    puntosAtaque=puntosAtaqueMaximoDelPokemon
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