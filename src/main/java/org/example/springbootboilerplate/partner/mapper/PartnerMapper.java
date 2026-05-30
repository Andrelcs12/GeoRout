package org.example.springbootboilerplate.partner.mapper;

import org.example.springbootboilerplate.partner.dto.PartnerDTO;
import org.example.springbootboilerplate.partner.model.Partner;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;

@Component
public class PartnerMapper {

    // Cria uma fábrica do JTS configurada com o SRID 4326 (WGS 84 - Graus Decimais)
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    /**
     * Converte o DTO de requisição e as coordenadas obtidas pelo Service em uma Entidade Partner
     * pronta para persistência, transformando o raio em metros em um MultiPolygon.
     */
    public Partner toEntity(PartnerDTO.RequestPartnerDTO dto, Double latitude, Double longitude) {
        Partner partner = new Partner();
        partner.setName(dto.name());
        partner.setMaxCapacity(dto.maxCapacity());
        partner.setCurrentCapacity(0);
        partner.setActive(true);
        partner.setStreet(dto.street());
        partner.setNumber(dto.number());
        Point centerPoint = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        double radiusInDegrees = dto.radiusInMeters() / 111320.0;

        Geometry bufferGeometry = centerPoint.buffer(radiusInDegrees, 32);

        MultiPolygon coverageArea;
        if (bufferGeometry instanceof MultiPolygon) {
            coverageArea = (MultiPolygon) bufferGeometry;
        } else {
            coverageArea = geometryFactory.createMultiPolygon(new org.locationtech.jts.geom.Polygon[]{
                    (org.locationtech.jts.geom.Polygon) bufferGeometry
            });
        }

        partner.setCoverageArea(coverageArea);
        return partner;
    }
}