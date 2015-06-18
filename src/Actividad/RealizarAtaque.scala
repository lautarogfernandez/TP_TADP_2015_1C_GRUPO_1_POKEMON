package Actividad

import tadp.grupo1.pokemon.AtaqueGenerico
import tadp.grupo1.pokemon.Pokemon
import tadp.grupo1.pokemon.NoTieneElAtaque
import tadp.grupo1.pokemon.NoTieneMasPA
import tadp.grupo1.pokemon.tipo._
import tadp.grupo1.pokemon.genero.Macho
import tadp.grupo1.pokemon.genero.Hembra
/**
 * @author usuario
 */
case class RealizarAtaque(val ataque:AtaqueGenerico) extends Actividad {
  
   override def apply(pokemon: Pokemon) = {   
      (ataque, ataque.tipo, pokemon, pokemon.especie.tipoPrincipal, pokemon.especie.tipoSecundario, pokemon.genero) match {
       case (ataque, _ , pokemon, _ , _, _) if (!pokemon.tieneAtaque(ataque)) => throw new NoTieneElAtaque
       case (ataque, _ , pokemon, _ , _, _) if (!pokemon.leQuedanAtaquesDe(ataque)) => throw new NoTieneMasPA
       case (ataque, Dragon , pokemon, _ , _, _) => bajarPAGanarExperienciaYAplicarEfectoSecundario(pokemon, ataque, 80)    
       case (ataque, tipoAtaque , pokemon, tipoPrincipalPokemon , _, _) if(tipoAtaque == tipoPrincipalPokemon) => bajarPAGanarExperienciaYAplicarEfectoSecundario(pokemon, ataque, 50)                                                                                                                          
       case (ataque, tipoAtaque , pokemon, _ , tipoSecundarioPokemon, Macho) if(tipoAtaque == tipoSecundarioPokemon) =>  bajarPAGanarExperienciaYAplicarEfectoSecundario(pokemon, ataque, 20)
       case (ataque, tipoAtaque , pokemon, _ , tipoSecundarioPokemon, Hembra) if(tipoAtaque == tipoSecundarioPokemon) => bajarPAGanarExperienciaYAplicarEfectoSecundario(pokemon, ataque, 40)                      
      }
  }
   
   def bajarPAGanarExperienciaYAplicarEfectoSecundario(pokemon : Pokemon, ataque : AtaqueGenerico, experienciaAIncrementar : Int) : Pokemon = { 
     ataque.aplicarEfectoSecundarioA(pokemon.bajarPA(ataque).ganarExperiencia(experienciaAIncrementar))
   }
       
}