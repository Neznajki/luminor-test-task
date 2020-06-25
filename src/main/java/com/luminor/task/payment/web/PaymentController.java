package com.luminor.task.payment.web;

import com.luminor.task.payment.db.repository.AllowedTypeCurrencyRepository;
import com.luminor.task.payment.db.repository.PaymentTypeRepository;
import com.luminor.task.payment.payment.PaymentFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {
    PaymentTypeRepository paymentTypeRepository;
    AllowedTypeCurrencyRepository allowedTypeCurrencyRepository;

    @Autowired
    public PaymentController(
        PaymentTypeRepository paymentTypeRepository,
        AllowedTypeCurrencyRepository allowedTypeCurrencyRepository
    ) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.allowedTypeCurrencyRepository = allowedTypeCurrencyRepository;
    }

    @GetMapping("")
    public String homepage(Model model) {
        model.addAttribute("allowedPaymentOptions", allowedTypeCurrencyRepository.findAll());
        model.addAttribute("paymentFormData", new PaymentFormData());

        return "welcome";
    }
}
