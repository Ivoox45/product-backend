package com.ivoox.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "products",
    indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_status", columnList = "active")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_name_category", columnNames = {"name", "category_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "category")
@EqualsAndHashCode(exclude = "category")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 500)
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Min(0)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "category_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_product_category")
    )
    private Category category;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @PrePersist
    public void prePersist() {
        if (!this.active) {
            this.active = true;
        }
    }
}
