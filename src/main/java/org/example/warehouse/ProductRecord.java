package org.example.warehouse;

import org.example.Category;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
    public ProductRecord {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Product name can't be null or empty.");
        if (category == null) throw new IllegalArgumentException("Category can't be null.");
        if (uuid == null) uuid = UUID.randomUUID();
        if (price == null) price = BigDecimal.ZERO;
    }


}
