package com.example.studentsretake.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private float price;
    private String name;
    private int qty;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;

        if (getId() != product.getId()) return false;
        if (Float.compare(product.getPrice(), getPrice()) != 0) return false;
        return getName() != null ? getName().equals(product.getName()) : product.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getPrice() != +0.0f ? Float.floatToIntBits(getPrice()) : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
