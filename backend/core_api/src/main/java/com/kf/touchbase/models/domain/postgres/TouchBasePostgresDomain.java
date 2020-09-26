package com.kf.touchbase.models.domain.postgres;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class TouchBasePostgresDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @CreationTimestamp
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    protected LocalDateTime updatedAt;
}
