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
     
    // val pokemonDespuesDeGanarExpPorAtacar =  
      (ataque, ataque.tipo, pokemon, pokemon.especie.tipoPrincipal, pokemon.especie.tipoSecundario, pokemon.genero) match {
     
       case (ataque, _ , pokemon, _ , _, _) if (!pokemon.tieneAtaque(ataque)) => throw new NoTieneElAtaque
       case (ataque, _ , pokemon, _ , _, _) if (!pokemon.leQuedanAtaquesDe(ataque)) => throw new NoTieneMasPA
       case (ataque, _ : Dragon , pokemon, _ , _, _) => pokemon.bajarPA(ataque)
                                                          .ganarExperiencia(80)  // TODO bajaPA se repite 3 veces una en cada case y los case que vengan                                                   
      
    
       case (ataque, tipoAtaque , pokemon, tipoPrincipalPokemon , _, _) if(tipoAtaque == tipoPrincipalPokemon) =>  pokemon.bajarPA(ataque) //Metodo que reciba que haga estas dos cosas y aplique efecto secundario
                                                                                                                          .ganarExperiencia(50)
                                                                                                                          
       case (ataque, tipoAtaque , pokemon, _ , tipoSecundarioPokemon, genero : Macho) if(tipoAtaque == tipoSecundarioPokemon) =>  pokemon.bajarPA(ataque)
                                                                                                                                         .ganarExperiencia(20)
       
       case (ataque, tipoAtaque , pokemon, _ , tipoSecundarioPokemon, _ : Hembra) if(tipoAtaque == tipoSecundarioPokemon) => pokemon.bajarPA(ataque)
                                                                                                                                    .ganarExperiencia(40)
          
               
            }
     
     // TODO opcion para no repetir pokemonDespuesDeGanarExpPorAtacar.bajarPA(ataque)
    // ataque.aplicarEfectoSecundarioA(pokemonDespuesDeGanarExpPorAtacar)
     
   }
    
}