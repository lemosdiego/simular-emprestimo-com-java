package com.simulateloan.simulateloan.application.controllers.dto.request;

import java.math.BigDecimal;

public record SimulateLoanRequest(String nome, BigDecimal salarioBruto) {
}
