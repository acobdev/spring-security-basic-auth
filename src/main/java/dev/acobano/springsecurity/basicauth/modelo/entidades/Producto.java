package dev.acobano.springsecurity.basicauth.modelo.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter @Setter
public class Producto
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long id;

    @NotBlank
    @Column(name = "nombre")
    private String nombre;

    @DecimalMin(value = "0.01")
    @Column(name = "precio")
    private BigDecimal precio;
}
