package com.land.parcel.model;

import com.land.parcel.enumr.LandType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Polygon;

import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Land {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long landId;

        @Column(nullable = false)
        private String region;

        @Column(nullable = false)
        private String zone;

        @Column(nullable = false)
        private String woreda;

        @Column(nullable = false)
        private String kebele;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private LandType landType;

        @Column(columnDefinition = "geometry(Polygon,4326)", nullable = false)
        private Polygon coordinates;

        @Column(nullable = false)
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
        public void prePersist() {
            createdAt = OffsetDateTime.now();
            updatedAt = createdAt;
        }

        @PreUpdate
        public void preUpdate() {
            updatedAt = OffsetDateTime.now();
        }


}
