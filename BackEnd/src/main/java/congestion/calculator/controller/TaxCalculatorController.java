package congestion.calculator.controller;

import congestion.calculator.service.TaxCalculatorService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tax")
public class TaxCalculatorController {

    private final TaxCalculatorService taxCalculatorService;

    public TaxCalculatorController(TaxCalculatorService taxCalculatorService) {
        this.taxCalculatorService = taxCalculatorService;
    }

    @GetMapping("/calculate")
    public Map<String, Integer> calculateTax() {
        // Simply call the service without logging
        Map<String, Integer> taxMap = taxCalculatorService.calculateTotalTax();
        return taxMap;
    }
}
