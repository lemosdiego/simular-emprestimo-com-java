package com.simulateloan.simulateloan.application.controllers.dto.response;

import java.math.BigDecimal;

public record PlanoResponse(
        Integer meses,
        BigDecimal valorParcela,
        BigDecimal percentualSalario,
        String jurosMensal
) {}
