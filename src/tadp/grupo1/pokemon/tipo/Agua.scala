package tadp.grupo1.pokemon.tipo

import org.omg.CORBA.Object


/**
 * @author usuario
 */

object Agua extends Tipo{
  
  def leGanaA(tipo:Tipo):Boolean={
    tipo match{
      case Fuego => true
      case Tierra => true
      case Roca => true
      case _ => false
    }
  }  
  
}