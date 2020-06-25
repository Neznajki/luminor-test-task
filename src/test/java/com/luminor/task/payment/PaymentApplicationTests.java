package com.luminor.task.payment;

import com.luminor.task.payment.web.ClientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentApplicationTests {
    @Autowired
    private ClientController clientController;

    @Test
    void contextLoads() {
        assertThat(clientController).isNotNull();
    }

}
