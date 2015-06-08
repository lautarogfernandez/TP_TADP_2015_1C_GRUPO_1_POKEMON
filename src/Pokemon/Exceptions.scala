package Pokemon

/**
 * @author usuario
 */
class EstadoInvalido extends RuntimeException
class NoPuedeRealizarActividad extends RuntimeException
class NoTieneElAtaque extends NoPuedeRealizarActividad
class NoTieneMasPA extends NoPuedeRealizarActividad