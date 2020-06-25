package com.luminor.task.payment.web.rest;

import com.luminor.task.payment.payment.PaymentServiceImpl;
import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import com.luminor.task.payment.web.rest.response.CreatePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Payment {
    PaymentServiceImpl paymentService;

    @Autowired
    public Payment(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PutMapping(
        value = "rest-api/create/payment",
        produces = {"application/json"}
    )
    public CreatePaymentResponse home(@RequestBody CreatePaymentRequest createPaymentRequest) {
        return paymentService.createPayment(createPaymentRequest);
    }

}
