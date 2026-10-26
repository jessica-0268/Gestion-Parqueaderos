# Sistema de Gestión de Parqueaderos

API RESTful desarrollada con **Spring Boot** y **Java 21** para la gestión integral de un parqueadero. El sistema centraliza la administración de accesos, disponibilidad de puestos, cálculo de tarifas, registro de vehículos y autenticación administrativa.

Mapeo endpoints aplicativo
1-- Clase EstadoResponse se encarga del estado general del parqueadero
endpoint asociado : GET /api/parqueadero/estado

2--IngresoRequest datos necesarios para el ingreso del vehiculo
endpoint asociado : POST /api/tickets/ingreso

3--IngresoResponse respuesta del servidor despues del ingreso del cliente
endpoint asociado : POST /api/tickets/ingreso

4--SalidaRequest informacion enviada para registrar la salida del vehiculo
endpoint asociado : POST /api/tickets/salida

5--SalidaResponse informacion al finalizar el servicio y la salida del vehiculo
endpoint asociado : POST /api/tickets/salida

6--PuestoResponse informacion detallada sobre el estado individual de cada puesto
endpoint asociado : GET /api/puestos

7--TarifaRequest datos enviados por el administrador para modificar valores (precios)
endpoint asociado : PUT /api/tarifas

8--AdminLoginRequest inicio de sesion con permisos para el administrador
endpoint asociado : POST /api/administrador/login