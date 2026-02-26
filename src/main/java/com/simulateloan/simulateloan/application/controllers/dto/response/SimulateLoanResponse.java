package com.simulateloan.simulateloan.application.controllers.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record SimulateLoanResponse(
        String nome,
        BigDecimal salarioLiquido,
        String faixa,
        BigDecimal limiteTotal,
        List<PlansResponse> planos
) {}

