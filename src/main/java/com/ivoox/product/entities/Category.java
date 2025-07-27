package com.ivoox.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories", indexes = {
    @Index(name = "idx_category_name", columnList = "name")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_category_name", columnNames = { "name" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true) // allows setName(...).setProducts(...)
@ToString(exclude = "products")
@EqualsAndHashCode(exclude = "products")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Utility method to maintain bidirectional consistency.
     */
    public void addProduct(Product product) {
        this.getProducts().add(product);
        product.setCategory(this);
    }

    /**
     * Defensive getter to ensure products is never null.
     */
    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }
}
