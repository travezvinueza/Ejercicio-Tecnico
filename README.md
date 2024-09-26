
# Arquitectura Cliente Servidor

Este proyecto está desarrollado utilizando una arquitectura cliente-servidor basada en el patrón MVC (Modelo-Vista-Controlador), con el backend implementado en Java utilizando Spring Boot y el frontend en Angular.

## Tecnologías Utilizadas

- Spring Boot: Para la construcción del servicio
- Flyway: Para la creacion de tablas y carga de datos iniciales
- PostgreSQL: Bases de datos relacionales para el almacenamiento de datos.
- ModelMapper: Para mapear objetos de un modelo a otro.
- Angular: Framework frontend utilizado para la creación de la interfaz gráfica de usuario.

# Configuración

Dentro del archivo: /resources/.. se encuentran el script para creacion de tablas y carga de datos iniciales. El contextPath de la app esta configurado con el valor /api. De acuerdo a la configuración en el archivo application.properties.
- El servicio se deplega en el puerto 8080 

El proyecto tiene swagger para la documentacion 

#### Ruta para acceder a Swagger del servicio 

```
  http://localhost:8080/api/swagger-ui/index.html#/
```

#### Ruta para acceder al frontend (Angular):

```
  http://localhost:4200/
```

 
