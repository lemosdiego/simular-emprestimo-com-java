package com.simulateloan.simulateloan.domain.rules;

import com.simulateloan.simulateloan.domain.enums.loan.CreditTrack;
import com.simulateloan.simulateloan.domain.simulation.Simulation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimulateLoanRule {
    public Simulation run(BigDecimal netSalary, Integer installments){
        CreditTrack rule = CreditTrack.trackFor(netSalary);

        BigDecimal limit = rule.calculateLimit(netSalary);
        BigDecimal installmentsValue = rule.calculateInstallment(limit, installments);

        BigDecimal percentageOfSalary = installmentsValue.divide(netSalary, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        return  new Simulation(null, netSalary, rule.name(), limit, installments, installmentsValue, percentageOfSalary);
    }
}
