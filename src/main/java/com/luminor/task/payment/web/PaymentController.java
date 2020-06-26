package com.luminor.task.payment.web;

import com.luminor.task.payment.db.repository.AllowedTypeCurrencyRepository;
import com.luminor.task.payment.db.repository.PaymentTypeRepository;
import com.luminor.task.payment.helper.Security;
import com.luminor.task.payment.payment.CancelFeeServiceImpl;
import com.luminor.task.payment.payment.PaymentFormData;
import com.luminor.task.payment.payment.PaymentServiceImpl;
import com.luminor.task.payment.web.rest.request.CancelPaymentRequest;
import com.luminor.task.payment.web.rest.request.CreatePaymentRequest;
import com.luminor.task.payment.web.rest.response.CancelPaymentResponse;
import com.luminor.task.payment.web.rest.response.CreatePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {
    PaymentTypeRepository paymentTypeRepository;
    AllowedTypeCurrencyRepository allowedTypeCurrencyRepository;
    PaymentServiceImpl paymentService;
    CancelFeeServiceImpl cancelFeeService;

    @Autowired
    public PaymentController(
        PaymentTypeRepository paymentTypeRepository,
        AllowedTypeCurrencyRepository allowedTypeCurrencyRepository,
        PaymentServiceImpl paymentService,
        CancelFeeServiceImpl cancelFeeService
    ) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.allowedTypeCurrencyRepository = allowedTypeCurrencyRepository;
        this.paymentService = paymentService;
        this.cancelFeeService = cancelFeeService;
    }

    @GetMapping("")
    public String homepage(Model model) {
        model.addAttribute("allowedPaymentOptions", allowedTypeCurrencyRepository.findAll());
        model.addAttribute("paymentFormData", new PaymentFormData());
        model.addAttribute("existingPayments", paymentService.getExistingPayments(Security.getLoggedInUser().getUsername()));
        model.addAttribute("cancelFeeService", cancelFeeService);

        return "welcome";
    }

    @PostMapping("create/payment")
    public String createPayment(
        @ModelAttribute("paymentFormData") PaymentFormData paymentFormData,
        Model model
    ) {
        CreatePaymentResponse result = paymentService.createPayment(CreatePaymentRequest.createFromFormData(paymentFormData));

        if (result.getStatus().equals("fail")) {
            model.addAttribute("errorMessage", result.getErrorMessage());

            return this.homepage(model);
        }

        return "redirect:/";
    }

    @PostMapping("cancel/payment")
    public String cancelPayment(
        @RequestParam("paymentId") String paymentId,
        @ModelAttribute("paymentFormData") PaymentFormData paymentFormData,
        Model model
    ) {
        CancelPaymentResponse result =
            paymentService.cancelPayment(
                CancelPaymentRequest.factory(paymentId)
            );

        if (result.getStatus().equals("fail")) {
            model.addAttribute("errorMessage", result.getErrorMessage());

            return this.homepage(model);
        }

        return "redirect:/";
    }
}
