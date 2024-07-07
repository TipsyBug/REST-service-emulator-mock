package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private static final BigDecimal LIMIT_8 = new BigDecimal("2000.00");
    private static final BigDecimal LIMIT_9 = new BigDecimal("1000.00");
    private static final BigDecimal DEFAULT_LIMIT = new BigDecimal("10000.00");

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDTO> postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency;

            switch (firstDigit) {
                case '9':
                    maxLimit = LIMIT_9;
                    currency = "EU";
                    break;
                case '8':
                    maxLimit = LIMIT_8;
                    currency = "US";
                    break;
                default:
                    maxLimit = DEFAULT_LIMIT;
                    currency = "RUB";
                    break;
            }

            BigDecimal balance = getRandomBalance(maxLimit);

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.info("********** RequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("********** ResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            log.error("Ошибка обработки запроса", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private BigDecimal getRandomBalance(BigDecimal maxLimit) {
        Random random = new Random();
        BigDecimal randomBalance = maxLimit.multiply(BigDecimal.valueOf(random.nextDouble()));
        return randomBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
