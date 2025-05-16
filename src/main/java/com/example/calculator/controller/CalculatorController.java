package com.example.calculator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador REST que gestiona les operacions de la calculadora.
 * Defineix els endpoints per a suma, resta, multiplicació i divisió.
 */
@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    @GetMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestParam double a, @RequestParam double b) {
        double result = a + b;
        Map<String, Object> response = Map.of(
                "operation", "add",
                "a", a,
                "b", b,
                "result", result
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subtract")
    public ResponseEntity<Map<String, Object>> subtract(@RequestParam double a, @RequestParam double b) {
        double result = a - b;
        Map<String, Object> response = Map.of(
                "operation", "subtract",
                "a", a,
                "b", b,
                "result", result
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/multiply")
    public ResponseEntity<Map<String, Object>> multiply(@RequestParam double a, @RequestParam double b) {
        double result = a * b;
        Map<String, Object> response = Map.of(
                "operation", "multiply",
                "a", a,
                "b", b,
                "result", result
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/divide")
    public ResponseEntity<Map<String, Object>> divide(@RequestParam double a, @RequestParam double b) {
        if (b == 0) {
            Map<String, Object> errorResponse = Map.of(
                    "operation", "divide",
                    "a", a,
                    "b", b,
                    "error", "Division by zero is not allowed"
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        double result = a / b;
        Map<String, Object> response = Map.of(
                "operation", "divide",
                "a", a,
                "b", b,
                "result", result
        );
        return ResponseEntity.ok(response);
    }
}
