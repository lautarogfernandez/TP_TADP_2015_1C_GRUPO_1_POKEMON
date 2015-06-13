package tadp.grupo1.pokemon.estado

/**
 * @author usuario
 */
case class Dormido(acividadesQuePuedeIgnorar : Int = 3) extends Estado{
  
  def ignorasteActividad () = {
   acividadesQuePuedeIgnorar  match {
     case 1 => new EstadoNormal
     case valorActual => new Dormido(valorActual - 1)
   }
  }
  
}