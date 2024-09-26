
# Arquitectura Cliente Servidor

Proyecto realizado bajo mvc con arquitetcura cliente servidor Este proyecto está desarrollado en Java utilizando Spring

## Tecnologías Utilizadas

- Spring Boot: Para la construcción del servicio
- Flyway: Para la creacion de tablas y carga de datos iniciales
- PostgreSQL: Bases de datos relacionales para el almacenamiento de datos.
- ModelMapper: Para mapear objetos de un modelo a otro.


# Configuración

Dentro del archivo: /resources/.. se encuentran el script para creacion de tablas y carga de datos iniciales. El contextPath de la app esta configurado con el valor /api. De acuerdo a la configuración en el archivo application.properties.
- El servicio se deplega en el puerto 8080 

El proyecto tiene swagger para la documentacion 

#### Para ingresar apuntar a la siguiente ruta

```http
  http://localhost:8080/api/swagger-ui/index.html#/
```

 
