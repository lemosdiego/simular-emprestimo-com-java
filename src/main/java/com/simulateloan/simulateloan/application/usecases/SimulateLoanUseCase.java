package com.simulateloan.simulateloan.application.usecases;

import com.simulateloan.simulateloan.domain.client.Client;
import com.simulateloan.simulateloan.domain.enums.loan.CreditTrack;
import com.simulateloan.simulateloan.domain.rules.CalculatingNetSalaryRule;
import com.simulateloan.simulateloan.domain.rules.SimulateLoanRule;
import com.simulateloan.simulateloan.domain.simulation.Simulation;


import java.math.BigDecimal;
import java.util.List;

public class SimulateLoanUseCase {
    public void run(){
        //Salario liquido
        Client client= new Client(new BigDecimal("3621.00"), BigDecimal.ZERO);
        CalculatingNetSalaryRule netSalaryRule = new CalculatingNetSalaryRule();
        Client clientNetSalaryCalculate = netSalaryRule.run(client);
        BigDecimal netSalary = clientNetSalaryCalculate.getNetSalary();
        System.out.println("Seu salario liquido é de: R$" + netSalary);

        //Simulação de credito
        SimulateLoanRule loanRule = new SimulateLoanRule();
        CreditTrack track = CreditTrack.trackFor(netSalary);
        System.out.println("\nFAIXA: " + track.name());
        System.out.println("Valor total do empréstimo: R$" + track.calculateLimit(netSalary));

        List<Integer> options = track.getInstallmentOptions();
        System.out.println("\nCONHEÇA NOSSOS PLANOS DISPONÍVEIS:\n");

        for (Integer installments: options){
            Simulation simulation = loanRule.run(netSalary, installments);
            System.out.printf(" %d meses de R$%.2f", installments, simulation.installmentsValue);
            System.out.printf(" (juros %.1f%% ao mês., comprometimento %.1f%% salário)\n",
                    track.interestRate, simulation.percentageOfSalary);
        }
    }
}
