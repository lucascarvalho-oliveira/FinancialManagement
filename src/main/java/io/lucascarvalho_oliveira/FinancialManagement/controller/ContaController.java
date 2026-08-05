package io.lucascarvalho_oliveira.FinancialManagement.controller;

import io.lucascarvalho_oliveira.FinancialManagement.service.ContaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("conta")
public class ContaController {
    private final ContaService serviceConta;

    public ContaController(ContaService serviceConta){
        this.serviceConta = serviceConta;
    }
}
