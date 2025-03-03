/**
 * Essa classe é responsável por gerar um UUID para ser
 * utilizado como CorrelationId. O uuid gerado é único e
 * aleatório, mudando a cada requisição.
 */
package org.example.methods;
import java.util.UUID;

public class MyUuidApp {
    public String correlationId() {
        UUID correlationId = UUID.randomUUID();
        return correlationId.toString();
    }
}
