package com.simulateloan.simulateloan.application.controllers;

import com.simulateloan.simulateloan.application.controllers.dto.request.SimulateLoanRequest;
import com.simulateloan.simulateloan.application.controllers.dto.response.SimulateLoanResponse;
import com.simulateloan.simulateloan.application.usecases.SimulateLoanUseCase;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/simulations")
public class SimulateLoanController {

    private final SimulateLoanUseCase useCase;

    public SimulateLoanController(SimulateLoanUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public SimulateLoanResponse simulate(@RequestBody SimulateLoanRequest request) {
        return useCase.process(request);
    }
}