package org.example.methods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Map;

/**
 * A classe Body recebe o método body, que é
 * responsável por montar e retornar o corpo
 * do JSON que será enviado na requisição.
 */

public class Body {
    MyUuidApp correlationId = new MyUuidApp();
    Dotenv dotenv = Dotenv.load();
    String webhook = dotenv.get("WEBHOOK");

    /**
     * O JSON tem valores fixos preenchidos, os quais não devem
     * ser alterados pelo usuário ao chamar um pagamento. Somen
     * te as chaves que se referem a dados do tipo de pagamento
     * serão dinânicos, ou seja, o usuário vai preenche-los ao
     * chamar o pagamento. Para esses, o método body recebe parâ
     * metros. Ex: value, paymentMethod, paymentType, paymentMethodSubType.
     */

    public String body(double value, String paymentMethod, String paymentType, String paymentMethodSubType) throws JsonProcessingException {
        Map<String, Object> json = Map.of(
                "type", "INPUT",
                "origin", "APP MAURICIO",
                "data", Map.of(
                        "callbackUrl", webhook,
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
