package org.example.methods;

import java.util.UUID;

public class MyUuidApp {
    public String correlationId() {
        UUID correlationId = UUID.randomUUID();
        return correlationId.toString();
    }
}
