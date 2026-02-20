package com.simulateloan.simulateloan.domain.enums.loan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CreditTrack {
    Renda_inicial(new BigDecimal("1450"), new BigDecimal("2500"), new BigDecimal("3.0"),12,18, new BigDecimal("1.8"), new BigDecimal("30") ),
    Renda_media(new BigDecimal("2501.00"), new BigDecimal("4500.00"), new BigDecimal("5.0"), 12, 24, new BigDecimal("1.5"), new BigDecimal("25")),
    Renda_alta(new BigDecimal("4501.00"), new BigDecimal("7500.00"), new BigDecimal("7.0"), 12, 30, new BigDecimal("1.2"), new BigDecimal("20")),
    Renda_premium(new BigDecimal("7501.00"), new BigDecimal("999999.99"), new BigDecimal("8.0"), 12, 60, new BigDecimal("1.0"), new BigDecimal("15"));

    public final BigDecimal minumum;
    public final BigDecimal maximum;
    public final BigDecimal multiplier;
    public final int minimumInstallments;
    public final int maximumInstallments;
    public final BigDecimal interestRate;
    public final BigDecimal percentageOfSalary;

    CreditTrack(BigDecimal minumum, BigDecimal maximum, BigDecimal multiplier, int minimumInstallments, int maximumInstallments, BigDecimal interestRate, BigDecimal percentageOfSalary){
        this.minumum = minumum;
        this.maximum = maximum;
        this.multiplier = multiplier;
        this.minimumInstallments = minimumInstallments;
        this.maximumInstallments = maximumInstallments;
        this.interestRate = interestRate;
        this.percentageOfSalary = percentageOfSalary;
    }
    // Metodo responsável em qual faixa de renda o salário se encaixa.
    public static CreditTrack trackFor(BigDecimal netSalary){
        if(netSalary.compareTo(Renda_inicial.minumum) < 0){
             System.out.println("Salario abaixo do limite minimo");
        }
        //Retorna um array com as faixas de renda, usa o filter para descobrirmos em qual faixa
        //o salario liquido se encaixa, se nao achar entao esta na renda premium.
        return Arrays.stream(values())
                .filter(range -> netSalary.compareTo(range.minumum) >= 0
                        && netSalary.compareTo(range.maximum) <= 0)
                .findFirst()
                .orElse(Renda_premium);
    }
    //Calcula o limite de credito.
    public BigDecimal calculateLimit(BigDecimal netSalary){
        return netSalary.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }
    //Verifica o numero de parcelas e retorna uma lista de opçoes de parcelamento.
    //Começa com o minimo de parcelas 12 e adiciona mais 6 de acordo com a renda que vai se encaixar.
    public List<Integer> getInstallmentOptions(){
        List<Integer> options = new ArrayList<>();
        for (int numberOfInstallments = minimumInstallments; numberOfInstallments <= maximumInstallments; numberOfInstallments += 6){
            options.add(numberOfInstallments);
        }
        return options;
    }
    //Calcula o valor da parcela aplicando os juros ao mes. soma e divide pelo numero de parcelas.
    public BigDecimal calculateInstallment(BigDecimal limit, int installments){
        BigDecimal months = BigDecimal.valueOf(installments);
        BigDecimal totalInterest = limit.multiply(interestRate.divide(BigDecimal.valueOf(100)))
                .multiply(months);
        BigDecimal totalWithInterest = limit.add(totalInterest);
        return  totalWithInterest.divide(months, 2, RoundingMode.HALF_UP);
    }
}
