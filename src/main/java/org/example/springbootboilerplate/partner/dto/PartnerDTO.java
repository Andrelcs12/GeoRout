package org.example.springbootboilerplate.partner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public final class PartnerDTO {
    private PartnerDTO() {}

    //1. contrato entrada (cadastro)

    // 1. O Contrato de Entrada (Cadastro) - Agora sem exigir lat/lng do cliente!
    public record RequestPartnerDTO(
            @NotBlank(message = "O nome do parceiro não pode estar em branco!")
            String name,

            @NotNull(message = "A capacidade máxima deve ser informada!")
            @Positive(message = "A capacidade máxima deve ser maior que zero!")
            Integer maxCapacity,

            @NotBlank(message = "O endereço é obrigatório!")
            String street,

            @NotBlank(message = "O número é obrigatório!")
            String number,

            @NotNull(message = "O raio de alcance em metros é obrigatório!")
            @Positive(message = "O raio de alcance deve ser um valor positivo!")
            Double radiusInMeters
    ) {}

    // 2. O Contrato de Saída (Resposta do Cadastro / Detalhes)
    public record ResponsePartnerDTO(
            UUID id,
            String name,
            boolean active,
            Integer maxCapacity,
            Integer currentCapacity,
            String street,
            String number
    ) {}

    // 3. O Contrato de Saída Simplificado (Para buscas em lote/geolocalização)
    public record SearchPartnerDTO(
            UUID id,
            String name,
            Integer availableCapacity
    ) {}

}
