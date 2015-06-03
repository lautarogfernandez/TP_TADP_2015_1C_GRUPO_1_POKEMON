package Pokemon

import Pokemon.Estado.Normal
import Pokemon.Genero.Genero

/**
 * @author usuario
 */
class Pokemon(val Genero: Genero,var Especie: Especie ) {  
  var Nivel=1
  var Experiencia=0 
  var Energia: Int
  var EnergiaMaxima: Int
  var Peso: Int
  var Fuerza: Int
  var Velocidad: Int
  var Estado= new Normal
}