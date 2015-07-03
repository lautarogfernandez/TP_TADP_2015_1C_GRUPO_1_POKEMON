package Pokemon

import org.junit.Assert._

import scala.util.{Failure, Success, Try}

/**
 *
 */
object TryInsideObjectObtainer {

  implicit def obtenerObjetoAdentroDelTry[T](unTryConAlgoAdentro :  Try[T]) = T {
    unTryConAlgoAdentro match {
      case Success(algo : T) => algo
      case Failure(exception) => fail("Se esperaba un Success con algo pero se recibio esta excepcion " + exception.getMessage)
    }
  }

}
