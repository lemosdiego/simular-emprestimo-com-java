package com.simulateloan.simulateloan.application.usecases;

import com.simulateloan.simulateloan.application.controllers.dto.request.SimulateLoanRequest;
import com.simulateloan.simulateloan.application.controllers.dto.response.PlansResponse;
import com.simulateloan.simulateloan.application.controllers.dto.response.SimulateLoanResponse;
import com.simulateloan.simulateloan.domain.client.Client;
import com.simulateloan.simulateloan.domain.enums.loan.CreditTrack;
import com.simulateloan.simulateloan.domain.rules.CalculatingNetSalaryRule;
import com.simulateloan.simulateloan.domain.rules.SimulateLoanRule;
import com.simulateloan.simulateloan.domain.simulation.Simulation;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimulateLoanUseCase {
   //inicia o processo de simulação
    public SimulateLoanResponse process(SimulateLoanRequest request){
       //Cria o cliente recebendo o salariobruto e o salario liquido inicia com 0
        Client client = new Client(request.nome(), request.salarioBruto(), BigDecimal.ZERO);
        //Chama a regra de calcular o salario liquido
        //Calcula o salario liquido
        CalculatingNetSalaryRule netSalaryRule = new CalculatingNetSalaryRule();
        Client clientNetSalary = netSalaryRule.run(client);
        BigDecimal netSalary = clientNetSalary.getNetSalary();
        //Após pegarmos o salario liquido vamos ver a qual faixa de credito esse salario pertence
        SimulateLoanRule loanRule = new SimulateLoanRule();
        CreditTrack track = CreditTrack.trackFor(netSalary);
        //Após descobrir a faixa, vamos geras as simulações. meses/valorparcela/percentual/juros.
        //totl de tres simulações para cada quantidade de meses iniciando com 12 meses.

        //pegamos a lista de opções de parcelas 12/18...
        //Criamos uma lista de simulações rodando um for
        List<Integer> options = track.getInstallmentOptions();
        List<Simulation> simulations = new ArrayList<>();
        //para cada parcela tera uma nova opção de simulação
        for (Integer installments : options){
            //para a vaixa de salario x com essa lista de opções rodamos a simulação
            Simulation simulation = loanRule.run(netSalary, installments);
            //adiciona uma nova simulação na lista de simulações
            simulations.add(simulation);
        }

        //Monta o Joson da resposta
        //resposta vai ser uma lista de planos
        //para cada simulação cria um plano e devolve a lista
        List<PlansResponse> plans = simulations.stream()
                .map(simulation -> new PlansResponse(
                        simulation.installments,
                        simulation.installmentsValue,
                        simulation.percentageOfSalary,
                        track.interestRate.toString()
                )).toList();
        // retorna o JSON
        return new SimulateLoanResponse(
                request.nome(),
                netSalary,
                track.name(),
                track.calculateLimit(netSalary),
                plans
        );
   }
}
