

# Sistema de Triage y Gestión de Solicitudes Académicas

## Integrantes: 
### Maria Luisa Alonso Giraldo J. Valentina Orlas Pachon
##  Descripción

Este proyecto es un sistema para la gestión de solicitudes académicas,
permitiendo el registro, clasificación, seguimiento y control de solicitudes
realizadas por diferentes tipos de usuarios como estudiantes, docentes,
coordinadores y administradores.

El sistema está diseñado bajo principios de arquitectura limpia y
Domain-Driven Design (DDD), aplicando buenas prácticas como:

- Uso de Value Objects
- Enums para reglas de dominio
- Inmutabilidad mediante `record`
- Encapsulación de reglas de negocio
- Separación de responsabilidades

---

##  Arquitectura

El proyecto está estructurado siguiendo principios de DDD y separación por capas:

- **domain/**
    - entity/
    - valueobject/
    - service/
    - exception/

- **application/**
- **infrastructure/**

El núcleo del sistema se encuentra en la capa de dominio, donde se
encuentran las reglas de negocio principales.

---

##  Reglas de Negocio Principales

- Los correos electrónicos deben tener formato válido.
- Las descripciones deben tener entre 10 y 500 caracteres.
- Una solicitud tiene un ciclo de vida definido por estados.
- Los usuarios pueden estar en estado ACTIVO o INACTIVO.
- Cada solicitud tiene un tipo y una prioridad definida.
- Se mantiene un historial de acciones realizadas sobre la solicitud.
- Los identificadores se manejan mediante UUID encapsulados en Value Objects.

---

##  Principales Componentes del Dominio

### Value Objects

- Email
- Descripcion
- DocumentoIdentidad
- SolicitudId
- Historial

### Enums

- EstadoSolicitud
- EstadoUsuario
- Prioridad
- TipoSolicitud
- TipoUsuario
- TipoDocumento
- TipoAccion
- CanalOrigen

###  Entity

- Solicitud
- Usuario

---

##  Tecnologías Utilizadas

- Java
- UUID
- LocalDateTime
- Arquitectura basada en dominio (DDD)
- Spring Boot

---
