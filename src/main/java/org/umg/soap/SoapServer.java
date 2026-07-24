package org.umg.soap;

import jakarta.xml.ws.Endpoint;

public class SoapServer {

    public static void main(String[] args) {
        String url = "http://localhost:8081/conversorMoneda";

        Endpoint.publish(url, new ConversorMonedaImpl());

        System.out.println("Servicio SOAP publicado en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }
}