package co.edu.uniquindio.proyecto.infraestructure.persistencia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEntity {

    @Id
    private String id;

    private LocalDateTime fechaCreacion;

    // 🔥 IMPORTANTE: este campo evita tu error solicitanteId
    private String solicitanteId;

    @Column(length = 2000)
    private String descripcion;

    private String responsableId;

    private String tipo;

    private String estado;

    private String prioridad;

    private String canal;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialEntity> historial = new ArrayList<>();
}