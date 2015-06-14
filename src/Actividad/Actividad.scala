package Actividad

import tadp.grupo1.pokemon.Pokemon

/**
 * @author usuario
 */
abstract class Actividad extends Function1[Pokemon, Pokemon]  {
  
  
  // TODO ver de hacer un template method: Por ejmplo algo asi:
  /*
   * def apply(pokemon : Pokemon) = { 
   * 
   *    evaluarEstados() //matcher definido aca en Actividad con la logica de dormido y KO
   *    abstract applyActividad() // el metodo que antes hacia de apply que tienen que redefinir las actividades concretas
   *    evaluarPeso() // definido aca tambien que con lo que haciamos al final de llamar a .estadoValido()
   */
  
  def apply(pokemon : Pokemon) = { pokemon  } // TODO hecho aca para no tener que definirlo en todas las subclases 
  
}