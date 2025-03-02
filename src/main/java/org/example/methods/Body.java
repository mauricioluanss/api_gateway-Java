package org.example.methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;

public class Body {
    MyUuidApp correlationId = new MyUuidApp();
    Dotenv dotenv = Dotenv.load();
    String webook = dotenv.get("WEBHOOK");

    public String body(double value, String paymentMethod, String paymentType, String paymentMethodSubType) throws JsonProcessingException {
        Map<String, Object> json = Map.of(
                "type", "INPUT",
                "origin", "APP MAURICIO",
                "data", Map.of(
                        "callbackUrl", webook,
                        "correlationId", correlationId.correlationId(),
                        "flow", "SYNC",
                        "automationName", "SAIPOS",
                        "receiver", Map.of(
                                "companyId", "000001",
                                "storeId", "0022",
                                "terminalId", "03"
                                    ),
                        "message", Map.of(
                                "command", "PAYMENT",
                                "value", value,
                                "paymentMethod", paymentMethod,
                                "paymentType", paymentType,
                                "paymentMethodSubType", paymentMethodSubType
                                    )
                            )
                    );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(json);
    }
}
