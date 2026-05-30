package org.example.springbootboilerplate.partner.repository;

import org.example.springbootboilerplate.partner.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, UUID> {

    /**
     * Query 1: Busca todos os parceiros ativos cuja área de cobertura
     * contém o ponto do cliente.
     */
    @Query(nativeQuery = true, value = """
        SELECT p.* FROM partners p
        WHERE p.active = true
          AND public.ST_Contains(p.coverage_area, public.ST_SetSRID(public.ST_MakePoint(:longitude, :latitude), 4326))
        """)
    List<Partner> findAvailablePartnersByLocation(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );

    /**
     * Query 2: Busca um parceiro aplicando Lock Pessimista de Escrita (SELECT FOR UPDATE).
     */
    @Query(value = """
        SELECT p.* FROM partners p
        WHERE p.id = :id
        FOR UPDATE
        """, nativeQuery = true)
    Optional<Partner> findByIdForUpdate(@Param("id") UUID id);
}