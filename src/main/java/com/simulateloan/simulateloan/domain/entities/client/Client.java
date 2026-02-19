package com.simulateloan.simulateloan.domain.entities.client;

import java.math.BigDecimal;

public class Client {

    private String name;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;


    public Client(BigDecimal grossSalary, BigDecimal netSalary) {
        this.name = name;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
    }
    public String getName() {
        return name;
    }
    public BigDecimal getGrossSalary() {
        return grossSalary;
    }
    public BigDecimal getNetSalary() {
        return netSalary;
    }
    public void setNetSalary(BigDecimal netSalary){
        this.netSalary = netSalary;
    }
}
