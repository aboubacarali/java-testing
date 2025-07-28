package fr.m2i.repository;

import fr.m2i.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository {
    // Trouve des produits par leurs IDs
    List<Product> findAllById(Set<String> ids);
}
