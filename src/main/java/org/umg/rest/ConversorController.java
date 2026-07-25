package org.umg.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConversorController {

    private static final Map<String, Double> TASAS_RESPECTO_USD = new HashMap<>();

    static {
        TASAS_RESPECTO_USD.put("USD", 1.0);
        TASAS_RESPECTO_USD.put("GTQ", 7.75);
        TASAS_RESPECTO_USD.put("EUR", 0.92);
    }

    @GetMapping("/api/convertir")
    public ResponseEntity<Map<String, Object>> convertir(
            @RequestParam double monto,
            @RequestParam String monedaOrigen,
            @RequestParam String monedaDestino
    ) {
        String origen = monedaOrigen.toUpperCase();
        String destino = monedaDestino.toUpperCase();

        Map<String, Object> respuesta = new HashMap<>();

        // Validación: monto no puede ser negativo
        if (monto < 0) {
            respuesta.put("error", "El monto no puede ser negativo");
            return ResponseEntity.badRequest().body(respuesta);
        }

        // Validación: monedas soportadas
        if (!TASAS_RESPECTO_USD.containsKey(origen) || !TASAS_RESPECTO_USD.containsKey(destino)) {
            respuesta.put("error", "Moneda no soportada. Use: USD, GTQ o EUR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        double montoEnUsd = monto / TASAS_RESPECTO_USD.get(origen);
        double montoConvertido = montoEnUsd * TASAS_RESPECTO_USD.get(destino);
        montoConvertido = Math.round(montoConvertido * 100.0) / 100.0;

        respuesta.put("montoOriginal", monto);
        respuesta.put("monedaOrigen", origen);
        respuesta.put("monedaDestino", destino);
        respuesta.put("montoConvertido", montoConvertido);

        return ResponseEntity.ok(respuesta);
    }
}