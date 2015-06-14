package tadp.grupo1.pokemon

import tadp.grupo1.pokemon.tipo.Tipo

case class AtaqueGenerico (val tipo:Tipo, val puntosAtaqueMaximo:Int, aplicarEfectoSecundarioA: Pokemon => Pokemon = { pokemon => pokemon }) {//ver el tipo de efecto secundario(le pongo Unit porque si)
   
}


case class AtaquePokemon (ataqueGenerico:AtaqueGenerico, puntosAtaqueMaximoDelPokemon : Int, puntosAtaque :Int ){
  
  def bajaPA() = {
    copy(puntosAtaque = puntosAtaque - 1)
  }
  
  def aplicarEfectoSecundarioA(pokemon:Pokemon) : Pokemon ={
    ataqueGenerico.aplicarEfectoSecundarioA(pokemon)
  }  
  
  def es(unAtaque:AtaqueGenerico)={
    ataqueGenerico==unAtaque
  }
  
  def aumentarPAMaximo(aumento:Int)={
    copy(puntosAtaqueMaximoDelPokemon = puntosAtaqueMaximoDelPokemon + aumento) 
  }
  
  def reestablecerPA()={
    copy(puntosAtaque = puntosAtaqueMaximoDelPokemon)
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