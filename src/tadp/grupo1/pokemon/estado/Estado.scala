package tadp.grupo1.pokemon.estado

/**
 * @author usuario
 */
trait Estado {}


case class Dormido(acividadesQuePuedeIgnorar : Int = 3) extends Estado{
  
  def ignorasteActividad () = {
   acividadesQuePuedeIgnorar  match {
     case 1 => new EstadoNormal
     case valorActual => new Dormido(valorActual - 1)
   }
  }
  
}


class Envenenado extends Estado{}//podrian ser objects


class EstadoNormal extends Estado{}


class KO extends Estado{}


class Paralizado extends Estado{}