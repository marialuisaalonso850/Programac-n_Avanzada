package co.edu.uniquindio.proyecto.infraestructure.persistencia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    private String identificacion;

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String tipoUsuario;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String password; // <- nuevo
}