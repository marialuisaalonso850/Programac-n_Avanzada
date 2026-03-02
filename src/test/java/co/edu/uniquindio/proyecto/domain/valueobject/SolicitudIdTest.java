package co.edu.uniquindio.proyecto.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudIdTest {

    @Test
    void solicitudId_valido_seCreaCorrectamente() {

        UUID uuid = UUID.randomUUID();
        SolicitudId id = new SolicitudId(uuid);

        assertEquals(uuid, id.value());
    }

    @Test
    void solicitudId_null_lanzaExcepcion() {

        assertThrows(IllegalArgumentException.class, () ->
                new SolicitudId(null)
        );
    }

    @Test
    void generar_creaIdNoNull() {

        SolicitudId id = SolicitudId.generar();

        assertNotNull(id);
        assertNotNull(id.value());
    }
    @Test
    void toString_devuelveValorUUID() {

        UUID uuid = UUID.randomUUID();
        SolicitudId id = new SolicitudId(uuid);

        assertEquals(uuid.toString(), id.toString());
    }
}