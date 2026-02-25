package com.simulateloan.simulateloan.application.controllers;

import com.simulateloan.simulateloan.application.controllers.dto.request.SimulateLoanRequest;
import com.simulateloan.simulateloan.application.controllers.dto.response.PlanoResponse;
import com.simulateloan.simulateloan.application.controllers.dto.response.SimulateLoanResponse;
import com.simulateloan.simulateloan.domain.rules.CalculatingNetSalaryRule;
import com.simulateloan.simulateloan.domain.rules.SimulateLoanRule;
import com.simulateloan.simulateloan.domain.client.Client;
import com.simulateloan.simulateloan.domain.enums.loan.CreditTrack;
import com.simulateloan.simulateloan.domain.simulation.Simulation;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/simulations")
public class SimulateLoanController {

    @PostMapping
    public SimulateLoanResponse simulate(@RequestBody SimulateLoanRequest request) {
        // EXATAMENTE seu use case atual!
        Client client = new Client(request.salarioBruto(), BigDecimal.ZERO);
        CalculatingNetSalaryRule netSalaryRule = new CalculatingNetSalaryRule();
        Client clientNetSalary = netSalaryRule.run(client);
        BigDecimal netSalary = clientNetSalary.getNetSalary();

        SimulateLoanRule loanRule = new SimulateLoanRule();
        CreditTrack track = CreditTrack.trackFor(netSalary);

        List<Integer> options = track.getInstallmentOptions();
        List<Simulation> simulations = options.stream()
                .map(parcelas -> loanRule.run(netSalary, parcelas))
                .toList();

        // DTO response (sem banco ainda)
        List<PlanoResponse> planos = simulations.stream()
                .map(s -> new PlanoResponse(
                        s.installments, s.installmentsValue, s.percentageOfSalary, track.interestRate.toString()))
                .toList();

        return new SimulateLoanResponse(
                request.nome(),
                netSalary,
                track.name(),
                track.calculateLimit(netSalary),
                planos
        );
    }
}
