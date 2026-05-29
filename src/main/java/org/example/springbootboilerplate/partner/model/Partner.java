package org.example.springbootboilerplate.partner.model;


import jakarta.persistence.*;
        import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.UUID;

@Entity
@Table(name = "partners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do parceiro não pode estar em branco!")
    private String name;

    private boolean active = true;

    @NotNull(message = "A capacidade máxima deve ser informada!")
    private Integer maxCapacity;

    private Integer currentCapacity = 0;

    // === CAMPOS DE ENDEREÇO FÍSICO ===
    @NotBlank(message = "O endereço é obrigatório!")
    private String street;

    @NotBlank(message = "O número é obrigatório!")
    private String number;

    // === CAMPO CORE ESPACIAL ===
    // O PostGIS armazena a área calculada (o raio redondo convertido em polígono) aqui
    @Column(columnDefinition = "geometry(MultiPolygon, 4326)")
    private MultiPolygon coverageArea;

}
