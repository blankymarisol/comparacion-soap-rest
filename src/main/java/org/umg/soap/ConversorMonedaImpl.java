package org.umg.soap;

import jakarta.jws.WebService;
import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface = "org.umg.soap.ConversorMoneda")
public class ConversorMonedaImpl implements ConversorMoneda {

    // Tasas de cambio fijas, tomando el USD como moneda base (1 USD = X)
    private static final Map<String, Double> TASAS_RESPECTO_USD = new HashMap<>();

    static {
        TASAS_RESPECTO_USD.put("USD", 1.0);
        TASAS_RESPECTO_USD.put("GTQ", 7.75);   // 1 USD = 7.75 GTQ
        TASAS_RESPECTO_USD.put("EUR", 0.92);   // 1 USD = 0.92 EUR
    }

    @Override
    public double convertir(double monto, String monedaOrigen, String monedaDestino) {
        String origen = monedaOrigen.toUpperCase();
        String destino = monedaDestino.toUpperCase();

        if (!TASAS_RESPECTO_USD.containsKey(origen) || !TASAS_RESPECTO_USD.containsKey(destino)) {
            throw new IllegalArgumentException(
                    "Moneda no soportada. Use: USD, GTQ o EUR");
        }

        // Convertir el primer monto a USD, y luego de USD a la moneda destino
        double montoEnUsd = monto / TASAS_RESPECTO_USD.get(origen);
        double montoConvertido = montoEnUsd * TASAS_RESPECTO_USD.get(destino);

        // Redondear a 2 decimales
        return Math.round(montoConvertido * 100.0) / 100.0;
    }
}