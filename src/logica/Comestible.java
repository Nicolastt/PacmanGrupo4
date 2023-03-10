package logica;

import java.awt.*;

/**
 * Esta interfaz define los métodos necesarios para cualquier objeto
 * comestible en el juego. Los objetos comestibles deben ser capaces de
 * proporcionar un rectángulo de colisión.
 */
public interface Comestible {
    /**
     * Retorna el rectángulo de colisión del objeto comestible.
     *
     * @return El rectángulo de colisión del objeto comestible.
     */
    Rectangle getHitbox();
}
