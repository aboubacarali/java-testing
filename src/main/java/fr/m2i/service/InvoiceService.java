package fr.m2i.service;

import fr.m2i.model.Product;
import fr.m2i.repository.ProductRepository;

import java.util.List;
import java.util.Set;

public class InvoiceService {
    private final ProductRepository productRepository;
    private static final double DISCOUNT_THRESHOLD = 100.0;
    private static final double DISCOUNT_RATE = 0.90; // 10% de réduction

    public InvoiceService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public double calculateTotalAmount(Set<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        double total = products.stream().mapToDouble(Product::getPrice).sum();

        if (total > DISCOUNT_THRESHOLD) {
            total *= DISCOUNT_RATE;
        }
        return total;
    }
}
