package com.luminor.task.payment.web.rest;

import com.luminor.task.payment.payment.InvalidPaymentException;
import com.luminor.task.payment.payment.PaymentQueryServiceImpl;
import com.luminor.task.payment.payment.PaymentServiceImpl;
import com.luminor.task.payment.web.rest.request.AmountFilterRequest;
import com.luminor.task.payment.web.rest.request.CancelPaymentRequest;
import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import com.luminor.task.payment.web.rest.request.UUIDFilterRequest;
import com.luminor.task.payment.web.rest.response.CancelPaymentResponse;
import com.luminor.task.payment.web.rest.response.CreatePaymentResponse;
import com.luminor.task.payment.web.rest.response.ExistingPaymentDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class Payment {
    PaymentServiceImpl paymentService;
    PaymentQueryServiceImpl paymentQueryService;

    @Autowired
    public Payment(
        PaymentServiceImpl paymentService,
        PaymentQueryServiceImpl paymentQueryService
    ) {
        this.paymentService = paymentService;
        this.paymentQueryService = paymentQueryService;
    }

    @PutMapping(
        value = "rest-api/create/payment",
        produces = {"application/json"}
    )
    public CreatePaymentResponse createPayment(@RequestBody CreatePaymentRequest createPaymentRequest) {
        return paymentService.createPayment(createPaymentRequest);
    }

    @PutMapping(
        value = "rest-api/cancel/payment",
        produces = {"application/json"}
    )
    public CancelPaymentResponse cancelPayment(@RequestBody CancelPaymentRequest cancelPaymentRequest) {
        return paymentService.cancelPayment(cancelPaymentRequest);
    }

    @GetMapping(
        value = "rest-api/get/by/uuid",
        produces = {"application/json"}
    )
    public ExistingPaymentDataResponse getDataByUUID(@RequestBody UUIDFilterRequest filterRequest) throws InvalidPaymentException {
        return paymentQueryService.getByUUID(filterRequest.getId());
    }

    @GetMapping(
        value = "rest-api/canceled/payments",
        produces = {"application/json"}
    )
    public Collection<ExistingPaymentDataResponse> getCanceledPayments(@RequestBody AmountFilterRequest amountFilter) {
        return paymentQueryService.getCanceledItems(amountFilter);
    }

    @GetMapping(
        value = "rest-api/not/canceled/payments",
        produces = {"application/json"}
    )
    public Collection<ExistingPaymentDataResponse> getNotCanceledPayments(@RequestBody AmountFilterRequest amountFilter) {
        return paymentQueryService.getNotCanceledItems();
    }
}
