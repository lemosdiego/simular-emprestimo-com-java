//package com.simulateloan.simulateloan.application.usecases;
//
//import com.simulateloan.simulateloan.domain.client.Client;
//import com.simulateloan.simulateloan.domain.client.rules.CalculatingNetSalaryRule;
//
//import java.math.BigDecimal;
//
//public class SimulateLoanSalaryUseCase {
//    public void run(){
//        Client client= new Client(new BigDecimal("1621.00"), BigDecimal.ZERO);
//        CalculatingNetSalaryRule rule= new CalculatingNetSalaryRule();
//        Client result = rule.run(client);
//
//        System.out.println("Salario Liquido: R$" + result.getNetSalary());
//    }
//
//}
//Orquesta:
    //1-cria cliente
    //2-calcula salario liquido regra
    //3-calcula o credico com base no salario
    //4-gera a simulação completa, valor+quantidadeparcelas+valor