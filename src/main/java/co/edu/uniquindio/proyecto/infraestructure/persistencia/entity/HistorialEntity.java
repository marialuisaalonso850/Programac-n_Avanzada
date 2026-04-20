package co.edu.uniquindio.proyecto.infraestructure.persistencia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    private String accion;

    private String usuarioId;

    @Column(length = 2000)
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private SolicitudEntity solicitud;
}