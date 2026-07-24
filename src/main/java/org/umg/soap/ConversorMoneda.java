package org.umg.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface ConversorMoneda {

    @WebMethod
    double convertir(
            @WebParam(name = "monto") double monto,
            @WebParam(name = "monedaOrigen") String monedaOrigen,
            @WebParam(name = "monedaDestino") String monedaDestino
    );
}