package com.example.calculator.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Tests /add ---

    @Test
    void testAddEndpoint_Success() throws Exception {
        performGetRequest("/calculator/add", "5", "3")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("add"))
                .andExpect(jsonPath("$.a").value(5.0))
                .andExpect(jsonPath("$.b").value(3.0))
                .andExpect(jsonPath("$.result").value(9.0)); // <-- Fallará intencionalmente (debería ser 8.0)
    }

    @Test
    void testAddWithNegativeNumbers() throws Exception {
        performGetRequest("/calculator/add", "-5", "3")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-2.0));
    }

    @Test
    void testAddWithZero() throws Exception {
        performGetRequest("/calculator/add", "10", "0")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(10.0));
    }

    // --- Tests /subtract ---

    @ParameterizedTest
    @CsvSource({
            "10, 3, 7.0",
            "5, 8, -3.0",
            "-5, -3, -2.0",
            "10, 0, 10.0",
            "0, 5, -5.0"
    })
    void testSubtractEndpoint(String a, String b, double expectedResult) throws Exception {
        performGetRequest("/calculator/subtract", a, b)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("subtract"))
                .andExpect(jsonPath("$.a").value(Double.parseDouble(a)))
                .andExpect(jsonPath("$.b").value(Double.parseDouble(b)))
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

    // --- Tests /multiply ---

    @ParameterizedTest
    @CsvSource({
            "2, 3, 6.0",
            "0, 5, 0.0",
            "-2, 4, -8.0",
            "-3, -3, 9.0"
    })
    void testMultiplyEndpoint(String a, String b, double expectedResult) throws Exception {
        performGetRequest("/calculator/multiply", a, b)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("multiply"))
                .andExpect(jsonPath("$.a").value(Double.parseDouble(a)))
                .andExpect(jsonPath("$.b").value(Double.parseDouble(b)))
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

    // --- Tests /divide ---

    @ParameterizedTest
    @CsvSource({
            "6, 3, 2.0",
            "5, 2, 2.5",
            "-10, 2, -5.0",
            "-9, -3, 3.0"
    })
    void testDivideEndpoint(String a, String b, double expectedResult) throws Exception {
        performGetRequest("/calculator/divide", a, b)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("divide"))
                .andExpect(jsonPath("$.a").value(Double.parseDouble(a)))
                .andExpect(jsonPath("$.b").value(Double.parseDouble(b)))
                .andExpect(jsonPath("$.result").value(expectedResult));
    }

    @Test
    void testDivideByZero() throws Exception {
        performGetRequest("/calculator/divide", "10", "0")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Division by zero is not allowed"));
    }

    // --- Mètode auxiliar ---

    private ResultActions performGetRequest(String path, String a, String b) throws Exception {
        return mockMvc.perform(get(path).param("a", a).param("b", b));
    }
}
