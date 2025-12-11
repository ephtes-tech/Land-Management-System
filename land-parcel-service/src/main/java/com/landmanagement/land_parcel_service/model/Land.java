package com.landmanagement.land_parcel_service.model;

import com.landmanagement.land_parcel_service.enumirator.LandType;
import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Polygon;

import java.time.OffsetDateTime;

@Entity
@Data
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;
    private String zone;
    private String woreda;
    private String kebele;

    @Enumerated(EnumType.STRING)
    private LandType landType;

    @Column(columnDefinition = "geometry(Polygon,4326")
    private Polygon coordinates;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt=OffsetDateTime.now();
        updatedAt=createdAt;
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt=OffsetDateTime.now();
    }




}
