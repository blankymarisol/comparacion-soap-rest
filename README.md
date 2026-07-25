## ▶️ Cómo ejecutar el proyecto

### Requisito previo
Tener instalado JDK 21 y Maven (o usar el Maven embebido de IntelliJ).

### 1. Clonar el repositorio
```bash
git clone <URL-del-repositorio>
cd comparacion-soap-rest
```

### 2. Ejecutar el servicio SOAP
Abrir `src/main/java/org/umg/soap/SoapServer.java` en IntelliJ y ejecutar su método `main()`.

Alternativamente, desde terminal:
```bash
mvn compile exec:java -Dexec.mainClass="org.umg.soap.SoapServer"
```

El servicio quedará disponible en:
http://localhost:8081/conversorMoneda

### 3. Ejecutar el servicio REST
Abrir `src/main/java/org/umg/rest/RestApplication.java` en IntelliJ y ejecutar su método `main()`.

Alternativamente, desde terminal:
```bash
mvn spring-boot:run
```

El servicio quedará disponible en:
http://localhost:8080/

> 📌 Ambos servicios pueden ejecutarse **simultáneamente**, ya que usan puertos distintos (8081 para SOAP, 8080 para REST).

## 🧼 Cómo consumir el servicio SOAP

**WSDL:**
http://localhost:8081/conversorMoneda?wsdl

**Operación disponible:** `convertir(monto, monedaOrigen, monedaDestino)`

**Probado con:** SoapUI, creando un proyecto SOAP a partir del WSDL anterior.

**Ejemplo de petición (SOAP Envelope):**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap="http://soap.umg.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:convertir>
         <monto>100</monto>
         <monedaOrigen>USD</monedaOrigen>
         <monedaDestino>GTQ</monedaDestino>
      </soap:convertir>
   </soapenv:Body>
</soapenv:Envelope>
```

**Respuesta esperada:**
```xml
<return>775.0</return>
```

Monedas soportadas: `USD`, `GTQ`, `EUR`. Si se envía una moneda no soportada, el servicio responde con un `SOAP Fault` describiendo el error.

## 🌐 Cómo consumir el servicio REST

**Endpoint:**
GET http://localhost:8080/api/convertir?monto=100&monedaOrigen=USD&monedaDestino=GTQ

**Probado con:** Postman.

**Respuesta exitosa (200 OK):**
```json
{
  "monedaOrigen": "USD",
  "monedaDestino": "GTQ",
  "montoOriginal": 100.0,
  "montoConvertido": 775.0
}
```

**Respuesta con error (400 Bad Request)** — ejemplo con moneda no soportada:
GET http://localhost:8080/api/convertir?monto=100&monedaOrigen=USD&monedaDestino=XYZ

```json
{
  "error": "Moneda no soportada. Use: USD, GTQ o EUR"
}
```

## 🧪 Pruebas realizadas

| Servicio | Herramienta | Caso probado | Resultado |
|---|---|---|---|
| SOAP | SoapUI | Conversión válida (100 USD → GTQ) | ✅ `775.0` |
| SOAP | SoapUI | Moneda no soportada | ✅ SOAP Fault controlado |
| REST | Postman | Conversión válida (100 USD → GTQ) | ✅ `200 OK` |
| REST | Postman | Moneda no soportada (XYZ) | ✅ `400 Bad Request` |

> Las capturas de estas pruebas se encuentran en la carpeta `/capturas` del repositorio (o anexadas en la entrega, según corresponda).

## 🔍 Comparación entre SOAP y REST

Durante el desarrollo notamos que **REST fue considerablemente más sencillo de implementar** que SOAP. Con Spring Boot, exponer un endpoint funcional tomó pocas líneas de código y la configuración fue mínima, mientras que SOAP requirió entender conceptos adicionales como el contrato WSDL, las anotaciones `@WebService` y la publicación explícita del *endpoint*. La parte que más nos costó comprender fue precisamente la diferencia entre el **WSDL** (un contrato XML rígido, generado automáticamente, que define con precisión cada operación, parámetro y tipo de dato) y el **JSON** de REST (mucho más flexible y liviano, sin un contrato formal que lo acompañe por defecto). En términos de uso: consideramos que **SOAP** es preferible en entornos empresariales o financieros donde se necesita un contrato estricto, seguridad avanzada (WS-Security) y transacciones complejas; mientras que **REST** es la opción natural para APIs públicas, aplicaciones web y móviles, microservicios, y en general cualquier escenario donde se priorice la simplicidad, el rendimiento y la integración rápida con otros sistemas.

## 👥 Autoría

Proyecto desarrollado como actividad práctica para comprender las diferencias entre arquitecturas SOAP y REST.