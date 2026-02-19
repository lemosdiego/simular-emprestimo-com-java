package com.simulateloan.simulateloan.domain.rules;

import com.simulateloan.simulateloan.domain.entities.client.Client;
import com.simulateloan.simulateloan.domain.salary.InssTrack;

import java.math.BigDecimal;

public class CalculatingNetSalaryRule {
    public Client run(Client client){
        BigDecimal grossSalary = client.getGrossSalary();
        InssTrack track = InssTrack.trackFor(grossSalary);
        BigDecimal inss = track.calculate(grossSalary);
        BigDecimal netSalary = grossSalary.subtract(inss);

        client.setNetSalary(netSalary);

        return client;
    }
}
