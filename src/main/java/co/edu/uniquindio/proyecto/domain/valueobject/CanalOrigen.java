package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa el canal de origen por el cual se genera o recibe una solicitud
 * dentro del sistema.
 *
 * <p>Este enum funciona como un Value Object del dominio,
 * ya que representa un concepto inmutable con un conjunto finito de valores.</p>
 *
 * <ul>
 *     <li>CSU: Centro de Servicios Universitarios</li>
 *     <li>CORREO: Canal de correo electrónico</li>
 *     <li>SAC: Sistema de Atención al Ciudadano</li>
 *     <li>TELEFONICO: Atención telefónica</li>
 * </ul>
 *
 * @author Maria Luisa Alonso
 * @author Valentina Orlas Pachon
 * @version 1.0
 */

public enum CanalOrigen {
    /** Canal Centro de Servicios Universitarios */
    CSU,

    /** Canal de correo electrónico */
    CORREO,

    /** Sistema de Atención al Ciudadano */
    SAC,

    /** Canal de atención telefónica */
    TELEFONICO
}